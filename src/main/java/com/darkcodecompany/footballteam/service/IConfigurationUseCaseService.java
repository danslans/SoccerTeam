package com.darkcodecompany.footballteam.service;

import com.darkcodecompany.footballteam.exception.SoccerException;
import com.darkcodecompany.footballteam.model.ConfigurationEntity;

public interface IConfigurationUseCaseService {
    ConfigurationEntity configurePercentage(ConfigurationEntity configuration) throws SoccerException;
}
