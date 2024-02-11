package com.darkcodecompany.footballteam.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "player")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name",  nullable = false)
    private String name;

    @ManyToMany(mappedBy = "players")
    private List<TrainingEntity> trainings;

    @OneToMany(mappedBy = "player")
    private List<StatEntity> stats;
    @Transient
    private Long totalScore;

    public PlayerEntity(Long id, String name, Long totalScore) {
        this.id = id;
        this.name = name;
        this.totalScore = totalScore;
    }
}
