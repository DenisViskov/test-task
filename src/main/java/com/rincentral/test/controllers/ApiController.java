package com.rincentral.test.controllers;

import com.rincentral.test.models.CarInfo;
import com.rincentral.test.models.params.CarRequestParameters;
import com.rincentral.test.models.params.MaxSpeedRequestParameters;
import com.rincentral.test.services.SearchByParametersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Collections.emptyList;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    @Autowired
    private final SearchByParametersService searchByParametersService;

    @GetMapping("/cars")
    public ResponseEntity<List<? extends CarInfo>> getCars(CarRequestParameters requestParameters) {
        return ResponseEntity.ok(searchByParametersService.searchByParameters(requestParameters));
    }

    @GetMapping("/fuel-types")
    public ResponseEntity<List<String>> getFuelTypes() {
        return ResponseEntity.ok(emptyList());
    }

    @GetMapping("/body-styles")
    public ResponseEntity<List<String>> getBodyStyles() {
        return ResponseEntity.ok(emptyList());
    }

    @GetMapping("/engine-types")
    public ResponseEntity<List<String>> getEngineTypes() {
        return ResponseEntity.ok(emptyList());
    }

    @GetMapping("/wheel-drives")
    public ResponseEntity<List<String>> getWheelDrives() {
        return ResponseEntity.ok(emptyList());
    }

    @GetMapping("/gearboxes")
    public ResponseEntity<List<String>> getGearboxTypes() {
        return ResponseEntity.ok(emptyList());
    }

    @GetMapping("/max-speed")
    public ResponseEntity<Double> getMaxSpeed(MaxSpeedRequestParameters requestParameters) {
        return ResponseEntity.ok(0.0);
    }
}
