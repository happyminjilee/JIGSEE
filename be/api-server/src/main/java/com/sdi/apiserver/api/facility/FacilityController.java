package com.sdi.apiserver.api.facility;

import com.sdi.apiserver.api.facility.dto.request.FacilityCreateRequestDto;
import com.sdi.apiserver.api.facility.dto.response.FacilityAllResponseDto;
import com.sdi.apiserver.api.facility.dto.response.FacilityDetailResponseDto;
import com.sdi.apiserver.api.jig.dto.util.JigStatus;
import com.sdi.apiserver.util.CheckType;
import com.sdi.apiserver.util.Response;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sdi.apiserver.api.facility.dto.response.FacilityAllResponseDto.*;
import static com.sdi.apiserver.api.facility.dto.response.FacilityDetailResponseDto.*;

@RestController
@RequestMapping("/v1/facility")
public class FacilityController {

    @PostMapping()
    Response<Void> add(@RequestBody FacilityCreateRequestDto dto) {
        return Response.success();
    }

    @GetMapping("/all")
    Response<FacilityAllResponseDto> all(){
        FacilityAllResponseDto dto = new FacilityAllResponseDto(
                List.of(
                        new FacilityInfo(0L, "testAlias", "testModel", "testFacilitySerialNo"),
                        new FacilityInfo(1L, "testAlias", "testModel", "testFacilitySerialNo")
                )
        );
        return Response.success(dto);
    }

    @GetMapping()
    Response<FacilityDetailResponseDto> searchByFacilityId(@RequestParam(name = "facility-id") String facilityId) {
        FacilityDetailResponseDto dto = new FacilityDetailResponseDto(
                0L,
                "testFacilityModel",
                "testAlias",
                "testFacilitySerialNo",
                List.of(
                        new JigDetail(0L, "testJigModel", "testJigSerial", JigStatus.WAREHOUSE, "testJigLifeTime", CheckType.UBM, 3, 2),
                        new JigDetail(1L, "testJigModel2", "testJigSerial2", JigStatus.WAREHOUSE, "testJigLifeTime2", CheckType.UBM, 0, 0)
                )
        );
        return Response.success(dto);
    }
}
