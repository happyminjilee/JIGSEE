package com.sdi.work_order.application;

import com.sdi.work_order.dto.reponse.JigItemResponseDto;
import com.sdi.work_order.dto.request.WorkOrderCreateRequestDto;
import com.sdi.work_order.entity.WorkOrderNosqlEntity;
import com.sdi.work_order.entity.WorkOrderRDBEntity;
import com.sdi.work_order.repository.WorkOrderNosqlRepository;
import com.sdi.work_order.repository.WorkOrderRDBRepository;
import com.sdi.work_order.util.Response;
import com.sdi.work_order.util.WorkOrderCheckList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WorkOrderService {

    @Value("${apis.api-base-url}")
    private String apiBaseUrl;
    private final CommonRequest request;
    private final WorkOrderRDBRepository workOrderRDBRepository;
    private final WorkOrderNosqlRepository workOrderNosqlRepository;

    @Transactional
    public void create(WorkOrderCreateRequestDto dto) {
        // 사번 조회
        String employeeNo = "생성자 사번";

        // 일련번호로 jig 조회
        JigItemResponseDto jigItem = getJigItem(dto.serialNo());

        // 저장할 데이터 생성
        WorkOrderRDBEntity rdb = WorkOrderRDBEntity.from(employeeNo, jigItem.serialNo());
        WorkOrderNosqlEntity nosql = WorkOrderNosqlEntity.from(rdb.getId(), false, WorkOrderCheckList.from(jigItem.jigCheckListItem()));

        // 데이터 저장
        workOrderRDBRepository.save(rdb);
        workOrderNosqlRepository.save(nosql);
    }

    private JigItemResponseDto getJigItem(String serialNo) {
        String url = String.format("%s/v1/jig-item?serial-no=%s", apiBaseUrl, serialNo);
        ResponseEntity<Response<JigItemResponseDto>> jigItem = request.get(url);

        return Optional.ofNullable(jigItem.getBody())
                .orElseThrow(() -> new IllegalArgumentException(String.format("%s에 해당하는 Jig Item을 검색할 수 없습니다.", serialNo)))
                .getResult();
    }
}
