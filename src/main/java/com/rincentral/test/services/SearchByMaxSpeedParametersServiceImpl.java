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

import static java.util.Objects.nonNull;

@Service
public class SearchByMaxSpeedParametersServiceImpl implements SearchByParametersService<Double, MaxSpeedRequestParameters> {

    @Autowired
    @Qualifier("externalCarInfoRepository")
    private CrudRepository<ExternalCarInfo> externalCarInfoRepository;

    @Autowired
    @Qualifier("externalBrandRepository")
    private CrudRepository<ExternalBrand> externalBrandRepository;

    @Autowired
    @Qualifier("validatorByMaxSpeedParametersServiceImpl")
    private ValidatorService<MaxSpeedRequestParameters> validatorService;

    @Override
    public Double searchByParameters(MaxSpeedRequestParameters parameters) throws RequestParametersException {
        validatorService.validate(parameters);
        return nonNull(parameters.getBrand()) ? getAverageByBrand(parameters)
                                              : getAverageByModel(parameters);
    }

    private Double getAverageByModel(MaxSpeedRequestParameters parameters) {
        return externalCarInfoRepository.getAll()
                                        .stream()
                                        .filter(externalCarInfo -> externalCarInfo.getModel().equals(parameters.getModel()))
                                        .mapToDouble(ExternalCarInfo::getMaxSpeed)
                                        .average().stream().map(Math::ceil)
                                        .findFirst()
                                        .orElseGet(() -> 0.0);
    }

    private Double getAverageByBrand(MaxSpeedRequestParameters parameters) {
        return externalBrandRepository
                .getAll()
                .stream()
                .filter(externalBrand -> externalBrand.getTitle().equals(parameters.getBrand()))
                .map(ExternalBrand::getId)
                .distinct()
                .flatMap(id -> externalCarInfoRepository.getAll()
                                                        .stream()
                                                        .filter(externalCarInfo -> externalCarInfo.getBrandId().equals(id)))
                .mapToDouble(ExternalCarInfo::getMaxSpeed)
                .average().stream().map(Math::ceil)
                .findFirst()
                .orElseGet(() -> 0.0);
    }
}
