package com.sdi.apiserver.api.facility.client;

import com.sdi.apiserver.api.facility.FacilityItemInspectionResponseDto;
import com.sdi.apiserver.api.facility.dto.response.FacilityAllResponseDto;
import com.sdi.apiserver.api.facility.dto.response.FacilityDetailResponseDto;
import com.sdi.apiserver.api.facility.dto.response.FacilityItemInspectionJigItemsResponseDto;
import com.sdi.apiserver.util.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "facilityItemClient", url = "${apis.facility-item-base-url}")
public interface FacilityItemClient {

    @GetMapping("/all")
    Response<FacilityAllResponseDto> all();

    @GetMapping
    Response<FacilityDetailResponseDto> searchByFacilityId(@RequestParam(name = "facility-id") String facilityId);

    @GetMapping("/inspection")
    Response<FacilityItemInspectionResponseDto> inspection();

    @GetMapping("/inspection/jig-item")
    Response<FacilityItemInspectionJigItemsResponseDto> inspectionJigItem(@RequestParam(name = "facility-id") String facilityId);
}
