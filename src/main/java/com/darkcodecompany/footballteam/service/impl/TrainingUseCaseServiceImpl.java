package com.darkcodecompany.footballteam.service.impl;

import com.darkcodecompany.footballteam.exception.SoccerException;
import com.darkcodecompany.footballteam.model.*;
import com.darkcodecompany.footballteam.repository.IConfigurationRepository;
import com.darkcodecompany.footballteam.repository.IPlayerRepository;
import com.darkcodecompany.footballteam.repository.IStatRepository;
import com.darkcodecompany.footballteam.repository.ITrainingRepository;
import com.darkcodecompany.footballteam.service.ITrainingUseCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

@Service
public class TrainingUseCaseServiceImpl implements ITrainingUseCaseService {


    private final IStatRepository statRepository;
    private final ITrainingRepository trainingRepository;
    private final IPlayerRepository playerRepository;
    private final IConfigurationRepository configurationRepository;
    private static final int HUNDRED_PERCENT = 100;
    private static final int SCALE_DIVIDE = 0;


    @Autowired
    public TrainingUseCaseServiceImpl(IStatRepository statRepository, ITrainingRepository trainingRepository, IPlayerRepository playerRepository, IConfigurationRepository configurationRepository) {
        this.statRepository = statRepository;
        this.trainingRepository = trainingRepository;
        this.playerRepository = playerRepository;
        this.configurationRepository = configurationRepository;
    }

    @Override
    public void processTraining(TrainingEntity trainingEntity, ConfigurationEntity configurationEntity) throws SoccerException {
        try {
            List<PlayerEntity> players = playerRepository.saveAll(trainingEntity.getPlayers());
            ConfigurationEntity configuration = configurationRepository.findById(configurationEntity.getId())
                    .orElseThrow(()-> new SoccerException(SoccerException.ERROR_CONFIGURATION_NOT_FOUND + configurationEntity.getId()));
            saveStats(trainingEntity, configuration, players);
            trainingEntity.setPlayers(players);
            trainingRepository.save(trainingEntity);
        }catch (Exception e){
            throw new SoccerException( SoccerException.ERROR_RECORD_TRAINING,e);
        }
    }

    private void saveStats(TrainingEntity trainingEntity, ConfigurationEntity configurationEntity, List<PlayerEntity> players) {
        players.forEach(playerEntity -> {
            var playerWithStat = getPlayerWithStat(trainingEntity, playerEntity);
            playerEntity.setStats(playerWithStat.getStats());
            getScore( configurationEntity,playerEntity);
            statRepository.saveAll(playerEntity.getStats());
        });
    }

    private  PlayerEntity getPlayerWithStat(TrainingEntity trainingEntity, PlayerEntity playerEntity) {
        return trainingEntity.getPlayers().stream().filter(player -> player.getId().equals(playerEntity.getId()))
                .findFirst().orElseThrow();
    }

    private void getScore(ConfigurationEntity configurationEntity, PlayerEntity playerWithStat) {
        playerWithStat.getStats( ).forEach(stat ->
            stat.setScore( calculateScore(configurationEntity.getWeek(), stat) )
        );
    }

    private int calculateScore(WeekEntity week, StatEntity stat) {
        var pointPower = pointByCategory(stat.getPower(), week.getConfiguration().getShootingPowerPercentage());
        var speed = getSpeed(stat.getDistance(),stat.getTime());
        var pointSpeed = pointByCategory(speed, week.getConfiguration().getVelocityPercentage());
        var pointPasses = pointByCategory(stat.getPasses(), week.getConfiguration().getEffectivePassesPercentage());
        return getTotalScore(pointPower, pointSpeed, pointPasses).intValue();
    }

    private static int getSpeed(int distance, int time) {
        return BigDecimal.valueOf(distance).divide(BigDecimal.valueOf(time), SCALE_DIVIDE, RoundingMode.HALF_EVEN).intValue();
    }

    private BigDecimal pointByCategory(int category, int percentage) {
        return BigDecimal.valueOf(category).multiply(BigDecimal.valueOf(percentage)).divide(BigDecimal.valueOf(HUNDRED_PERCENT), SCALE_DIVIDE, RoundingMode.HALF_EVEN);
    }

    private BigDecimal getTotalScore(BigDecimal... points) {
        return Arrays.stream(points).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
