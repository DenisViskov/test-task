package com.rincentral.test.services;

import com.rincentral.test.exceptions.RequestParametersException;
import com.rincentral.test.models.external.ExternalBrand;
import com.rincentral.test.models.external.ExternalCarInfo;
import com.rincentral.test.models.params.MaxSpeedRequestParameters;
import com.rincentral.test.persistence.CrudRepository;
import com.rincentral.test.services.interfaces.SearchByParametersService;
import com.rincentral.test.services.interfaces.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Service
public class SearchByMaxSpeedParametersServiceImpl implements SearchByParametersService<Double, MaxSpeedRequestParameters> {

    @Autowired
    @Qualifier("externalCarInfoRepository")
    private CrudRepository externalCarInfoRepository;

    @Autowired
    @Qualifier("externalBrandRepository")
    private CrudRepository externalBrandRepository;

    @Autowired
    @Qualifier("validatorByMaxSpeedParametersServiceImpl")
    private ValidatorService validatorService;

    @Override
    public Double searchByParameters(MaxSpeedRequestParameters parameters) throws RequestParametersException {
        validatorService.validate(parameters);
        return nonNull(parameters.getBrand()) ? getAverageByBrand(parameters)
                                              : getAverageByModel(parameters);
    }

    private Double getAverageByModel(MaxSpeedRequestParameters parameters) {
        return externalCarInfoRepository.getAll()
                                        .stream()
                                        .filter(externalCarInfo -> ((ExternalCarInfo)externalCarInfo).getModel().equals(parameters.getModel()))
                                        .mapToDouble(externalCarInfo -> ((ExternalCarInfo)externalCarInfo).getMaxSpeed())
                                        .average().stream().map(value -> Math.ceil(value))
                                        .findFirst()
                                        .orElseGet(() -> 0.0);
    }

    private Double getAverageByBrand(MaxSpeedRequestParameters parameters) {
        return externalBrandRepository
                .getAll()
                .stream()
                .filter(externalBrand -> ((ExternalBrand)externalBrand).getTitle().equals(parameters.getBrand()))
                .map(externalBrand -> ((ExternalBrand)externalBrand).getId())
                .distinct()
                .flatMap(id -> externalCarInfoRepository.getAll()
                                                        .stream()
                                                        .filter(externalCarInfo -> ((ExternalCarInfo)externalCarInfo).getBrandId().equals(id)))
                .mapToDouble(externalCarInfo -> ((ExternalCarInfo)externalCarInfo).getMaxSpeed())
                .average().stream().map(value -> Math.ceil(value))
                .findFirst()
                .orElseGet(() -> 0.0);
    }
}
