package com.darkcodecompany.footballteam.service;

import com.darkcodecompany.footballteam.exception.SoccerException;
import com.darkcodecompany.footballteam.model.PlayerEntity;

import java.util.List;

public interface ITitularTeamUseCaseService {
    List<PlayerEntity> obtainTitularTeam(Integer weekId) throws SoccerException;
}
