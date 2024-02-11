package com.darkcodecompany.footballteam.service.impl;

import com.darkcodecompany.footballteam.exception.SoccerException;
import com.darkcodecompany.footballteam.model.PlayerEntity;
import com.darkcodecompany.footballteam.model.WeekEntity;
import com.darkcodecompany.footballteam.repository.IWeekRepository;
import com.darkcodecompany.footballteam.service.ITitularTeamUseCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TitularTeamUseCaseServiceImpl implements ITitularTeamUseCaseService {

    private static final int MIN_TRAINING = 3;
    private final IWeekRepository weekRepository;

    @Autowired
    public TitularTeamUseCaseServiceImpl(IWeekRepository weekRepository) {
        this.weekRepository = weekRepository;
    }

    @Override
    public List<PlayerEntity> obtainTitularTeam(Integer weekId) throws SoccerException {
        WeekEntity week = weekRepository.findById(weekId)
                .orElseThrow(new SoccerException(SoccerException.ERROR_WEEK_NOT_FOUND));
        if (week.getTrainings().size() >= MIN_TRAINING) {
            return calculateScoreAndResponse(week);
        } else {
            throw new SoccerException(SoccerException.ERROR_DATA_INSUFFICIENT);
        }
    }

    private List<PlayerEntity> calculateScoreAndResponse(WeekEntity week) throws SoccerException {
        try {
            return weekRepository.findByIdAndGroupByPlayer(week.getId());
        } catch (Exception e) {
            throw new SoccerException(SoccerException.ERROR_CALCULATE_SCORE, e);
        }
    }

}
