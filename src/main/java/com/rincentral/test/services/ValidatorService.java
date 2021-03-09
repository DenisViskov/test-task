package com.rincentral.test.services;

import com.rincentral.test.exceptions.RequestParametersException;

public interface ValidatorService<T> {
    void validate(T request) throws RequestParametersException;
}
