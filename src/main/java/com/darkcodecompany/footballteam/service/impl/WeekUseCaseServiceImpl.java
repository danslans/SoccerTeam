package com.darkcodecompany.footballteam.service.impl;

import com.darkcodecompany.footballteam.exception.SoccerException;
import com.darkcodecompany.footballteam.model.WeekEntity;
import com.darkcodecompany.footballteam.repository.IWeekRepository;
import com.darkcodecompany.footballteam.service.IWeekUseCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeekUseCaseServiceImpl implements IWeekUseCaseService {
    private IWeekRepository weekRepository;

    @Autowired
    public WeekUseCaseServiceImpl(IWeekRepository weekRepository) {
        this.weekRepository = weekRepository;
    }

    @Override
    public WeekEntity initWeek() throws SoccerException {
        try {
            return weekRepository.save(new WeekEntity());
        }catch (Exception e){
            throw new SoccerException(SoccerException.ERROR_SAVING_WEEK,e);
        }
    }
}
