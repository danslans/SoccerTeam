package com.darkcodecompany.footballteam.delegate;

import com.darkcodecompany.footballteam.dto.*;
import com.darkcodecompany.footballteam.mapper.IConfigurationMapper;
import com.darkcodecompany.footballteam.mapper.IPlayerMapper;
import com.darkcodecompany.footballteam.mapper.ITrainingMapper;
import com.darkcodecompany.footballteam.mapper.IWeekMapper;
import com.darkcodecompany.footballteam.model.*;
import com.darkcodecompany.footballteam.repository.*;
import com.darkcodecompany.footballteam.service.IConfigurationUseCaseService;
import com.darkcodecompany.footballteam.service.ITitularTeamUseCaseService;
import com.darkcodecompany.footballteam.service.ITrainingUseCaseService;
import com.darkcodecompany.footballteam.service.IWeekUseCaseService;
import com.darkcodecompany.footballteam.service.impl.ConfigurationUseCaseServiceImpl;
import com.darkcodecompany.footballteam.service.impl.TitularTeamUseCaseServiceImpl;
import com.darkcodecompany.footballteam.service.impl.TrainingUseCaseServiceImpl;
import com.darkcodecompany.footballteam.service.impl.WeekUseCaseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SoccerDelegateTest {
    @Mock
    ITitularTeamUseCaseService titularTeamUseCaseService;
    @Mock
    ITrainingUseCaseService trainingUseCaseService;
    @Mock
    IConfigurationUseCaseService configurationUseCaseService;
    @Mock
    IWeekUseCaseService weekUseCaseService;
    @Mock
    IWeekRepository weekRepository;
    @Mock
    IConfigurationRepository configurationRepository;

    @Mock
    IStatRepository statRepository;
    @Mock
    ITrainingRepository trainingRepository;
    @Mock
    IPlayerRepository playerRepository;


    @Autowired
    ITrainingMapper trainingMapper;
    @Autowired
    IConfigurationMapper configurationMapper;
    @Autowired
    IWeekMapper weekMapper;
    @Autowired
    IPlayerMapper playerMapper;
    SoccerDelegate soccerDelegate;

    @BeforeEach
    void setUp() {

    }

    @Test
    void recordTraining() {
        ITrainingUseCaseService trainingUseCaseServiceInstance = new TrainingUseCaseServiceImpl(
                statRepository,
                trainingRepository,
                playerRepository,
                configurationRepository
        );
        soccerDelegate = new SoccerDelegate(
                titularTeamUseCaseService,
                trainingUseCaseServiceInstance,
                configurationUseCaseService,
                weekUseCaseService,
                trainingMapper,
                configurationMapper,
                weekMapper,
                playerMapper);
        TrainingRequestDto trainingRequestDto = new TrainingRequestDto();
        trainingRequestDto.setConfiguration(new ConfigurationDto());
        PlayerDto playerDto = new PlayerDto();
        playerDto.setId(1);
        playerDto.setName("player 1");
        StatsDto statsDto = new StatsDto();
        statsDto.setPasses(20);
        statsDto.setPower(300);
        SpeedDto speedDto = new SpeedDto();
        speedDto.setDistance(30);
        speedDto.setTime(6);
        statsDto.setSpeed(speedDto);
        playerDto.setStats(Arrays.asList(statsDto));

        trainingRequestDto.setPlayers(Arrays.asList(playerDto));
        ConfigurationEntity configuration = new ConfigurationEntity();
        configuration.setId(1);
        configuration.setVelocityPercentage(20);
        configuration.setShootingPowerPercentage(30);
        configuration.setEffectivePassesPercentage(50);
        WeekEntity week = new WeekEntity();
        week.setId(1);
        week.setConfiguration(configuration);
        configuration.setWeek(week);

        List<PlayerEntity> players = Arrays.asList(
                PlayerEntity.builder()
                        .id(1L)
                        .name("player 1")
                        .stats(Arrays.asList(StatEntity.builder()
                                        .distance(30)
                                        .time(6)
                                        .id(1)
                                .build()))
                        .build()
        );

        Mockito.when(playerRepository.saveAll(Mockito.any())).thenReturn(players);
        Mockito.when(configurationRepository.findById(Mockito.any())).thenReturn(Optional.of(configuration));
        Mockito.when(statRepository.saveAll(Mockito.any())).thenReturn(Arrays.asList());
        Mockito.when(trainingRepository.save(Mockito.any())).thenReturn(Mockito.any());

        ResponseEntity<SoccerResponseDto<String>> response = soccerDelegate.recordTraining(trainingRequestDto);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    void when_obtainTitularTeam_expected_StatusOk() {
        ITitularTeamUseCaseService titularTeamUseCaseServiceInstance = new TitularTeamUseCaseServiceImpl(weekRepository);
        int weekId = 1;
        soccerDelegate = new SoccerDelegate(
                titularTeamUseCaseServiceInstance,
                trainingUseCaseService,
                configurationUseCaseService,
                weekUseCaseService,
                trainingMapper,
                configurationMapper,
                weekMapper,
                playerMapper);
        WeekEntity week = new WeekEntity();
        week.setId(1);
        week.setTrainings(Arrays.asList(TrainingEntity.builder().build(),
                TrainingEntity.builder().build(),
                TrainingEntity.builder().build()
        ));

        List<PlayerEntity> playerEntities = Arrays.asList();

        Mockito.when(weekRepository.findById(weekId)).thenReturn(Optional.of(week));
        Mockito.when(weekRepository.findByIdAndGroupByPlayer(weekId)).thenReturn(playerEntities);
        ResponseEntity<SoccerResponseDto<List<PlayerDto>>> response = soccerDelegate.obtainTitularTeam(weekId);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    void when_obtainTitularTeam_expected_StatusError() {
        ITitularTeamUseCaseService titularTeamUseCaseServiceInstance = new TitularTeamUseCaseServiceImpl(weekRepository);
        int weekId = 1;
        soccerDelegate = new SoccerDelegate(
                titularTeamUseCaseServiceInstance,
                trainingUseCaseService,
                configurationUseCaseService,
                weekUseCaseService,
                trainingMapper,
                configurationMapper,
                weekMapper,
                playerMapper);

        Mockito.when(weekRepository.findById(weekId)).thenReturn(Optional.empty());

        ResponseEntity<SoccerResponseDto<List<PlayerDto>>> response = soccerDelegate.obtainTitularTeam(weekId);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    void when_configurePercentage_expected_statusOK() {
        IConfigurationUseCaseService configurationUseCaseServiceInstance = new ConfigurationUseCaseServiceImpl(configurationRepository);
        soccerDelegate = new SoccerDelegate(
                titularTeamUseCaseService,
                trainingUseCaseService,
                configurationUseCaseServiceInstance,
                weekUseCaseService,
                trainingMapper,
                configurationMapper,
                weekMapper,
                playerMapper);

        ConfigurationDto configurationDto = new ConfigurationDto();
        configurationDto.setId(1);
        configurationDto.setWeek(new WeekDto());
        configurationDto.setVelocityPercentage(30);
        configurationDto.setShootingPowerPercentage(20);
        configurationDto.setEffectivePassesPercentage(50);

        ConfigurationEntity configuration = new ConfigurationEntity();
        configuration.setId(1);
        configuration.setWeek(new WeekEntity());
        configuration.setVelocityPercentage(30);
        configuration.setShootingPowerPercentage(20);
        configuration.setEffectivePassesPercentage(50);

        Mockito.when(configurationRepository.save(Mockito.any())).thenReturn(configuration);
        ResponseEntity<SoccerResponseDto<ConfigurationDto>> response = soccerDelegate.configurePercentage(configurationDto);

        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    void when_configurePercentageWithErrorDatabase_expected_statusError() {
        IConfigurationUseCaseService configurationUseCaseServiceInstance = new ConfigurationUseCaseServiceImpl(configurationRepository);
        soccerDelegate = new SoccerDelegate(
                titularTeamUseCaseService,
                trainingUseCaseService,
                configurationUseCaseServiceInstance,
                weekUseCaseService,
                trainingMapper,
                configurationMapper,
                weekMapper,
                playerMapper);

        ConfigurationDto configurationDto = new ConfigurationDto();
        configurationDto.setId(1);
        configurationDto.setWeek(new WeekDto());
        configurationDto.setVelocityPercentage(30);
        configurationDto.setShootingPowerPercentage(20);
        configurationDto.setEffectivePassesPercentage(50);

        Mockito.doThrow(new IllegalArgumentException()).when(configurationRepository).save(Mockito.any());
        ResponseEntity<SoccerResponseDto<ConfigurationDto>> response = soccerDelegate.configurePercentage(configurationDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    void when_initWeek_expected_statusOk() {
        IWeekUseCaseService weekUseCaseServiceInstance = new WeekUseCaseServiceImpl(weekRepository);
        soccerDelegate = new SoccerDelegate(
                titularTeamUseCaseService,
                trainingUseCaseService,
                configurationUseCaseService,
                weekUseCaseServiceInstance,
                trainingMapper,
                configurationMapper,
                weekMapper,
                playerMapper);

        WeekEntity week = new WeekEntity();
        week.setId(1);

        Mockito.when(weekRepository.save(Mockito.any())).thenReturn(week);
        ResponseEntity<SoccerResponseDto<WeekDto>> response = soccerDelegate.initWeek();

        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    void when_tryInitWeekErrorSave_expected_statusError() {
        IWeekUseCaseService weekUseCaseServiceInstance = new WeekUseCaseServiceImpl(weekRepository);
        soccerDelegate = new SoccerDelegate(
                titularTeamUseCaseService,
                trainingUseCaseService,
                configurationUseCaseService,
                weekUseCaseServiceInstance,
                trainingMapper,
                configurationMapper,
                weekMapper,
                playerMapper);

        Mockito.doThrow(new IllegalArgumentException()).when(weekRepository).save(Mockito.any());
        ResponseEntity<SoccerResponseDto<WeekDto>> response = soccerDelegate.initWeek();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}