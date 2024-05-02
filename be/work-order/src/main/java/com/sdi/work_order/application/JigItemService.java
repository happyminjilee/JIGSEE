package com.sdi.work_order.application;

import com.sdi.work_order.client.JigItemClient;
import com.sdi.work_order.client.response.JigItemResponseDto;
import com.sdi.work_order.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JigItemService {

    private final JigItemClient jigItemClient;

    public Response<JigItemResponseDto> findBySerialNo(String serialNo){
        return jigItemClient.findBySerialNo(serialNo);
    }
}
