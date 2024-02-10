package com.darkcodecompany.footballteam.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlayerDto  {
    @NotEmpty(message = "id cannot be empty")
    @JsonView(Views.Public.class)
    private int id;
    @NotEmpty(message = "name cannot be empty")
    @JsonView(Views.Public.class)
    private String name;
    @NotEmpty(message = "stats cannot be empty")
    private List<StatsDto> stats;
    @JsonView(Views.Public.class)
    private Long totalScore;
}
