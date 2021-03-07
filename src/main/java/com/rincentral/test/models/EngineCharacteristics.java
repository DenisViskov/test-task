package com.rincentral.test.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rincentral.test.models.external.enums.EngineType;
import com.rincentral.test.models.external.enums.FuelType;
import lombok.Builder;

@Builder
public class EngineCharacteristics {

    @JsonProperty("engine_type")
    private final FuelType fuelType;

    @JsonProperty("engine_cylinders")
    private final EngineType engineType;

    @JsonProperty("engine_displacement")
    private final Integer engineDisplacement;

    @JsonProperty("engine_horsepower")
    private final Integer hp;
}
