package com.darkcodecompany.footballteam.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatsDto {
    @NotEmpty(message = "power cannot be empty")
    @Min(value = 0, message = "power cannot be less than 0")
    private int power;
    @NotEmpty(message = "speed cannot be empty")
    @Min(value = 0, message = "speed cannot be less than 0")
    private SpeedDto speed;
    @NotEmpty(message = "passes cannot be empty")
    @Min(value = 0, message = "passes cannot be less than 0")
    private int passes;
}
