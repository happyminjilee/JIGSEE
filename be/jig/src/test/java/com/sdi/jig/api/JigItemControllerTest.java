package com.sdi.jig.api;

import com.sdi.jig.repository.JigItemRDBRepository;
import org.json.JSONArray;
import org.json.JSONException;
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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class JigItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JigItemRDBRepository jigItemRDBRepository;

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
}