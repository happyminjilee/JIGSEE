package com.sdi.work_order.application;

import com.sdi.work_order.client.JigItemClient;
import com.sdi.work_order.client.request.JigItemDeleteRequestDto;
import com.sdi.work_order.client.request.JigItemRepairRequestDto;
import com.sdi.work_order.client.response.JigItemResponseDto;
import com.sdi.work_order.dto.request.JigItemDeleteAndRepairRequestDto;
import com.sdi.work_order.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JigItemService {

    private final JigItemClient jigItemClient;

    public Response<JigItemResponseDto> findBySerialNo(String accessToken, String serialNo){
        return jigItemClient.findBySerialNo(accessToken, serialNo);
    }

    public Response<JigItemResponseDto> findBySerialNoIncludeDelete(String accessToken, String serialNo){
        return jigItemClient.findBySerialNoIncludeDelete(accessToken, serialNo);
    }

    public void deleteBySerialNo(String accessToken, String serialNo){
        jigItemClient.deleteBySerialNo(accessToken, JigItemDeleteRequestDto.from(serialNo));
    }

    public void repair(String serialNo){
        jigItemClient.repair(JigItemRepairRequestDto.from(serialNo));
    }

    public void deleteAndRepair(String serialNo, Boolean isAllPass){
        jigItemClient.deleteAndRepair(JigItemDeleteAndRepairRequestDto.of(serialNo, isAllPass));
    }
}
