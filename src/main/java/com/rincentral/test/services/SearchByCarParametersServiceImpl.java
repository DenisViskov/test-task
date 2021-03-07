package com.rincentral.test.services;

import com.rincentral.test.models.CarInfo;
import com.rincentral.test.models.external.ExternalBrand;
import com.rincentral.test.models.external.ExternalCar;
import com.rincentral.test.models.external.ExternalCarInfo;
import com.rincentral.test.models.params.CarRequestParameters;
import com.rincentral.test.persistence.CrudRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

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
        Set<ExternalBrand> filteredExternalBrand = getFilteredExternalBrand(parameters);
        Set<ExternalCar> filteredExternalCar = getFilteredExternalCar(parameters);
        Set<ExternalCarInfo> filteredExternalCarInfo = getFilteredExternalCarInfo(parameters);
        return null;
    }

    private Set<ExternalCarInfo> getFilteredExternalCarInfo(CarRequestParameters parameters) {
        Set<ExternalCarInfo> result = filteredByMinEngineDisplacement(parameters.getMinEngineDisplacement(), externalCarInfoRepository.getAll());
        result = filteredByMinEngineHorsepower(parameters.getMinEngineHorsepower(), result);
        result = filteredByMinMaxSpeed(parameters.getMinMaxSpeed(), result);
        result = filteredByYear(parameters.getYear(), result);
        result = filteredByBodyStyle(parameters.getBodyStyle(), result);
        return result;
    }

    private Set<ExternalCar> getFilteredExternalCar(CarRequestParameters parameters) {
        return (Set<ExternalCar>)externalCarRepository.getAll().stream()
                                                      .filter(externalCar -> nonNull(parameters.getSegment())
                                                              && ((ExternalCar)externalCar)
                                                              .getSegment().equals(parameters.getSegment()))
                                                      .filter(externalCar -> {
                                                          if (nonNull(parameters.getSearch())) {
                                                              return (((ExternalCar)externalCar)
                                                                      .getGeneration().equals(parameters.getSearch())
                                                                      || ((ExternalCar)externalCar)
                                                                      .getModel().equals(parameters.getSearch())
                                                                      || ((ExternalCar)externalCar)
                                                                      .getModification().equals(parameters.getSearch()));
                                                          }
                                                          return true;
                                                      })
                                                      .collect(Collectors.toSet());
    }

    private Set<ExternalBrand> getFilteredExternalBrand(CarRequestParameters parameters) {
        return (Set<ExternalBrand>)externalBrandRepository.getAll().stream()
                                                          .filter(carInfo -> nonNull(parameters.getCountry())
                                                                  && ((ExternalBrand)carInfo)
                                                                  .getCountry()
                                                                  .equals(parameters.getCountry()))
                                                          .collect(Collectors.toSet());
    }


    private Set<ExternalCarInfo> filteredByMinEngineDisplacement(Double minEngineDisplacement, Set<ExternalCarInfo> info) {
        return info.stream()
                   .filter(externalCarInfo -> {
                               if (nonNull(minEngineDisplacement)) {
                                   return externalCarInfo.getEngineDisplacement().doubleValue() >= minEngineDisplacement;
                               }
                               return true;
                           }
                   ).collect(Collectors.toSet());
    }

    private Set<ExternalCarInfo> filteredByMinEngineHorsepower(Integer minEngineHorsepower, Set<ExternalCarInfo> info) {
        return info.stream()
                   .filter(externalCarInfo -> {
                               if (nonNull(minEngineHorsepower)) {
                                   return externalCarInfo.getHp() >= minEngineHorsepower;
                               }
                               return true;
                           }
                   ).collect(Collectors.toSet());
    }

    private Set<ExternalCarInfo> filteredByMinMaxSpeed(Integer minMaxSpeed, Set<ExternalCarInfo> info) {
        return info.stream()
                   .filter(externalCarInfo -> {
                               if (nonNull(minMaxSpeed)) {
                                   return externalCarInfo.getMaxSpeed() >= minMaxSpeed;
                               }
                               return true;
                           }
                   ).collect(Collectors.toSet());
    }

    private Set<ExternalCarInfo> filteredByYear(Integer year, Set<ExternalCarInfo> info) {
        return info.stream()
                   .filter(externalCarInfo -> {
                               String[] splitYears = externalCarInfo.getYearsRange().split("-");
                               if (nonNull(year)) {
                                   Integer startYear = Integer.valueOf(splitYears[0]);
                                   Integer endYear = splitYears[1].matches("\\d+") ? Integer.valueOf(splitYears[1]) : null;
                                   return nonNull(endYear) ? year >= startYear && year <= endYear
                                                           : year >= startYear;
                               }
                               return true;
                           }
                   ).collect(Collectors.toSet());
    }

    private Set<ExternalCarInfo> filteredByBodyStyle(String bodyStyle, Set<ExternalCarInfo> info) {
        return info.stream()
                   .filter(externalCarInfo -> {
                               if (nonNull(bodyStyle)) {
                                   return externalCarInfo.getBodyStyle().equals(bodyStyle);
                               }
                               return true;
                           }
                   ).collect(Collectors.toSet());
    }
}
