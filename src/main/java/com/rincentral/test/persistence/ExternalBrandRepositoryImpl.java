package com.rincentral.test.persistence;

import com.rincentral.test.models.external.ExternalBrand;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ExternalBrandRepositoryImpl implements CrudRepository<ExternalBrand> {

    private final Map<Integer, ExternalBrand> storage = new HashMap<>();

    @Override
    public void add(@NonNull ExternalBrand some) {
        if (!storage.containsKey(some.getId())) {
            storage.put(some.getId(), some);
        }
    }

    @Override
    public boolean update(@NonNull ExternalBrand some) {
        if (!storage.containsKey(some.getId())) {
            return false;
        }
        storage.put(some.getId(), some);
        return true;
    }

    @Override
    public boolean delete(@NonNull ExternalBrand some) {
        if (!storage.containsKey(some.getId())) {
            return false;
        }
        storage.remove(some.getId());
        return true;
    }

    @Override
    public ExternalBrand get(int id) {
        return storage.get(id);
    }

    @Override
    public Set<ExternalBrand> getAll() {
        return new HashSet<>(storage.values());
    }
}
