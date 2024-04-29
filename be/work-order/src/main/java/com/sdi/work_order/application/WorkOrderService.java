package com.sdi.work_order.application;

import com.sdi.work_order.client.JigItemClient;
import com.sdi.work_order.client.response.JigItemResponseDto;
import com.sdi.work_order.dto.reponse.WorkOrderGroupingResponseDto;
import com.sdi.work_order.dto.request.WorkOrderCreateRequestDto;
import com.sdi.work_order.entity.WorkOrderNosqlEntity;
import com.sdi.work_order.entity.WorkOrderRDBEntity;
import com.sdi.work_order.repository.WorkOrderNosqlRepository;
import com.sdi.work_order.repository.WorkOrderRDBRepository;
import com.sdi.work_order.util.Response;
import com.sdi.work_order.util.WorkOrderCheckList;
import com.sdi.work_order.util.WorkOrderItem;
import com.sdi.work_order.util.WorkOrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WorkOrderService {

    private final JigItemClient jigItemClient;
    private final WorkOrderRDBRepository workOrderRDBRepository;
    private final WorkOrderNosqlRepository workOrderNosqlRepository;

    public WorkOrderGroupingResponseDto grouping() {
        List<WorkOrderItem> items = new ArrayList<>();
        List<WorkOrderRDBEntity> all = workOrderRDBRepository.findAll();
        for (WorkOrderRDBEntity entity : all) {
            // TODO: 사용자 연결 완료시 실제 사용자 이름으로 대체
            String creator = entity.getCreatorEmployeeNo();
            String terminator = entity.getTerminatorEmployeeNo();

            items.add(WorkOrderItem.from(entity, creator, terminator));
        }

        Map<WorkOrderStatus, List<WorkOrderItem>> group =
                items.stream()
                        .collect(Collectors.groupingBy(WorkOrderItem::status));
        return WorkOrderGroupingResponseDto.from(group);
    }

    @Transactional
    public void create(WorkOrderCreateRequestDto dto) {
        // 사번 조회
        // TODO: 사용자 연결 완료 시 실 사번으로 대체
        String employeeNo = "생성자 사번";

        // 일련번호로 jig 조회
        JigItemResponseDto jigItem = getJigItem(dto.serialNo());

        // 저장할 데이터 생성
        String checkListId = UUID.randomUUID().toString();
        WorkOrderRDBEntity rdb = WorkOrderRDBEntity.from(employeeNo, jigItem.serialNo(), jigItem.model(), checkListId);
        WorkOrderNosqlEntity nosql = WorkOrderNosqlEntity.from(checkListId, false, WorkOrderCheckList.from(jigItem.checkList()));

        // 데이터 저장
        workOrderRDBRepository.save(rdb);
        workOrderNosqlRepository.save(nosql);
    }

    private JigItemResponseDto getJigItem(String serialNo) {
        return jigItemClient.findBySerialNo(serialNo).getResult();
    }
}
