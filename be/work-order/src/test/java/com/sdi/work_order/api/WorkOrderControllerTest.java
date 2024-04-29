package com.sdi.work_order.api;

import com.sdi.work_order.repository.WorkOrderNosqlRepository;
import com.sdi.work_order.repository.WorkOrderRDBRepository;
import com.sdi.work_order.util.WorkOrderStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class WorkOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WorkOrderRDBRepository workOrderRDBRepository;

    @Autowired
    private WorkOrderNosqlRepository workOrderNosqlRepository;

    @Test
    void detail() {
        // given
        // when
        // then
    }

    @Test
    void all() {
        // given
        // when
        // then
    }

    @Test
    @DisplayName("상태 그룹핑")
    void grouping() throws Exception {
        // given
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/v1/work-order/grouping")
                .contentType(MediaType.APPLICATION_JSON);

        // when
        ResultActions perform = mockMvc.perform(request);

        // then
        perform.andExpect(status().isOk());
        String contentAsString = perform.andReturn().getResponse().getContentAsString();
        System.out.println(contentAsString);
    }

    @Test
    void findByPerson() {
        // given
        // when
        // then
    }

    @Test
    @DisplayName("WO 생성")
    void create() throws Exception {
        // given
        String serialNo = "js2";
        JSONObject body = new JSONObject();
        body.put("serialNo", serialNo);
        int beforeSizeRDB = (int) workOrderRDBRepository.count();
        int beforeSizeNosql = (int) workOrderNosqlRepository.count();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/v1/work-order")
                .content(body.toString())
                .contentType(MediaType.APPLICATION_JSON);

        // when
        ResultActions perform = mockMvc.perform(request);

        // then
        perform.andExpect(status().isOk());
        assertEquals(beforeSizeRDB + 1, (int) workOrderRDBRepository.count());
        assertEquals(beforeSizeNosql + 1, (int) workOrderNosqlRepository.count());
    }

    @Test
    void tmpSave() {
        // given
        // when
        // then
    }

    @Test
    void done() {
        // given
        // when
        // then
    }

    @Test
    void updateStatus() throws Exception {
        // given
        Long id = workOrderRDBRepository.findAll().getFirst().getId();
        WorkOrderStatus status = WorkOrderStatus.FINISH;

        JSONObject body = new JSONObject();
        JSONObject item = new JSONObject();
        item.put("id", id);
        item.put("status", status);
        JSONArray list = new JSONArray();
        list.put(item);
        body.put("list", list);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/v1/work-order/status")
                .content(body.toString())
                .contentType(MediaType.APPLICATION_JSON);

        // when
        ResultActions perform = mockMvc.perform(request);

        // then
        perform.andExpect(status().isOk());
        assertEquals(status, workOrderRDBRepository.findById(id).get().getStatus());
    }
}