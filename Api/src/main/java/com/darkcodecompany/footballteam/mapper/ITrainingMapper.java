package com.darkcodecompany.footballteam.mapper;

import com.darkcodecompany.footballteam.dto.TrainingRequestDto;
import com.darkcodecompany.footballteam.exception.SoccerException;
import com.darkcodecompany.footballteam.model.TrainingEntity;

public interface ITrainingMapper {
    TrainingEntity convert(TrainingRequestDto trainingRequestDto) throws SoccerException;
}
