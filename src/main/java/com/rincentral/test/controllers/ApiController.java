package com.rincentral.test.controllers;

import com.rincentral.test.exceptions.RequestParametersException;
import com.rincentral.test.models.CarInfo;
import com.rincentral.test.models.params.CarRequestParameters;
import com.rincentral.test.models.params.MaxSpeedRequestParameters;
import com.rincentral.test.services.interfaces.FindAllTypesService;
import com.rincentral.test.services.interfaces.SearchByParametersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    @Qualifier("searchByCarParametersServiceImpl")
    private SearchByParametersService searchByParametersService;

    @Autowired
    @Qualifier("searchByMaxSpeedParametersServiceImpl")
    private SearchByParametersService searchByMaxSpeedParametersService;

    @Autowired
    private FindAllTypesService findAllTypesService;

    @GetMapping("/cars")
    public ResponseEntity<List<? extends CarInfo>> getCars(CarRequestParameters requestParameters) throws RequestParametersException {
        List<CarInfo> result = (List<CarInfo>)searchByParametersService.searchByParameters(requestParameters);
        return result.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(result);
    }

    @GetMapping("/fuel-types")
    public ResponseEntity<List<String>> getFuelTypes() {
        return ResponseEntity.ok(findAllTypesService.getFuelTypes());
    }

    @GetMapping("/body-styles")
    public ResponseEntity<List<String>> getBodyStyles() {
        return ResponseEntity.ok(findAllTypesService.getBodyStyles());
    }

    @GetMapping("/engine-types")
    public ResponseEntity<List<String>> getEngineTypes() {
        return ResponseEntity.ok(findAllTypesService.getEngineTypes());
    }

    @GetMapping("/wheel-drives")
    public ResponseEntity<List<String>> getWheelDrives() {
        return ResponseEntity.ok(findAllTypesService.getWheelDrives());
    }

    @GetMapping("/gearboxes")
    public ResponseEntity<List<String>> getGearboxTypes() {
        return ResponseEntity.ok(findAllTypesService.getGearboxTypes());
    }

    @GetMapping("/max-speed")
    public ResponseEntity<Double> getMaxSpeed(MaxSpeedRequestParameters requestParameters) throws RequestParametersException {
        Double result = (Double)searchByMaxSpeedParametersService.searchByParameters(requestParameters);
        return result.doubleValue() == 0.0 ? ResponseEntity.notFound().build() : ResponseEntity.ok(result);
    }
}
