package com.rincentral.test.persistence;

import com.rincentral.test.models.external.ExternalBrand;
import com.rincentral.test.models.external.ExternalCar;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

public class ExternalCarRepositoryImpl implements CrudRepository<ExternalCar>{

    private final Map<Integer, ExternalCar> storage = new HashMap<>();

    @Override
    public void add(@NonNull ExternalCar some) {
        if (!storage.containsKey(some.getId())) {
            storage.put(some.getId(), some);
        }
    }

    @Override
    public boolean update(@NonNull ExternalCar some) {
        if (!storage.containsKey(some.getId())) {
            return false;
        }
        storage.put(some.getId(), some);
        return true;
    }

    @Override
    public boolean delete(@NonNull ExternalCar some) {
        if (!storage.containsKey(some.getId())) {
            return false;
        }
        storage.remove(some.getId());
        return true;
    }

    @Override
    public ExternalCar get(int id) {
        return storage.get(id);
    }
}
