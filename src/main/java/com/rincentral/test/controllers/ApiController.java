package com.rincentral.test.controllers;

import com.rincentral.test.models.CarInfo;
import com.rincentral.test.models.params.CarRequestParameters;
import com.rincentral.test.models.params.MaxSpeedRequestParameters;
import com.rincentral.test.services.FindAllTypesService;
import com.rincentral.test.services.SearchByParametersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Collections.emptyList;

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
    public ResponseEntity<List<? extends CarInfo>> getCars(CarRequestParameters requestParameters) {
        return ResponseEntity.ok(searchByParametersService.searchByParameters(requestParameters));
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
    public ResponseEntity<Double> getMaxSpeed(MaxSpeedRequestParameters requestParameters) {
        return ResponseEntity.ok(
                ((Double)Optional.ofNullable(searchByMaxSpeedParametersService
                        .searchByParameters(requestParameters)
                        .get(0)).orElse(null))
        );
    }
}
