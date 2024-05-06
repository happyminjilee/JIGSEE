package com.sdi.jig.api;

import com.sdi.jig.entity.rdb.FacilityItemRDBEntity;
import com.sdi.jig.repository.rdb.FacilityItemRDBRepository;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FacilityItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FacilityItemRDBRepository facilityItemRDBRepository;

    @Test
    @DisplayName("전체 조회")
    public void all() throws Exception {
        // given
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/v1/facility-item/all")
                .contentType(MediaType.APPLICATION_JSON);

        // when
        ResultActions perform = mockMvc.perform(request);

        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.result.list").isArray());
    }

    @Test
    @DisplayName("상세 조회")
    public void detail() throws Exception {
        // given
        FacilityItemRDBEntity first = facilityItemRDBRepository.findAll().getFirst();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/v1/facility-item")
                .param("facility-id", String.valueOf(first.getId()))
                .contentType(MediaType.APPLICATION_JSON);

        // when
        ResultActions perform = mockMvc.perform(request);

        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.result.facilitySerialNo").value(first.getSerialNo()));

    }
}