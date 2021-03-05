package com.rincentral.test.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rincentral.test.TestApplication;
import com.rincentral.test.models.params.CarRequestParameters;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        CarRequestParameters carRequestParameters = new CarRequestParameters();
        carRequestParameters.setCountry("country");
        carRequestParameters.setSegment("segment");
        carRequestParameters.setYear(1);
        mockMvc.perform(get("/api/cars")
                .queryParam(carRequestParameters.getCountry(), carRequestParameters.getCountry())
                .queryParam(carRequestParameters.getSegment(), carRequestParameters.getSegment())
                .queryParam("year", String.valueOf(carRequestParameters.getYear())))
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
