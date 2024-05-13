package com.sdi.jig.application;

import com.sdi.jig.client.NotificationApiClient;
import com.sdi.jig.dto.response.JigModelCountResponseDto;
import com.sdi.jig.dto.response.JigModelCountResponseDto.JigModelCount;
import com.sdi.jig.dto.response.JigMonthResponseDto;
import com.sdi.jig.dto.response.JigResponseDto;
import com.sdi.jig.dto.response.JigUpdatedCheckListResponseDto;
import com.sdi.jig.dto.response.JigUpdatedCheckListResponseDto.UpdatedJig;
import com.sdi.jig.entity.nosql.JigNosqlEntity;
import com.sdi.jig.entity.rdb.JigRDBEntity;
import com.sdi.jig.repository.nosql.JigNosqlRepository;
import com.sdi.jig.repository.rdb.JigItemIOHistoryRepository;
import com.sdi.jig.repository.rdb.JigItemRDBRepository;
import com.sdi.jig.repository.rdb.JigItemRepairHistoryRepository;
import com.sdi.jig.repository.rdb.JigRDBRepository;
import com.sdi.jig.util.CheckItem;
import com.sdi.jig.util.IOStatus;
import com.sdi.jig.util.JigStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JigService {

    private static final int MAX_COUNT = 100;

    private final JigRDBRepository jigRDBRepository;
    private final JigItemRDBRepository jigItemRDBRepository;
    private final JigNosqlRepository jigNosqlRepository;
    private final JigItemIOHistoryRepository jigItemIOHistoryRepository;
    private final JigItemRepairHistoryRepository jigItemRepairHistoryRepository;
    private final NotificationApiClient notificationApiClient;

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
        TreeMap<String, LocalDateTime> startAndEndDate = integerToLocalDateTime(year, month);

        LocalDateTime startDate = startAndEndDate.get("startDate");
        LocalDateTime endDate = startAndEndDate.get("endDate");

        // 폐기 갯수
        int countDelete = jigItemRDBRepository.countByDeletedTimeBetween(startDate, endDate);

        // 교체된 지그 갯수
        int countChange = jigItemIOHistoryRepository.countByInOutTimeBetweenAndStatus(startDate, endDate, IOStatus.OUT);

        // 보수 요청 갯수
        int countRepairRequest = notificationApiClient.countRepairRequest(accessToken, year, month).getResult().count();

        // 보수 완료 갯수
        int countRepairFinish = jigItemRepairHistoryRepository.countByRepairTimeBetween(startDate, endDate);

        return new JigMonthResponseDto(countDelete, countChange, countRepairRequest, countRepairFinish);
    }

    public JigModelCountResponseDto jigCountStatus(JigStatus status) {
        List<JigRDBEntity> jigList = jigRDBRepository.findAll();
        List<JigModelCount> jigModelCountList = new ArrayList<>();

        for (JigRDBEntity jig : jigList) {
            int count = jigItemRDBRepository.countByStatusAndJigId(status, jig.getId());
            if (count == 0) continue;
            jigModelCountList.add(JigModelCount.of(jig.getModel(), count));
        }

        return JigModelCountResponseDto.from(MAX_COUNT, jigModelCountList);
    }

    public JigUpdatedCheckListResponseDto updatedCheckList(Integer year, Integer month) {
        TreeMap<String, LocalDateTime> startAndEndDate = integerToLocalDateTime(year, month);

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

    public JigRDBEntity getJigRdbEntityByModel(String model) {
        return jigRDBRepository.findByModel(model)
                .orElseThrow(() -> new IllegalArgumentException("모델을 찾을 수 없습니다."));
    }

    public JigNosqlEntity getJigNosqlEntityByModel(String model) {
        return jigNosqlRepository.findById(model)
                .orElseThrow(() -> new IllegalArgumentException("모델을 찾을 수 없습니다."));
    }

    private TreeMap<String, LocalDateTime> integerToLocalDateTime(Integer year, Integer month) {
        if (year == null) {
            year = Calendar.getInstance().get(Calendar.YEAR);
        }
        if (month == null) {
            month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        }

        // 월의 시작과 끝
        LocalDateTime startDate = YearMonth.of(year, month).atDay(1).atStartOfDay();
        LocalDateTime endDate = YearMonth.of(year, month).atEndOfMonth().atTime(23, 59, 59);

        TreeMap<String, LocalDateTime> startAndEndDate = new TreeMap<>();
        startAndEndDate.put("startDate", startDate);
        startAndEndDate.put("endDate", endDate);

        return startAndEndDate;
    }
}
