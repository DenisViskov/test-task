package com.rincentral.test.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rincentral.test.TestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.rincentral.test.controllers.params.QueryParams.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestApplication.class)
@AutoConfigureMockMvc
class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getCars() throws Exception {
        mockMvc.perform(get("/api/cars")
                .queryParam(COUNTRY.getParam(), "Czech Republic")
                .queryParam(SEGMENT.getParam(), "B-segment")
                .queryParam(YEAR.getParam(), "2015")
                .queryParam(MIN_ENGINE_DISPLACEMENT.getParam(), "1200")
                .queryParam(MIN_ENGINE_HORSE_POWER.getParam(), "86")
                .queryParam(MIN_MAX_SPEED.getParam(), "200")
                .queryParam(SEARCH.getParam(), "1.4 TSI")
                .queryParam(IS_FULL.getParam(), "true")
                .queryParam(BODY_STYLE.getParam(),"Hatchback"))
               .andExpect(status().isOk());

        mockMvc.perform(get("/api/cars")
                .queryParam(SEGMENT.getParam(), "B-segment"))
               .andExpect(status().isOk());
    }

    @Test
    void getFuelTypes() {
    }

    @Test
    void getBodyStyles() {
    }

    @Test
    void getEngineTypes() {
    }

    @Test
    void getWheelDrives() {
    }

    @Test
    void getGearboxTypes() {
    }

    @Test
    void getMaxSpeed() {
    }
}
