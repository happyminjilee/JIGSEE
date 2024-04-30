package com.sdi.work_order.application;

import com.sdi.work_order.client.JigItemClient;
import com.sdi.work_order.client.response.JigItemResponseDto;
import com.sdi.work_order.dto.reponse.WorkOrderGroupingResponseDto;
import com.sdi.work_order.dto.reponse.WorkOrderResponseDto;
import com.sdi.work_order.dto.request.WorkOrderCreateRequestDto;
import com.sdi.work_order.entity.WorkOrderNosqlEntity;
import com.sdi.work_order.entity.WorkOrderRDBEntity;
import com.sdi.work_order.repository.WorkOrderNosqlRepository;
import com.sdi.work_order.repository.WorkOrderRDBRepository;
import com.sdi.work_order.util.WorkOrderCheckList;
import com.sdi.work_order.util.WorkOrderItem;
import com.sdi.work_order.util.WorkOrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.sdi.work_order.dto.request.WorkOrderUpdateStatusRequestDto.UpdateStatusItem;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WorkOrderService {

    private final JigItemClient jigItemClient;
    private final WorkOrderRDBRepository workOrderRDBRepository;
    private final WorkOrderNosqlRepository workOrderNosqlRepository;

    public WorkOrderResponseDto all(WorkOrderStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(Math.max(0, page - 1), Math.max(1, size));

        Page<WorkOrderRDBEntity> infos = switch (status) {
            case PUBLISH, PROGRESS, FINISH -> workOrderRDBRepository.findByStatusOrderByCreatedAtDesc(status, pageable);
            case null -> workOrderRDBRepository.findAllByOrderByCreatedAtDesc(pageable);
        };

        List<WorkOrderItem> workOrderItems = mapToWorkOrderItems(infos);
        return WorkOrderResponseDto.of(infos.getNumber()+1, infos.getTotalPages(), workOrderItems);
    }

    public WorkOrderGroupingResponseDto grouping() {
        List<WorkOrderRDBEntity> all = workOrderRDBRepository.findAll();
        List<WorkOrderItem> items = mapToWorkOrderItems(all);

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

    @Transactional
    public void tmpSave(Long id, List<WorkOrderCheckList> updateCheckList) {
        WorkOrderRDBEntity rdb = getRDBWorkOrderById(id);
        saveData(rdb, updateCheckList);
    }

    @Transactional
    public void save(Long id, List<WorkOrderCheckList> checkList) {
        // TODO: 사용자 사번 조회
        String terminatorEmployeeNo = "완료자";

        WorkOrderRDBEntity rdb = getRDBWorkOrderById(id);
        saveData(rdb, checkList);
        rdb.updateTerminatorEmployeeNo(terminatorEmployeeNo);
    }

    @Transactional
    public void updateStatus(List<UpdateStatusItem> list) {
        for (UpdateStatusItem item : list) {
            WorkOrderRDBEntity workOrder = getRDBWorkOrderById(item.id());
            if(workOrder.getStatus() == WorkOrderStatus.FINISH) continue; // 이미 종료된 wo는 수정 불가

            workOrder.updateStatus(item.status());
        }
    }

    // List와 Page의 공통 상위 객체인 Iterable 사용
    private List<WorkOrderItem> mapToWorkOrderItems(Iterable<WorkOrderRDBEntity> infos) {
        List<WorkOrderItem> items = new ArrayList<>();

        for (WorkOrderRDBEntity info : infos) {
            // TODO: 사용자 연결 완료시 실제 사용자 이름으로 대체
            String creator = info.getCreatorEmployeeNo();
            String terminator = info.getTerminatorEmployeeNo();

            items.add(WorkOrderItem.from(info, creator, terminator));
        }
        return items;
    }

    private void saveData(WorkOrderRDBEntity rdb, List<WorkOrderCheckList> updateCheckList) {
        WorkOrderNosqlEntity nosql;

        if(rdb.getCheckListId() != null){
            nosql = getNosqlWorkOrderCheckList(rdb.getCheckListId());
            nosql.updateCheckList(updateCheckList);
        }else{
            String uuid = UUID.randomUUID().toString();
            nosql = WorkOrderNosqlEntity.from(uuid, false, updateCheckList);
            rdb.updatedCheckListId(uuid);
        }
        workOrderNosqlRepository.save(nosql);
        rdb.updateDate();
    }

    private JigItemResponseDto getJigItem(String serialNo) {
        return jigItemClient.findBySerialNo(serialNo).getResult();
    }

    private WorkOrderRDBEntity getRDBWorkOrderById(Long id) {
        return workOrderRDBRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException(String.format("id : %d 로 Work order를 찾을 수 없습니다.", id)));
    }

    private WorkOrderNosqlEntity getNosqlWorkOrderCheckList(String checkListId) {
        return workOrderNosqlRepository.findById(checkListId)
                .orElseThrow(()-> new IllegalArgumentException(String.format("id : %s 로 Work order CheckList를 찾을 수 없습니다.", checkListId)));
    }
}
