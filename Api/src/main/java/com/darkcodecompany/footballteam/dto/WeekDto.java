package com.darkcodecompany.footballteam.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeekDto {
    @NotEmpty(message = "id cannot be empty")
    private int id;
    private int number;
    private ConfigurationDto configuration;
}
