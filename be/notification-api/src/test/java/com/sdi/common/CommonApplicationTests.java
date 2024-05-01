package com.sdi.common;

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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CommonApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("성공 응답")
    public void success() throws Exception {
        // given
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/v1/common/success")
                .contentType(MediaType.APPLICATION_JSON);

        // when
        ResultActions perform = mockMvc.perform(request);

        // then
        perform.andExpect(status().isOk());
    }

    @Test
    @DisplayName("성공 응답 body")
    public void successBody() throws Exception {
        // given
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/v1/common/success-body")
                .contentType(MediaType.APPLICATION_JSON);

        // when
        ResultActions perform = mockMvc.perform(request);

        // then
        String response = perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.result.data").value("데이터"))
                .andReturn().getResponse().getContentAsString();

        System.out.println("===반환 값===");
        System.out.println(response);
    }

    @Test
    @DisplayName("실패 응답")
    public void fail() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/v1/common/fail")
                .contentType(MediaType.APPLICATION_JSON);

        // when
        ResultActions perform = mockMvc.perform(request);

        // then
        perform.andExpect(status().isInternalServerError());
    }
}
