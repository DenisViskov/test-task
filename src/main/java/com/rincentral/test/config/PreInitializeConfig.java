package com.rincentral.test.config;

import com.rincentral.test.models.external.ExternalBrand;
import com.rincentral.test.models.external.ExternalCar;
import com.rincentral.test.persistence.ExternalBrandRepositoryImpl;
import com.rincentral.test.persistence.ExternalCarInfoRepositoryImpl;
import com.rincentral.test.persistence.ExternalCarRepositoryImpl;
import com.rincentral.test.services.ExternalCarsApiService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.NoSuchElementException;

@Configuration
@AllArgsConstructor
public class PreInitializeConfig {

    private static final Logger LOGGER = LogManager.getLogger(PreInitializeConfig.class);

    @Autowired
    private final ExternalCarsApiService service;
    private List<ExternalBrand> externalBrands;
    private List<ExternalCar> externalCars;

    @PostConstruct
    public void initExternalInfo() {
        LOGGER.info("Running initialize external partners info");
        externalBrands = service.loadAllBrands();
        externalCars = service.loadAllCars();
        if (externalBrands.isEmpty()) {
            LOGGER.info("External partners returned empty brands list");
            throw new NoSuchElementException();
        }
        if (externalCars.isEmpty()) {
            LOGGER.info("External partners returned empty cars list");
            throw new NoSuchElementException();
        }
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
}
