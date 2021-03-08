package com.rincentral.test.services;

import com.rincentral.test.models.external.ExternalCarInfo;
import com.rincentral.test.models.external.enums.FuelType;
import com.rincentral.test.persistence.CrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindAllTypesServiceImpl implements FindAllTypesService<String> {

    @Qualifier("externalCarInfoRepository")
    private final CrudRepository externalCarInfoRepository;

    @Override
    public List<String> getFuelTypes() {
        return collectResult(
                externalCarInfo -> externalCarInfo.getFuelType().name()
        );
    }

    @Override
    public List<String> getBodyStyles() {
        return collectResult(
                ExternalCarInfo::getBodyStyle
        );
    }

    @Override
    public List<String> getEngineTypes() {
        return collectResult(
                externalCarInfo -> externalCarInfo.getEngineType().name()
        );
    }

    @Override
    public List<String> getWheelDrives() {
        return collectResult(
                externalCarInfo -> externalCarInfo.getWheelDriveType().name()
        );
    }

    @Override
    public List<String> getGearboxTypes() {
        return collectResult(
                externalCarInfo -> externalCarInfo.getGearboxType().name()
        );
    }

    private List<String> collectResult(Function<ExternalCarInfo, String> mapper) {
        Set<String> result = (Set<String>)externalCarInfoRepository.getAll()
                                                                   .stream()
                                                                   .map(mapper)
                                                                   .collect(Collectors.toSet());
        return new ArrayList<>(result);
    }
}
