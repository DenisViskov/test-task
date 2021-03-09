package com.rincentral.test.services;

import com.rincentral.test.exceptions.RequestParametersException;
import com.rincentral.test.models.params.MaxSpeedRequestParameters;
import com.rincentral.test.services.interfaces.ValidatorService;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class ValidatorByMaxSpeedParametersServiceImpl implements ValidatorService<MaxSpeedRequestParameters> {

    @Override
    public void validate(MaxSpeedRequestParameters request) throws RequestParametersException {
        if(!isNull(request.getModel()) && !isNull(request.getBrand())){
            throw new RequestParametersException("Given two parameters");
        }
        if(isNull(request.getModel()) && isNull(request.getBrand())){
            throw new RequestParametersException("model and brand is null");
        }
    }
}
