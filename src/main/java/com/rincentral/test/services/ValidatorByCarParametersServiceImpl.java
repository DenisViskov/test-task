package com.rincentral.test.services;

import com.rincentral.test.exceptions.RequestParametersException;
import com.rincentral.test.models.params.CarRequestParameters;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Stream;

@Service
public class ValidatorByCarParametersServiceImpl implements ValidatorService<CarRequestParameters> {

    @Override
    public void validate(CarRequestParameters request) throws RequestParametersException {
        boolean result = Stream.of
                (
                        request.getSegment(),
                        request.getYear(),
                        request.getCountry(),
                        request.getBodyStyle(),
                        request.getIsFull(),
                        request.getMinEngineDisplacement(),
                        request.getMinEngineHorsepower(),
                        request.getMinMaxSpeed(),
                        request.getSearch()
                )
                               .allMatch(Objects::isNull);
        if (result) {
            throw new RequestParametersException("All given parameters is null");
        }
    }
}
