package com.darkcodecompany.footballteam.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TrainingRequestDto {

    @NotEmpty(message = "players cannot be empty")
    private List<PlayerDto> players;
    @NotEmpty(message = "players cannot be empty", groups = {ICreateConfiguration.class})
    private ConfigurationDto configuration;
}
