package com.darkcodecompany.footballteam.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stat")
public class StatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private int power;
    @Column(nullable = false)
    private int distance;
    @Column(nullable = false)
    private int time;
    @Column(nullable = false)
    private int passes;
    @Column(nullable = false)
    private int score;
    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private PlayerEntity player;
}
