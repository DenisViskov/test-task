package com.rincentral.test.services;

import com.rincentral.test.exceptions.RequestParametersException;
import com.rincentral.test.models.BodyCharacteristics;
import com.rincentral.test.models.CarFullInfo;
import com.rincentral.test.models.CarInfo;
import com.rincentral.test.models.EngineCharacteristics;
import com.rincentral.test.models.external.ExternalBrand;
import com.rincentral.test.models.external.ExternalCarInfo;
import com.rincentral.test.models.params.CarRequestParameters;
import com.rincentral.test.persistence.CrudRepository;
import com.rincentral.test.services.interfaces.SearchByParametersService;
import com.rincentral.test.services.interfaces.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
public class SearchByCarParametersServiceImpl implements SearchByParametersService<List<CarInfo>, CarRequestParameters> {

    @Autowired
    @Qualifier("externalCarInfoRepository")
    private CrudRepository externalCarInfoRepository;

    @Autowired
    @Qualifier("externalBrandRepository")
    private CrudRepository externalBrandRepository;

    @Autowired
    @Qualifier("validatorByCarParametersServiceImpl")
    private ValidatorService validatorService;

    @Override
    public List<CarInfo> searchByParameters(CarRequestParameters parameters) throws RequestParametersException {
        validatorService.validate(parameters);
        Set<ExternalBrand> filteredExternalBrand = getFilteredExternalBrand(parameters);
        Set<ExternalCarInfo> filteredExternalCarInfo = getFilteredExternalCarInfo(parameters);
        return collectCarInfo(filteredExternalBrand,
                filteredExternalCarInfo,
                Optional.ofNullable(parameters.getIsFull()).orElse(false));
    }

    private List<CarInfo> collectCarInfo(Set<ExternalBrand> filteredExternalBrand, Set<ExternalCarInfo> filteredExternalCarInfo, boolean isFull) {
        List<CarInfo> result = new ArrayList<>();
        filteredExternalCarInfo.forEach(externalCarInfo -> {
            filteredExternalBrand.forEach(externalBrand -> {
                if (externalCarInfo.getBrandId().equals(externalBrand.getId())) {
                    result.add(isFull ? buildCarFullInfo(externalBrand, externalCarInfo)
                                      : buildCarInfo(externalBrand, externalCarInfo));
                }
            });
        });
        return result;
    }

    private Set<ExternalCarInfo> getFilteredExternalCarInfo(CarRequestParameters parameters) {
        Set<ExternalCarInfo> result = filteredByMinEngineDisplacement(parameters.getMinEngineDisplacement(), externalCarInfoRepository.getAll());
        result = filteredByMinEngineHorsepower(parameters.getMinEngineHorsepower(), result);
        result = filteredByMinMaxSpeed(parameters.getMinMaxSpeed(), result);
        result = filteredByYear(parameters.getYear(), result);
        result = filteredByBodyStyle(parameters.getBodyStyle(), result);
        result = filteredBySegment(parameters.getSegment(), result);
        result = filteredBySearch(parameters.getSearch(), result);
        return result;
    }

    private Set<ExternalBrand> getFilteredExternalBrand(CarRequestParameters parameters) {
        return (Set<ExternalBrand>)externalBrandRepository.getAll().stream()
                                                          .filter(carInfo -> {
                                                              if (nonNull(parameters.getCountry()) && !parameters.getCountry().isBlank()) {
                                                                  return ((ExternalBrand)carInfo).getCountry().equals(parameters.getCountry());
                                                              }
                                                              return true;
                                                          })
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
                               if (nonNull(bodyStyle) && !bodyStyle.isBlank()) {
                                   return externalCarInfo.getBodyStyle().equals(bodyStyle);
                               }
                               return true;
                           }
                   ).collect(Collectors.toSet());
    }

    private Set<ExternalCarInfo> filteredBySegment(String segment, Set<ExternalCarInfo> info) {
        return info.stream()
                   .filter(externalCarInfo -> {
                               if (nonNull(segment) && !segment.isBlank()) {
                                   return externalCarInfo.getSegment().equals(segment);
                               }
                               return true;
                           }
                   ).collect(Collectors.toSet());
    }

    private Set<ExternalCarInfo> filteredBySearch(String search, Set<ExternalCarInfo> info) {
        return info.stream()
                   .filter(externalCarInfo -> {
                               if (nonNull(search) && !search.isBlank()) {
                                   return externalCarInfo.getGeneration().equals(search)
                                           || externalCarInfo.getModel().equals(search)
                                           || externalCarInfo.getModification().equals(search);
                               }
                               return true;
                           }
                   ).collect(Collectors.toSet());
    }

    private CarInfo buildCarInfo(ExternalBrand externalBrand, ExternalCarInfo externalCarInfo) {
        return CarInfo.builder()
                      .id(externalBrand.getId())
                      .segment(externalCarInfo.getSegment())
                      .brand(externalBrand.getTitle())
                      .model(externalCarInfo.getModel())
                      .country(externalBrand.getCountry())
                      .generation(externalCarInfo.getGeneration())
                      .modification(externalCarInfo.getModification())
                      .build();
    }

    private CarInfo buildCarFullInfo(ExternalBrand externalBrand, ExternalCarInfo externalCarInfo) {
        return CarFullInfo.builder()
                          .id(externalBrand.getId())
                          .segment(externalCarInfo.getSegment())
                          .brand(externalBrand.getTitle())
                          .model(externalCarInfo.getModel())
                          .country(externalBrand.getCountry())
                          .generation(externalCarInfo.getGeneration())
                          .modification(externalCarInfo.getModification())
                          .bodyCharacteristics(
                                  BodyCharacteristics.builder()
                                                     .bodyHeight(externalCarInfo.getBodyHeight())
                                                     .bodyLength(externalCarInfo.getBodyLength())
                                                     .bodyStyle(externalCarInfo.getBodyStyle())
                                                     .bodyWidth(externalCarInfo.getBodyWidth())
                                                     .build()
                          )
                          .engineCharacteristics(
                                  EngineCharacteristics.builder()
                                                       .engineDisplacement(externalCarInfo.getEngineDisplacement())
                                                       .engineType(externalCarInfo.getEngineType())
                                                       .fuelType(externalCarInfo.getFuelType())
                                                       .hp(externalCarInfo.getHp())
                                                       .build()
                          )
                          .build();
    }
}
