package com.darkcodecompany.footballteam.service;

import com.darkcodecompany.footballteam.exception.SoccerException;
import com.darkcodecompany.footballteam.model.*;
import com.darkcodecompany.footballteam.repository.IConfigurationRepository;
import com.darkcodecompany.footballteam.repository.IPlayerRepository;
import com.darkcodecompany.footballteam.repository.IStatRepository;
import com.darkcodecompany.footballteam.repository.ITrainingRepository;
import com.darkcodecompany.footballteam.service.impl.TrainingUseCaseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class ITrainingUseCaseServiceTest {
    @Mock
    IStatRepository statRepository;
    @Mock
    ITrainingRepository trainingRepository;
    @Mock
    IPlayerRepository playerRepository;
    @Mock
    IConfigurationRepository configurationRepository;


    @BeforeEach
    void setUp() {
    }

    @Test
    void when_recordTraining_expected_okScore() {
        ITrainingUseCaseService trainingUseCaseServiceInstance = new TrainingUseCaseServiceImpl(
                statRepository,
                trainingRepository,
                playerRepository,
                configurationRepository
        );

        var velocityPercentage = 20;
        var shootingPowerPercentage = 30;
        var effectivePassesPercentage = 50;
        var passes = 20;
        var power = 300;
        var distance = 30;
        var time = 6;
        var speed = distance / time;

        var velocity = (speed * velocityPercentage / 100);
        var shootingPower = (power * shootingPowerPercentage / 100);
        var effectivePasses = (passes * effectivePassesPercentage / 100);
        var score = velocity + shootingPower + effectivePasses;

        TrainingEntity trainingEntity = new TrainingEntity();

        PlayerEntity player = new PlayerEntity();
        player.setId(1L);
        player.setName("player 1");
        StatEntity stat = new StatEntity();
        stat.setPasses(passes);
        stat.setPower(power);
        stat.setDistance(distance);
        stat.setTime(time);
        player.setStats(Arrays.asList(stat));

        trainingEntity.setPlayers(Arrays.asList(player));

        ConfigurationEntity configuration = new ConfigurationEntity();
        configuration.setId(1);
        configuration.setVelocityPercentage(velocityPercentage);
        configuration.setShootingPowerPercentage(shootingPowerPercentage);
        configuration.setEffectivePassesPercentage(effectivePassesPercentage);
        WeekEntity week = new WeekEntity();
        week.setId(1);
        week.setConfiguration(configuration);
        configuration.setWeek(week);
        trainingEntity.setWeek(week);

        List<PlayerEntity> players = Arrays.asList(
                PlayerEntity.builder()
                        .id(1L)
                        .name("player 1")
                        .stats(Arrays.asList(StatEntity.builder()
                                .distance(distance)
                                .time(time)
                                .power(power)
                                .passes(passes)
                                .id(1)
                                .build()))
                        .build()
        );


        Mockito.when(playerRepository.saveAll(Mockito.any())).thenReturn(players);
        Mockito.when(configurationRepository.findById(Mockito.any())).thenReturn(Optional.of(configuration));
        Mockito.when(statRepository.saveAll(Mockito.any())).thenReturn(Arrays.asList());
        Mockito.when(trainingRepository.save(Mockito.any())).thenReturn(Mockito.any());


        try {
            trainingUseCaseServiceInstance.processTraining(trainingEntity, configuration);
            trainingEntity.getPlayers().forEach(playerEntity -> {
                playerEntity.getStats().forEach(statEntity -> {
                    assertEquals(score, statEntity.getScore());
                });
            });
        } catch (SoccerException e) {
            fail("Should not throw an exception");
        }
    }

}