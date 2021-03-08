package com.rincentral.test.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class CarFullInfo extends CarInfo{
    @JsonProperty("body_characteristics")
    private final BodyCharacteristics bodyCharacteristics;

    @JsonProperty("engine_characteristics")
    private final EngineCharacteristics engineCharacteristics;
}
