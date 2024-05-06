package com.sdi.apiserver.api.facility;

import com.sdi.apiserver.api.facility.client.FacilityItemClient;
import com.sdi.apiserver.api.facility.dto.response.FacilityAllResponseDto;
import com.sdi.apiserver.api.facility.dto.response.FacilityDetailResponseDto;
import com.sdi.apiserver.api.facility.dto.response.FacilityItemInspectionJigItemsResponseDto;
import com.sdi.apiserver.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/facility-item")
@RequiredArgsConstructor
class FacilityController {

    private final FacilityItemClient facilityItemClient;

    @GetMapping("/all")
    Response<FacilityAllResponseDto> all() {
        return facilityItemClient.all();
    }

    @GetMapping()
    Response<FacilityDetailResponseDto> searchByFacilityId(@RequestParam(name = "facility-id") String facilityId) {
        return facilityItemClient.searchByFacilityId(facilityId);
    }

    @GetMapping("/inspection")
    Response<FacilityItemInspectionResponseDto> inspection() {
        return facilityItemClient.inspection();
    }

    @GetMapping("/inspection/jig-item")
    Response<FacilityItemInspectionJigItemsResponseDto> inspectionJigItem(@RequestParam(name = "facility-id") String facilityId) {
        return facilityItemClient.inspectionJigItem(facilityId);
    }
}
