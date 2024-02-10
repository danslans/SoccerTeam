package com.darkcodecompany.footballteam.service.impl;

import com.darkcodecompany.footballteam.exception.SoccerException;
import com.darkcodecompany.footballteam.model.ConfigurationEntity;
import com.darkcodecompany.footballteam.repository.IConfigurationRepository;
import com.darkcodecompany.footballteam.service.IConfigurationUseCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationUseCaseServiceImpl implements IConfigurationUseCaseService {
    private IConfigurationRepository configurationRepository;

    @Autowired
    public ConfigurationUseCaseServiceImpl(IConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    @Override
    public ConfigurationEntity configurePercentage(ConfigurationEntity configuration) throws SoccerException {
        try {
            return configurationRepository.save(configuration);
        }catch (Exception e){
            throw new SoccerException(SoccerException.ERROR_SAVING_CONFIGURATION,e);
        }
    }
}
