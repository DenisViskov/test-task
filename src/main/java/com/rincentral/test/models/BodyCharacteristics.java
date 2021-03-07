package com.rincentral.test.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class BodyCharacteristics {

    @JsonProperty("body_length")
    private final Integer bodyLength;

    @JsonProperty("body_width")
    private final Integer bodyWidth;

    @JsonProperty("body_height")
    private final Integer bodyHeight;

    @JsonProperty("body_style")
    private final String bodyStyle;
}
