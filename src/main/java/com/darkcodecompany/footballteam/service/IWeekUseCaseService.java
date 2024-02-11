package com.darkcodecompany.footballteam.service;

import com.darkcodecompany.footballteam.exception.SoccerException;
import com.darkcodecompany.footballteam.model.WeekEntity;

public interface IWeekUseCaseService {
    WeekEntity initWeek() throws SoccerException;
}
