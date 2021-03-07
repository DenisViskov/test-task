package com.rincentral.test.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
public class CarFullInfo extends CarInfo{
    @JsonProperty("body_characteristics")
    private final BodyCharacteristics bodyCharacteristics;

    @JsonProperty("engine_characteristics")
    private final EngineCharacteristics engineCharacteristics;
}
