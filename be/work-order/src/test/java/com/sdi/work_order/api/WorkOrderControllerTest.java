package com.sdi.work_order.api;

import com.sdi.work_order.repository.WorkOrderNosqlRepository;
import com.sdi.work_order.repository.WorkOrderRDBRepository;
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
    void grouping() {
        // given
        // when
        // then
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
        String serialNo = "testModelId2";
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
    void updateStatus() {
        // given
        // when
        // then
    }
}