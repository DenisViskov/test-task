package com.rincentral.test.services;

import com.rincentral.test.models.CarInfo;
import com.rincentral.test.models.external.ExternalBrand;
import com.rincentral.test.models.external.ExternalCar;
import com.rincentral.test.models.external.ExternalCarInfo;
import com.rincentral.test.models.params.CarRequestParameters;
import com.rincentral.test.persistence.CrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchByCarParametersServiceImpl implements SearchByParametersService<CarInfo, CarRequestParameters> {

    @Qualifier("externalCarInfoRepository")
    private final CrudRepository externalCarInfoRepository;
    @Qualifier("externalBrandRepository")
    private final CrudRepository externalBrandRepository;
    @Qualifier("externalCarRepository")
    private final CrudRepository externalCarRepository;

    @Override
    public List<CarInfo> searchByParameters(CarRequestParameters parameters) {
        Set<ExternalBrand> country = (Set<ExternalBrand>)externalBrandRepository.getAll()
                                                                                .stream()
                                                                                .filter(carInfo -> parameters.getCountry() != null
                                                                                        && ((ExternalBrand)carInfo)
                                                                                        .getCountry()
                                                                                        .equals(parameters.getCountry()))
                                                                                .collect(Collectors.toSet());

        Set<ExternalCar> segment = (Set<ExternalCar>)externalCarRepository.getAll()
                                                                          .stream()
                                                                          .filter(externalCar -> parameters.getSegment() != null
                                                                                  && ((ExternalCar)externalCar)
                                                                                  .getSegment().equals(parameters.getSegment()))
                                                                          .collect(Collectors.toSet());
        return null;
    }
}
