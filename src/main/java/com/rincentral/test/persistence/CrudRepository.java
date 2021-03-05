package com.rincentral.test.persistence;

public interface CrudRepository<T> {

    void add(T some);

    boolean update(T some);

    boolean delete(T some);

    T get(int id);
}
