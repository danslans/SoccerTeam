package com.darkcodecompany.footballteam.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "training")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    private List<PlayerEntity> players;

    @ManyToOne
    @JoinColumn(name="week_id", nullable=false)
    private WeekEntity week;

}
