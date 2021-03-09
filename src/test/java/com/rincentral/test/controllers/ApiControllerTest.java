package com.rincentral.test.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rincentral.test.TestApplication;
import com.rincentral.test.models.BodyCharacteristics;
import com.rincentral.test.models.CarFullInfo;
import com.rincentral.test.models.EngineCharacteristics;
import com.rincentral.test.models.external.enums.EngineType;
import com.rincentral.test.models.external.enums.FuelType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;

import static com.rincentral.test.controllers.params.QueryParams.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
        CarFullInfo carFullExpected = CarFullInfo.builder()
                                                 .id(15)
                                                 .segment("B-segment")
                                                 .brand("Skoda")
                                                 .model("Rapid")
                                                 .country("Czech Republic")
                                                 .generation("I generation")
                                                 .modification("1.4 TSI")
                                                 .bodyCharacteristics(
                                                         BodyCharacteristics.builder()
                                                                            .bodyHeight(1474)
                                                                            .bodyLength(4483)
                                                                            .bodyStyle("Hatchback")
                                                                            .bodyWidth(1706)
                                                                            .build()
                                                 )
                                                 .engineCharacteristics(
                                                         EngineCharacteristics.builder()
                                                                              .engineDisplacement(1395)
                                                                              .engineType(EngineType.L4)
                                                                              .fuelType(FuelType.GASOLINE)
                                                                              .hp(125)
                                                                              .build()
                                                 )
                                                 .build();

        mockMvc.perform(get("/api/cars")
                .queryParam(COUNTRY.getParam(), "Czech Republic")
                .queryParam(SEGMENT.getParam(), "B-segment")
                .queryParam(YEAR.getParam(), "2015")
                .queryParam(MIN_ENGINE_DISPLACEMENT.getParam(), "1200")
                .queryParam(MIN_ENGINE_HORSE_POWER.getParam(), "86")
                .queryParam(MIN_MAX_SPEED.getParam(), "200")
                .queryParam(SEARCH.getParam(), "1.4 TSI")
                .queryParam(IS_FULL.getParam(), "true")
                .queryParam(BODY_STYLE.getParam(), "Hatchback"))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(carFullExpected))));

        // Expected error message
        mockMvc.perform(get("/api/cars")
                .queryParam(SEGMENT.getParam(), "B-segment")
                .queryParam(YEAR.getParam(), "someWord"))
               .andExpect(status().is4xxClientError())
               .andExpect(content().string("Wrong given parameters"));
    }

    @Test
    void getFuelTypes() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/fuel-types"))
                                     .andExpect(status().isOk())
                                     .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString())
                .contains("GASOLINE");
    }

    @Test
    void getBodyStyles() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/body-styles"))
                                     .andExpect(status().isOk())
                                     .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString())
                .contains("Sedan,  Hatchback")
                .contains("Hatchback")
                .contains("Sedan")
                .contains("Sedan,  Hatchback,  Wagon")
                .contains("Hathback")
                .contains("Sedan,  Wagon");
    }

    @Test
    void getEngineTypes() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/engine-types"))
                                     .andExpect(status().isOk())
                                     .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString())
                .contains("L4");
    }

    @Test
    void getWheelDrives() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/wheel-drives"))
                                     .andExpect(status().isOk())
                                     .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString())
                .contains("FWD");
    }

    @Test
    void getGearboxTypes() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/gearboxes"))
                                     .andExpect(status().isOk())
                                     .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString())
                .contains("AUTO")
                .contains("ROBOTIC")
                .contains("MANUAL");
    }

    @Test
    void getMaxSpeed() throws Exception {
        mockMvc.perform(get("/api/max-speed")
                .queryParam(MODEL.getParam(), "Picanto"))
               .andExpect(status().isOk())
               .andExpect(content().string("156"));

        mockMvc.perform(get("/api/max-speed")
                .queryParam(BRAND.getParam(), "Skoda"))
               .andExpect(status().isOk())
               .andExpect(content().string("206"));
    }
}
