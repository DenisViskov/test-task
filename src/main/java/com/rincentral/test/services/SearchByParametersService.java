package com.rincentral.test.services;

import java.util.List;

public interface SearchByParametersService<T, V> {

    List<T> searchByParameters(V parameters);
}
