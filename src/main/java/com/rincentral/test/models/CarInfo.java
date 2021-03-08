package com.rincentral.test.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class CarInfo {
    @JsonProperty("id")
    private final Integer id;

    @JsonProperty("segment")
    private final String segment;

    @JsonProperty("brand")
    private final String brand;

    @JsonProperty("model")
    private final String model;

    @JsonProperty("country")
    private final String country;

    @JsonProperty("generation")
    private final String generation;

    @JsonProperty("modification")
    private final String modification;
}
