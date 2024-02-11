package com.darkcodecompany.footballteam.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Week")
public class WeekEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int number;
    @OneToOne(mappedBy = "week", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ConfigurationEntity configuration;

    @OneToMany(mappedBy="week", fetch = FetchType.EAGER)
    private List<TrainingEntity> trainings;

}
