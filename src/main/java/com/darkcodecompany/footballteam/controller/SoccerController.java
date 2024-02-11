package com.darkcodecompany.footballteam.controller;

import com.darkcodecompany.footballteam.delegate.SoccerDelegate;
import com.darkcodecompany.footballteam.dto.*;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
public class SoccerController {

    SoccerDelegate soccerDelegate;

    @Autowired
    public SoccerController(SoccerDelegate soccerDelegate) {
        this.soccerDelegate = soccerDelegate;
    }

    @PostMapping("/training")
    public ResponseEntity<SoccerResponseDto<String>>  recordTraining(@Valid @RequestBody TrainingRequestDto trainingRequestDto) {
        return soccerDelegate.recordTraining(trainingRequestDto);
    }

    @GetMapping("/team")
    @JsonView(Views.Public.class)
    public ResponseEntity<SoccerResponseDto<List<PlayerDto>>> obtainTitularTeam(
            @RequestParam( value = "week") Integer weekId
    ) {
        return soccerDelegate.obtainTitularTeam(weekId);
    }

    @PostMapping("/configurePercentage")
    public ResponseEntity<SoccerResponseDto<ConfigurationDto>> configurePercentage(@Validated(ICreateConfiguration.class) @RequestBody ConfigurationDto configurationDto) {
        return soccerDelegate.configurePercentage(configurationDto);
    }

    @PostMapping("/initWeek")
    public ResponseEntity<SoccerResponseDto<WeekDto>> initWeek() {
        return soccerDelegate.initWeek();
    }
}
