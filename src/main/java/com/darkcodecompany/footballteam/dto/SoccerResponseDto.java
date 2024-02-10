package com.darkcodecompany.footballteam.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SoccerResponseDto <T>{
    @JsonView(Views.Public.class)
    private boolean status;
    @JsonView(Views.Public.class)
    private String message;
    @JsonView(Views.Public.class)
    private T payload;
}
