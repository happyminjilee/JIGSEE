//package com.sdi.jig.api;
//
//import com.sdi.jig.entity.rdb.FacilityItemRDBEntity;
//import com.sdi.jig.entity.rdb.JigItemRDBEntity;
//import com.sdi.jig.repository.rdb.FacilityItemRDBRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//@Transactional
//class FacilityItemControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private FacilityItemRDBRepository facilityItemRDBRepository;
//
//    @Test
//    @DisplayName("전체 조회")
//    void all() throws Exception {
//        // given
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
//                .get("/v1/facility-item/all")
//                .contentType(MediaType.APPLICATION_JSON);
//
//        // when
//        ResultActions perform = mockMvc.perform(request);
//
//        // then
//        perform.andExpect(status().isOk())
//                .andExpect(jsonPath("$.result.list").isArray());
//    }
//
//    @Test
//    @DisplayName("상세 조회")
//    void detail() throws Exception {
//        // given
//        FacilityItemRDBEntity first = facilityItemRDBRepository.findAll().get(0);
//
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
//                .get("/v1/facility-item")
//                .param("facility-id", String.valueOf(first.getId()))
//                .contentType(MediaType.APPLICATION_JSON);
//
//        // when
//        ResultActions perform = mockMvc.perform(request);
//
//        // then
//        perform.andExpect(status().isOk())
//                .andExpect(jsonPath("$.result.facilitySerialNo").value(first.getSerialNo()));
//
//    }
//
//    @Test
//    @DisplayName("점검이 필요한 시설 조회")
//    void inspection() throws Exception {
//        // given
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
//                .get("/v1/facility-item/inspection")
//                .contentType(MediaType.APPLICATION_JSON);
//
//        // when
//        ResultActions perform = mockMvc.perform(request);
//
//        // then
//        perform.andExpect(status().isOk())
//                .andExpect(jsonPath("$.result").exists());
//    }
//
//    @Test
//    @DisplayName("점검이 필요한 지그 항목 조회")
//     void inspectionJigItems() throws Exception {
//        // given
//        FacilityItemRDBEntity first = facilityItemRDBRepository.findAll().get(0);
//
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
//                .get("/v1/facility-item/inspection/jig-item")
//                .param("facility-id", String.valueOf(first.getId()))
//                .contentType(MediaType.APPLICATION_JSON);
//
//        // when
//        ResultActions perform = mockMvc.perform(request);
//
//        // then
//        if (first.getJigItems() == null) {
//            perform.andExpect(status().isOk())
//                    .andExpect(jsonPath("$.result.serialNos").isEmpty());
//        } else {
//            List<String> serialNos = first.getJigItems().stream()
//                    .map(JigItemRDBEntity::getSerialNo)
//                    .collect(Collectors.toList());
//
//            perform.andExpect(status().isOk())
//                    .andExpect(jsonPath("$.result.serialNos").value(serialNos));
//        }
//    }
//}