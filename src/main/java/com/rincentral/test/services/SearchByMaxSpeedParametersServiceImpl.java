package com.rincentral.test.services;

import com.rincentral.test.models.params.CarRequestParameters;
import com.rincentral.test.models.params.MaxSpeedRequestParameters;
import com.rincentral.test.persistence.CrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchByMaxSpeedParametersServiceImpl implements SearchByParametersService<Double, MaxSpeedRequestParameters> {

    @Qualifier("externalCarInfoRepository")
    private final CrudRepository externalCarInfoRepository;
    @Qualifier("externalBrandRepository")
    private final CrudRepository externalBrandRepository;

    @Override
    public List<Double> searchByParameters(MaxSpeedRequestParameters parameters) {
        return null;
    }
}
