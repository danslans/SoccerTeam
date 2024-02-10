package com.darkcodecompany.footballteam.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfigurationDto {
    private int id;
    @Min(value = 0, message = "shootingPowerPercentage cannot be less than 0", groups = {ICreateConfiguration.class})
    private int shootingPowerPercentage;
    @Min(value = 0, message = "velocityPercentage cannot be less than 0", groups = {ICreateConfiguration.class})
    private int velocityPercentage;
    @Min(value = 0, message = "effectivePassesPercentage cannot be less than 0", groups = {ICreateConfiguration.class})
    private int effectivePassesPercentage;
    private WeekDto week;
}
