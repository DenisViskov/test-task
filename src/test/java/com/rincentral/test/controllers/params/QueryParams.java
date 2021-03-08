package com.rincentral.test.controllers.params;

public enum QueryParams {
    COUNTRY("country"),
    SEGMENT("segment"),
    MIN_ENGINE_DISPLACEMENT("minEngineDisplacement"),
    MIN_ENGINE_HORSE_POWER("minEngineHorsepower"),
    MIN_MAX_SPEED("minMaxSpeed"),
    SEARCH("search"),
    IS_FULL("isFull"),
    YEAR("year"),
    BRAND("brand"),
    MODEL("model"),
    BODY_STYLE("bodyStyle");

    private final String param;

    QueryParams(String param) {
        this.param = param;
    }

    public String getParam() {
        return param;
    }
}
