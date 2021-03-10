package com.rincentral.test.config;

import com.rincentral.test.models.external.ExternalBrand;
import com.rincentral.test.models.external.ExternalCar;
import com.rincentral.test.persistence.ExternalBrandRepositoryImpl;
import com.rincentral.test.persistence.ExternalCarInfoRepositoryImpl;
import com.rincentral.test.persistence.ExternalCarRepositoryImpl;
import com.rincentral.test.services.ExternalCarsApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PreInitializeConfig {

    private final ExternalCarsApiService service;
    private final List<ExternalBrand> externalBrands;
    private final List<ExternalCar> externalCars;

    @PostConstruct
    public void initExternalInfo() {
        log.info("Getting data from external partners");
        tryingToGetInfo();
        log.info("External partner info initialized was successfully");
    }

    @Bean
    public ExternalBrandRepositoryImpl externalBrandRepository() {
        ExternalBrandRepositoryImpl repository = new ExternalBrandRepositoryImpl();
        externalBrands.forEach(repository::add);
        return repository;
    }

    @Bean
    public ExternalCarInfoRepositoryImpl externalCarInfoRepository() {
        ExternalCarInfoRepositoryImpl repository = new ExternalCarInfoRepositoryImpl();
        externalBrands.forEach(externalBrand ->
                repository.add(service.loadCarInformationById(externalBrand.getId()))
        );
        return repository;
    }

    @Bean
    public ExternalCarRepositoryImpl externalCarRepository() {
        ExternalCarRepositoryImpl repository = new ExternalCarRepositoryImpl();
        externalCars.forEach(repository::add);
        return repository;
    }

    private void tryingToGetInfo() {
        externalBrands.addAll(service.loadAllBrands());
        externalCars.addAll(service.loadAllCars());
        while (externalBrands.isEmpty() && externalCars.isEmpty()) {
            log.error("External partners returned empty lists");
            log.info("Trying to get info again");
            externalBrands.addAll(service.loadAllBrands());
            externalCars.addAll(service.loadAllCars());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
