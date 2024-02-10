package com.darkcodecompany.footballteam.service;

import com.darkcodecompany.footballteam.exception.SoccerException;
import com.darkcodecompany.footballteam.model.ConfigurationEntity;
import com.darkcodecompany.footballteam.model.TrainingEntity;

public interface ITrainingUseCaseService {

    boolean processTraining(TrainingEntity trainingEntity, ConfigurationEntity configurationEntity) throws SoccerException;
}
