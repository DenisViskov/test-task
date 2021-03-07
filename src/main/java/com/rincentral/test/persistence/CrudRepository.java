package com.rincentral.test.persistence;

import java.util.Set;

public interface CrudRepository<T> {

    void add(T some);

    boolean update(T some);

    boolean delete(T some);

    T get(int id);

    Set<T> getAll();
}
