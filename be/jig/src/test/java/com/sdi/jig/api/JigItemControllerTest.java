package com.sdi.jig.api;

import com.sdi.jig.dto.request.JigItemInventoryRequestDto;
import com.sdi.jig.entity.rdb.FacilityItemRDBEntity;
import com.sdi.jig.entity.rdb.FacilityRDBEntity;
import com.sdi.jig.entity.rdb.JigItemRDBEntity;
import com.sdi.jig.entity.rdb.JigRDBEntity;
import com.sdi.jig.repository.rdb.*;
import com.sdi.jig.util.JigStatus;
import com.sdi.jig.util.TimeCalculator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
class JigItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JigItemRDBRepository jigItemRDBRepository;

    @Autowired
    private JigItemIOHistoryRepository jigItemIOHistoryRepository;

    @Autowired
    private FacilityItemRDBRepository facilityItemRDBRepository;

    @Autowired
    private FacilityJigMappingRDBRepository facilityJigMappingRDBRepository;

    @Autowired
    private FacilityRDBRepository facilityRDBRepository;

    @Test
    @DisplayName("지그 serial-no로 검색")
    public void findBySerialNo() throws Exception {
        // given
        JigItemRDBEntity rdb = jigItemRDBRepository.findAll().getFirst();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/v1/jig-item")
                .param("serial-no", rdb.getSerialNo())
                .contentType(MediaType.APPLICATION_JSON);

        // when
        ResultActions perform = mockMvc.perform(request);

        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.result.id").value(rdb.getId()))
                .andExpect(jsonPath("$.result.model").value(rdb.getJig().getModel()))
                .andExpect(jsonPath("$.result.serialNo").value(rdb.getSerialNo()))
                .andExpect(jsonPath("$.result.status").value(rdb.getStatus().toString()))
                .andExpect(jsonPath("$.result.expectLife").value(rdb.getJig().getExpectLife()))
                .andExpect(jsonPath("$.result.checkList").isArray());
    }

    @Test
    @DisplayName("지그 재고 추가")
    public void add() throws Exception {
        // given
        String addModel1 = "testModelId";
        String addModel2 = "testModelId2";
        int beforeSize1 = jigItemRDBRepository.findByJigModel(addModel1).size();
        int beforeSize2 = jigItemRDBRepository.findByJigModel(addModel2).size();

        JSONArray serialNos1 = new JSONArray();
        serialNos1.put(UUID.randomUUID());
        serialNos1.put(UUID.randomUUID());
        JSONArray serialNos2 = new JSONArray();
        serialNos2.put(UUID.randomUUID());
        serialNos2.put(UUID.randomUUID());

        JSONObject model1 = new JSONObject();
        model1.put("model", addModel1);
        model1.put("serialNos", serialNos1);
        JSONObject model2 = new JSONObject();
        model2.put("model", addModel2);
        model2.put("serialNos", serialNos2);

        JSONArray list = new JSONArray();
        list.put(model1);
        list.put(model2);

        JSONObject body = new JSONObject();
        body.put("list", list);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/v1/jig-item")
                .content(body.toString())
                .contentType(MediaType.APPLICATION_JSON);

        // when
        ResultActions perform = mockMvc.perform(request);

        // then
        perform.andExpect(status().isOk());
        assertEquals(beforeSize1 + serialNos1.length(), jigItemRDBRepository.findByJigModel(addModel1).size());
        assertEquals(beforeSize2 + serialNos2.length(), jigItemRDBRepository.findByJigModel(addModel2).size());
    }

    @Test
    @DisplayName("지그 사용 가능 확인")
    public void isUsable() throws Exception {
        // given
        String facilitySerialNo = "fs1";
        String serialNo = "js1";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/v1/jig-item/usable")
                .param("facility-serial-no", facilitySerialNo)
                .param("jig-serial-no", serialNo)
                .contentType(MediaType.APPLICATION_JSON);

        // when
        ResultActions perform = mockMvc.perform(request);

        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.result.isUsable").value(false))
                .andExpect(jsonPath("$.result.data.useCount").value(5))
                .andExpect(jsonPath("$.result.data.useAccumulationTime").value(TimeCalculator.millsToString(12789515060L)))
                .andExpect(jsonPath("$.result.data.repairCount").value(1));

    }

    @Test
    @DisplayName("JIG ITEM 폐기")
    public void delete() throws Exception {
        // given
        JigItemRDBEntity jigItemRDB = jigItemRDBRepository.findAll().getFirst();
        String serialNo = jigItemRDB.getSerialNo();
        JSONObject body = new JSONObject();
        body.put("serialNo", serialNo);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/v1/jig-item")
                .content(body.toString())
                .contentType(MediaType.APPLICATION_JSON);

        // when
        ResultActions perform = mockMvc.perform(request);

        // then
        perform.andExpect(status().isOk());
        assertTrue(jigItemRDBRepository.findBySerialNo(serialNo).get().getIsDelete());
    }

    @Test
    @DisplayName("지그 상태 변경")
    public void updateStatus() throws Exception {
        // given
        JigItemRDBEntity jigItemRDB = jigItemRDBRepository.findAll().getFirst();
        JigStatus jigStatus = JigStatus.OUT;

        JSONObject body = new JSONObject();
        body.put("serialNo", jigItemRDB.getSerialNo());
        body.put("status", jigStatus);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/v1/jig-item/status")
                .content(body.toString())
                .contentType(MediaType.APPLICATION_JSON);

        // when
        ResultActions perform = mockMvc.perform(request);

        // then
        perform.andExpect(status().isOk());
        assertEquals(jigStatus, jigItemRDBRepository.findBySerialNo(jigItemRDB.getSerialNo()).get().getStatus());
    }

    @Test
    @DisplayName("지그 교체")
    public void exchange() throws Exception {
        // given
        FacilityItemRDBEntity facilityItemRDBEntity = facilityItemRDBRepository.findAll().getFirst();
        String facilitySerialNo = facilityItemRDBEntity.getSerialNo();

        String beforeSerialNo = facilityItemRDBEntity.getJigItems().getFirst().getSerialNo();

        FacilityRDBEntity facilityRDBEntity = facilityItemRDBEntity.getFacility();
        JigRDBEntity jigRDBEntity = facilityJigMappingRDBRepository.findByFacilityId(facilityRDBEntity.getId()).getFirst().getJig();
        String afterSerialNo = jigItemRDBRepository.findByJigModel(jigRDBEntity.getModel()).getLast().getSerialNo();

        Long runBeforeAccumulateTime = jigItemRDBRepository.findBySerialNoAndIsDeleteFalse(beforeSerialNo).get().getUseAccumulateTime();
        long runBeforeIOHistorySize = jigItemIOHistoryRepository.count();

        JSONObject body = new JSONObject();
        body.put("facilitySerialNo", facilitySerialNo);
        body.put("beforeSerialNo", beforeSerialNo);
        body.put("afterSerialNo", afterSerialNo);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/v1/jig-item/exchange")
                .content(body.toString())
                .contentType(MediaType.APPLICATION_JSON);

        // when
        ResultActions perform = mockMvc.perform(request);

        // then
        JigItemRDBEntity before = jigItemRDBRepository.findBySerialNoAndIsDeleteFalse(beforeSerialNo).get();
        JigItemRDBEntity after = jigItemRDBRepository.findBySerialNoAndIsDeleteFalse(afterSerialNo).get();

        perform.andExpect(status().isOk());
        assertEquals(JigStatus.OUT, before.getStatus());
        assertEquals(JigStatus.IN, after.getStatus());
        assertTrue(runBeforeAccumulateTime < before.getUseAccumulateTime());
        assertEquals(jigItemIOHistoryRepository.count(), (runBeforeIOHistorySize + 2));
    }

    @Test
    @DisplayName("지그 폐기 취소")
    public void recovery() throws Exception {
        // given
        JigItemRDBEntity jig = jigItemRDBRepository.findAll().stream()
                .filter(i -> i.getIsDelete() == true)
                .findFirst().get();

        JSONObject body = new JSONObject();
        body.put("serialNo", jig.getSerialNo());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/v1/jig-item/recovery")
                .content(body.toString())
                .contentType(MediaType.APPLICATION_JSON);

        // when
        ResultActions perform = mockMvc.perform(request);

        // then
        Boolean result = jig.getIsDelete();
        perform.andExpect(status().isOk());
        assertEquals(false, result);
    }

    @Test
    @DisplayName("설비에 가능한 지그 검색")
    public void facilityAvailable() throws Exception {
        // given
        FacilityRDBEntity facility = facilityRDBRepository.findAll().getFirst();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/v1/jig-item/facility-available")
                .param("facility-model", facility.getModel())
                .contentType(MediaType.APPLICATION_JSON);

        // when
        ResultActions perform = mockMvc.perform(request);

        // then
        List<String> list = facilityJigMappingRDBRepository.findByFacilityId(facility.getId()).stream()
                .flatMap(i -> jigItemRDBRepository.findByJigModel(i.getJig().getModel()).stream())
                .filter(i -> i.getStatus() != JigStatus.IN && i.getStatus() != JigStatus.REPAIR && i.getStatus() != JigStatus.READY)
                .map(i -> i.getSerialNo())
                .collect(Collectors.toList());

        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.result.list").value(list));
    }
}