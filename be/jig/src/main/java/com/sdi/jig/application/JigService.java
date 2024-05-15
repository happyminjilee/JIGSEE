package com.sdi.jig.application;

import com.sdi.jig.client.WorkOrderClient;
import com.sdi.jig.dto.response.*;
import com.sdi.jig.dto.response.JigModelCountResponseDto.JigModelCount;
import com.sdi.jig.dto.response.JigUpdatedCheckListResponseDto.UpdatedJig;
import com.sdi.jig.entity.nosql.JigNosqlEntity;
import com.sdi.jig.entity.rdb.JigItemRDBEntity;
import com.sdi.jig.entity.rdb.JigRDBEntity;
import com.sdi.jig.entity.rdb.JigStatsRDBEntity;
import com.sdi.jig.repository.nosql.JigNosqlRepository;
import com.sdi.jig.repository.rdb.*;
import com.sdi.jig.util.CheckItem;
import com.sdi.jig.util.IOStatus;
import com.sdi.jig.util.JigStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JigService {

    private final int MAX_COUNT = 100;
    private final int PRODUCTION_HOUR = 1000;
    private final int PRODUCTION_PROFIT = 100;
    private final int FACILITY_HOUR_OPERATING_COST = 1000;
    private final int BREAKEDOWN_COST = 500;

    private final JigRDBRepository jigRDBRepository;
    private final JigItemRDBRepository jigItemRDBRepository;
    private final JigNosqlRepository jigNosqlRepository;
    private final JigItemIOHistoryRepository jigItemIOHistoryRepository;
    private final JigItemRepairHistoryRepository jigItemRepairHistoryRepository;
    private final JigStatsRDBRepository jigStatsRDBRepository;
    private final WorkOrderClient workOrderClient;

    public JigResponseDto findByModel(String model) {
        JigRDBEntity rdb = getJigRdbEntityByModel(model);
        JigNosqlEntity nosql = getJigNosqlEntityByModel(model);

        return JigResponseDto.from(rdb, nosql);
    }

    @Transactional
    public void updateCheckList(String model, List<CheckItem> checkList) {
        JigNosqlEntity entity = getJigNosqlEntityByModel(model);
        entity.updateCheckList(checkList);
        jigNosqlRepository.save(entity);
    }

    public JigMonthResponseDto monthStatus(String accessToken, Integer year, Integer month) {
        Map<String, LocalDateTime> startAndEndDate = integerToLocalDateTime(year, month);

        LocalDateTime startDate = startAndEndDate.get("startDate");
        LocalDateTime endDate = startAndEndDate.get("endDate");

        // 폐기 갯수
        int countDelete = jigItemRDBRepository.countByDeletedTimeBetween(startDate, endDate);

        // 교체된 지그 갯수
        int countChange = jigItemIOHistoryRepository.countByInOutTimeBetweenAndStatus(startDate, endDate, IOStatus.OUT);

        // 보수 요청 갯수
        int countRepairRequest = workOrderClient.countRepairRequest(accessToken, year, month).getResult().count();

        // 보수 완료 갯수
        int countRepairFinish = jigItemRepairHistoryRepository.countByRepairTimeBetween(startDate, endDate);

        return new JigMonthResponseDto(countDelete, countChange, countRepairRequest, countRepairFinish);
    }

    public JigModelCountResponseDto jigCountStatus() {
        List<JigRDBEntity> jigList = jigRDBRepository.findAll();
        List<JigModelCount> jigModelCountList = new ArrayList<>();

        for (JigRDBEntity jig : jigList) {
            int countReady = jigItemRDBRepository.countByStatusAndJigId(JigStatus.READY, jig.getId());
            int countWarehouse = jigItemRDBRepository.countByStatusAndJigId(JigStatus.WAREHOUSE, jig.getId());
            jigModelCountList.add(JigModelCount.of(jig.getModel(), countReady, countWarehouse));
        }

        return JigModelCountResponseDto.from(MAX_COUNT, jigModelCountList);
    }

    public JigUpdatedCheckListResponseDto updatedCheckList(Integer year, Integer month) {
        Map<String, LocalDateTime> startAndEndDate = integerToLocalDateTime(year, month);

        LocalDateTime startDate = startAndEndDate.get("startDate");
        LocalDateTime endDate = startAndEndDate.get("endDate");

        List<UpdatedJig> updatedJigList =
                jigNosqlRepository.findAllByUpdatedAtBetween(startDate, endDate)
                        .map(list -> list.stream()
                                .map(UpdatedJig::of)
                                .collect(Collectors.toList())
                        )
                        .orElse(Collections.emptyList());
        return JigUpdatedCheckListResponseDto.from(updatedJigList);
    }

    public JigOptimalIntervalResponseDto jigOptimalInterval(String model) {
        JigRDBEntity jig = getJigRdbEntityByModel(model);

        List<Float> data = jigStatsRDBRepository.findAllByJigOrderByRepairCount(jig).stream()
                .map(JigStatsRDBEntity::getOptimalInterval)
                .map(value -> value.setScale(2, RoundingMode.HALF_UP).floatValue()) // 소수점 두 번째 자리에서 반올림하고 float로 변환
                .collect(Collectors.toList());

        return JigOptimalIntervalResponseDto.of(data);
    }

    public List<JigGraphResponseDto> jigGraph() {
        int startDay = 10;
        int endDay = 30;

        // 응답 리스트
        List<JigGraphResponseDto> jigGraphResponseDtoList = new ArrayList<>();

        // 지그 모델 별 적정 점검 주기 List
        List<Float> optimalIntervalList = getOptimalIntervalList();

        // 날짜 별 값을 확인하기 위한 for 문
        for (int day = startDay; day <= endDay; day++) {
            // 해당 점검 주기에 따른 예상 점검 횟수
            double monthCheckNumber = (double) 30 / day;

            int finalDay = day;
            // 점검을 놓친 지그 갯수
            int countMissingJig = (int) optimalIntervalList.stream()
                    .filter(cycle -> cycle < finalDay)
                    .count();

            // 점검 대상 지그 갯수
            int countCheckJig = optimalIntervalList.size() - countMissingJig;

            // 비정규 점검 소요 시간
            Integer downTime = getDownTime(countMissingJig);
            if (downTime == null) {
                jigGraphResponseDtoList.add(JigGraphResponseDto.of(day, -1, -1, -1, -1));
                continue;
            }

            // 정규 유지 보수 소요 시간
            int maintenanceTime = getMaintenanceTime(countCheckJig);

            // 한달 소요 되는 총 비정규 점검 시간
            Double monthDownTime = downTime * monthCheckNumber;
            // 한달 소요 되는 정규 유지 보수 소요 시간
            Double monthMaintenanceTime = maintenanceTime * monthCheckNumber;
            // 한달 간 설비 총 가동 시간
            double monthOperatingTime = 30 * 24 - (monthDownTime + monthMaintenanceTime);

            // 생산량
            double outputValue = monthOperatingTime * PRODUCTION_HOUR;

            // 생산 비용
            double costValue = monthOperatingTime * FACILITY_HOUR_OPERATING_COST + downTime * BREAKEDOWN_COST;

            // 차이
            double yieldValue = outputValue * PRODUCTION_PROFIT - costValue;

            jigGraphResponseDtoList.add(JigGraphResponseDto.of(day, outputValue, costValue, yieldValue, countMissingJig));
        }

        return jigGraphResponseDtoList;
    }

    public JigRDBEntity getJigRdbEntityByModel(String model) {
        return jigRDBRepository.findByModel(model)
                .orElseThrow(() -> new IllegalArgumentException("모델을 찾을 수 없습니다."));
    }

    public JigNosqlEntity getJigNosqlEntityByModel(String model) {
        return jigNosqlRepository.findById(model)
                .orElseThrow(() -> new IllegalArgumentException("모델을 찾을 수 없습니다."));
    }

    private Map<String, LocalDateTime> integerToLocalDateTime(Integer year, Integer month) {
        if (year == null) {
            year = Calendar.getInstance().get(Calendar.YEAR);
        }
        if (month == null) {
            month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        }

        // 월의 시작과 끝
        LocalDateTime startDate = YearMonth.of(year, month).atDay(1).atStartOfDay();
        LocalDateTime endDate = YearMonth.of(year, month).atEndOfMonth().atTime(23, 59, 59);

        Map<String, LocalDateTime> startAndEndDate = new HashMap<>();
        startAndEndDate.put("startDate", startDate);
        startAndEndDate.put("endDate", endDate);

        return startAndEndDate;
    }

    private List<Float> getOptimalIntervalList() {
        List<JigItemRDBEntity> jigItemList = jigItemRDBRepository.findByStatus(JigStatus.IN);

        // 지그 모델 별 적정 점검 주기 List
        List<Float> optimalIntervalList = new ArrayList<>();

        for (JigItemRDBEntity jigItem : jigItemList) {
            int countRepair = jigItemRepairHistoryRepository.countByJigItemId(jigItem.getJig().getId());
            BigDecimal optimalInterval = jigStatsRDBRepository.findByJigIdAndRepairCount(jigItem.getJig().getId(), countRepair).getOptimalInterval();
            float optimalIntervalFloat = roundToTwoDecimalPlaces(optimalInterval);
            optimalIntervalList.add(optimalIntervalFloat);
        }

        return optimalIntervalList;
    }

    private Integer getDownTime(int count) {
        if (count <= 2) {
            return 1;
        } else if (count <= 7) {
            return 4;
        } else if (count <= 19) {
            return 24;
        } else if (20 <= count) {
            return null;
        }
        return null;
    }

    private Integer getMaintenanceTime(int count) {
        return count * 4 / 20;
    }

    private static float roundToTwoDecimalPlaces(BigDecimal value) {
        BigDecimal bd = value.setScale(2, RoundingMode.HALF_UP);
        return bd.floatValue();
    }
}
