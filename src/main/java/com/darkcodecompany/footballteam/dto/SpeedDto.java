package com.darkcodecompany.footballteam.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpeedDto {
    @NotEmpty(message = "distance cannot be empty")
    private int distance;
    @NotEmpty(message = "time cannot be empty")
    private int time;
}
