package com.sdi.jig.api;

import com.sdi.jig.entity.nosql.JigNosqlEntity;
import com.sdi.jig.repository.rdb.JigItemRDBRepository;
import com.sdi.jig.repository.nosql.JigNosqlRepository;
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

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class JigControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JigItemRDBRepository jigItemRDBRepository;

    @Autowired
    private JigNosqlRepository jigNosqlRepository;

    @Test
    @DisplayName("모델명으로 JIG 검색")
    public void findByModel() throws Exception {
        // given
        String model = "testModelId";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(String.format("/v1/jig?model=%s", model))
                .contentType(MediaType.APPLICATION_JSON);

        // when
        ResultActions perform = mockMvc.perform(request);

        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.result.model").value(model))
                .andExpect(jsonPath("$.result.checkList").isArray());
    }

    @Test
    @DisplayName("JIG 체크리스트 수정")
    public void updateCheckList() throws Exception {
        // given
        String model = "testModelId";
        Random random = new Random();
        int num1 = random.nextInt(100);
        int num2 = random.nextInt(100);

        JSONObject item1 = new JSONObject();
        item1.put("content", num1);
        item1.put("standard", num1);
        JSONObject item2 = new JSONObject();
        item2.put("content", num2);
        item2.put("standard", num2);
        JSONArray checkList = new JSONArray();
        checkList.put(item1);
        checkList.put(item2);

        JSONObject body = new JSONObject();
        body.put("model", model);
        body.put("checkList", checkList);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/v1/jig")
                .content(body.toString())
                .contentType(MediaType.APPLICATION_JSON);

        // when
        ResultActions perform = mockMvc.perform(request);

        // then
        perform.andExpect(status().isOk());
        JigNosqlEntity entity = jigNosqlRepository.findById(model).get();
        assertEquals(item1.getString("content"), entity.getCheckList().get(0).content());
        assertEquals(item1.getString("standard"), entity.getCheckList().get(0).standard());
        assertEquals(item2.getString("content"), entity.getCheckList().get(1).content());
        assertEquals(item2.getString("standard"), entity.getCheckList().get(1).standard());
    }

    @Test
    @DisplayName("JIG ITEM 폐기")
    public void delete() throws Exception {
        // given
        String serialNo = "14d51713-3eb5-4d38-af87-31ae7d4c19f3";
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
        assertTrue(jigItemRDBRepository.findBySerialNoAndIsDeleteFalse(serialNo).get().getIsDelete());
    }
}