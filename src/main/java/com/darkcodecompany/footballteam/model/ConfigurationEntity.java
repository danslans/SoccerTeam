package com.darkcodecompany.footballteam.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "configuration")
public class ConfigurationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int shootingPowerPercentage;
    private int velocityPercentage;
    private int effectivePassesPercentage;
    @OneToOne
    @JoinColumn(name="week_id", referencedColumnName = "id")
    private WeekEntity week;
}
