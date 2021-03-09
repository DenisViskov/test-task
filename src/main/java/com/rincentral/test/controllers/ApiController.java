package com.rincentral.test.controllers;

import com.rincentral.test.exceptions.RequestParametersException;
import com.rincentral.test.models.CarInfo;
import com.rincentral.test.models.params.CarRequestParameters;
import com.rincentral.test.models.params.MaxSpeedRequestParameters;
import com.rincentral.test.services.FindAllTypesService;
import com.rincentral.test.services.SearchByParametersService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final SearchByParametersService searchByParametersService;
    private final SearchByParametersService searchByMaxSpeedParametersService;
    private final FindAllTypesService findAllTypesService;

    public ApiController(@Qualifier("searchByCarParametersServiceImpl") SearchByParametersService searchByParametersService,
                         @Qualifier("searchByMaxSpeedParametersServiceImpl") SearchByParametersService searchByMaxSpeedParametersService,
                         FindAllTypesService findAllTypesService) {
        this.searchByParametersService = searchByParametersService;
        this.searchByMaxSpeedParametersService = searchByMaxSpeedParametersService;
        this.findAllTypesService = findAllTypesService;
    }

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
        return isNull(result) ? ResponseEntity.notFound().build() : ResponseEntity.ok(result);
    }
}
