package com.rincentral.test.services.interfaces;

import com.rincentral.test.exceptions.RequestParametersException;

public interface SearchByParametersService<T, V> {

    T searchByParameters(V parameters) throws RequestParametersException;
}
