package com.sdi.jig.api;

import com.sdi.jig.entity.JigItemRDBEntity;
import com.sdi.jig.repository.JigItemIOHistoryRepository;
import com.sdi.jig.repository.JigItemRDBRepository;
import com.sdi.jig.util.JigStatus;
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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @Test
    @DisplayName("지그 serial-no로 검색")
    public void findBySerialNo() throws Exception {
        // given
        String serialNo = "869db53f-e9ba-4886-aa06-52d57b5ff07d";
        JigItemRDBEntity rdb = jigItemRDBRepository.findBySerialNo(serialNo).get();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/v1/jig-item")
                .param("serial-no", serialNo)
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
        String facilityModel = "f1";
        String serialNo = "869db53f-e9ba-4886-aa06-52d57b5ff07d";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/v1/jig-item/usable")
                .param("facility-model", facilityModel)
                .param("jig-serial-no", serialNo)
                .contentType(MediaType.APPLICATION_JSON);

        // when
        ResultActions perform = mockMvc.perform(request);

        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.result.isUsable").value(false))
                .andExpect(jsonPath("$.result.data.useCount").value(1))
                .andExpect(jsonPath("$.result.data.useAccumulationTime").value("24:00:00.000"))
                .andExpect(jsonPath("$.result.data.repairCount").value(1));

    }

    @Test
    @DisplayName("지그 상태 변경")
    public void updateStatus() throws Exception {
        // given
        String serialNo = "14d51713-3eb5-4d38-af87-31ae7d4c19f3";
        JigStatus jigStatus = JigStatus.OUT;

        JSONObject body = new JSONObject();
        body.put("serialNo", serialNo);
        body.put("status", jigStatus);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/v1/jig-item/status")
                .content(body.toString())
                .contentType(MediaType.APPLICATION_JSON);

        // when
        ResultActions perform = mockMvc.perform(request);

        // then
        perform.andExpect(status().isOk());
        assertEquals(jigStatus, jigItemRDBRepository.findBySerialNo(serialNo).get().getStatus());
    }

    @Test
    @DisplayName("지그 교체")
    public void exchange() throws Exception {
        // given
        String facilitySerialNo = "s1";
        String beforeSerialNo = "db3a1720-9a95-4563-92ef-8fb3b5576b9a";
        String afterSerialNo = "872cd4a1-5dd7-47a3-81ae-13ae86d336f2";
        Long runBeforeAccumulateTime = jigItemRDBRepository.findBySerialNo(beforeSerialNo).get().getUseAccumulateTime();
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
        JigItemRDBEntity before = jigItemRDBRepository.findBySerialNo(beforeSerialNo).get();
        JigItemRDBEntity after = jigItemRDBRepository.findBySerialNo(afterSerialNo).get();

        perform.andExpect(status().isOk());
        assertEquals(JigStatus.OUT, before.getStatus());
        assertEquals(JigStatus.IN, after.getStatus());
        assertTrue(runBeforeAccumulateTime < before.getUseAccumulateTime());
        assertEquals(jigItemIOHistoryRepository.count(), (runBeforeIOHistorySize + 2));
    }
}