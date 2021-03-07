package com.rincentral.test.persistence;

import com.rincentral.test.models.external.ExternalCarInfo;
import lombok.NonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ExternalCarInfoRepositoryImpl implements CrudRepository<ExternalCarInfo>{

    private final Map<Integer, ExternalCarInfo> storage = new HashMap<>();

    @Override
    public void add(@NonNull ExternalCarInfo some) {
        if (!storage.containsKey(some.getId())) {
            storage.put(some.getId(), some);
        }
    }

    @Override
    public boolean update(@NonNull ExternalCarInfo some) {
        if (!storage.containsKey(some.getId())) {
            return false;
        }
        storage.put(some.getId(), some);
        return true;
    }

    @Override
    public boolean delete(@NonNull ExternalCarInfo some) {
        if (!storage.containsKey(some.getId())) {
            return false;
        }
        storage.remove(some.getId());
        return true;
    }

    @Override
    public ExternalCarInfo get(int id) {
        return storage.get(id);
    }

    @Override
    public Set<ExternalCarInfo> getAll() {
        return new HashSet<>(storage.values());
    }
}
