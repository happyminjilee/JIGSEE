package com.sdi.apiserver.api.facility.client;

import com.sdi.apiserver.api.facility.dto.response.FacilityAllResponseDto;
import com.sdi.apiserver.api.facility.dto.response.FacilityDetailResponseDto;
import com.sdi.apiserver.util.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "jigItemClient", url = "${apis.facility-item-base-url}")
public interface FacilityItemClient {

    @GetMapping("/all")
    FacilityAllResponseDto all();

    @GetMapping
    FacilityDetailResponseDto searchByFacilityId(@RequestParam(name = "facility-id") String facilityId);
}
