package com.sdi.apiserver.api.facility;

import com.sdi.apiserver.api.facility.client.FacilityItemClient;
import com.sdi.apiserver.api.facility.dto.response.FacilityAllResponseDto;
import com.sdi.apiserver.api.facility.dto.response.FacilityDetailResponseDto;
import com.sdi.apiserver.api.facility.dto.response.FacilityItemInspectionJigItemsResponseDto;
import com.sdi.apiserver.api.facility.dto.response.FacilityItemInspectionResponseDto;
import com.sdi.apiserver.api.member.MemberController;
import com.sdi.apiserver.api.member.client.MemberClient;
import com.sdi.apiserver.util.Response;
import jakarta.servlet.http.HttpServletRequest;
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
    private final MemberController memberController;

    @GetMapping("/all")
    Response<FacilityAllResponseDto> all(HttpServletRequest request) {
        memberController.producerCheck(request);
        return facilityItemClient.all();
    }

    @GetMapping()
    Response<FacilityDetailResponseDto> searchByFacilityId(HttpServletRequest request, @RequestParam(name = "facility-id") String facilityId) {
        memberController.producerCheck(request);
        return facilityItemClient.searchByFacilityId(facilityId);
    }

    @GetMapping("/inspection")
    Response<FacilityItemInspectionResponseDto> inspection(HttpServletRequest request) {
        memberController.producerCheck(request);
        return facilityItemClient.inspection();
    }

    @GetMapping("/inspection/jig-item")
    Response<FacilityItemInspectionJigItemsResponseDto> inspectionJigItem(HttpServletRequest request, @RequestParam(name = "facility-id") String facilityId) {
        memberController.producerCheck(request);
        return facilityItemClient.inspectionJigItem(facilityId);
    }
}
