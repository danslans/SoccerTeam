package com.darkcodecompany.footballteam.delegate;

import com.darkcodecompany.footballteam.dto.*;
import com.darkcodecompany.footballteam.exception.SoccerException;
import com.darkcodecompany.footballteam.mapper.IConfigurationMapper;
import com.darkcodecompany.footballteam.mapper.IPlayerMapper;
import com.darkcodecompany.footballteam.mapper.IWeekMapper;
import com.darkcodecompany.footballteam.model.ConfigurationEntity;
import com.darkcodecompany.footballteam.model.PlayerEntity;
import com.darkcodecompany.footballteam.mapper.ITrainingMapper;
import com.darkcodecompany.footballteam.model.TrainingEntity;
import com.darkcodecompany.footballteam.model.WeekEntity;
import com.darkcodecompany.footballteam.service.IConfigurationUseCaseService;
import com.darkcodecompany.footballteam.service.ITitularTeamUseCaseService;
import com.darkcodecompany.footballteam.service.ITrainingUseCaseService;
import com.darkcodecompany.footballteam.service.IWeekUseCaseService;
import com.darkcodecompany.footballteam.util.Constant;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoccerDelegate {

    private final ITitularTeamUseCaseService titularTeamUseCaseService;
    private final ITrainingUseCaseService trainingUseCaseService;
    private final IConfigurationUseCaseService configurationUseCaseService;
    private final IWeekUseCaseService weekUseCaseService;
    private final ITrainingMapper trainingMapper;
    private final IConfigurationMapper configurationMapper;
    private final IWeekMapper weekMapper;
    private final IPlayerMapper playerMapper;

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(SoccerDelegate.class);

    @Autowired
    public SoccerDelegate(ITitularTeamUseCaseService titularTeamUseCaseService,
                          ITrainingUseCaseService trainingUseCaseService,
                          IConfigurationUseCaseService configurationUseCaseService,
                          IWeekUseCaseService weekUseCaseService,
                          ITrainingMapper trainingMapper,
                          IConfigurationMapper configurationMapper,
                          IWeekMapper weekMapper,
                          IPlayerMapper playerMapper) {
        this.titularTeamUseCaseService = titularTeamUseCaseService;
        this.trainingUseCaseService = trainingUseCaseService;
        this.configurationUseCaseService = configurationUseCaseService;
        this.weekUseCaseService = weekUseCaseService;
        this.trainingMapper = trainingMapper;
        this.configurationMapper = configurationMapper;
        this.weekMapper = weekMapper;
        this.playerMapper = playerMapper;
    }

    public ResponseEntity<SoccerResponseDto<String>> recordTraining(TrainingRequestDto trainingRequestDto) {
        SoccerResponseDto<String> soccerResponseDto = new SoccerResponseDto<>();
        soccerResponseDto.setStatus(true);
        try {
            TrainingEntity trainingPlayers = trainingMapper.convert(trainingRequestDto);
            ConfigurationEntity configurationEntity = configurationMapper.configurationDtoToConfigurationEntity(trainingRequestDto.getConfiguration());
            trainingUseCaseService.processTraining(trainingPlayers, configurationEntity);
        } catch (SoccerException e) {
            soccerResponseDto.setStatus(false);
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(soccerResponseDto);
        }
        soccerResponseDto.setMessage(Constant.TRAINING_PROCESS + (soccerResponseDto.isStatus() ? Constant.WAS_SUCCESSFUL : Constant.WAS_NOT_SUCCESSFUL));
        return ResponseEntity.ok(soccerResponseDto);
    }

    public ResponseEntity<SoccerResponseDto<List<PlayerDto>>> obtainTitularTeam(Integer weekId) {
        SoccerResponseDto<List<PlayerDto>> soccerResponseDto = new SoccerResponseDto<>();
        soccerResponseDto.setStatus(true);
        try {
            List<PlayerEntity> players = titularTeamUseCaseService.obtainTitularTeam(weekId);
            List<PlayerDto> playersDto = playerMapper.playerEntityToPlayerDto(players);
            soccerResponseDto.setPayload(playersDto);
        } catch (SoccerException e) {
            soccerResponseDto.setStatus(false);
            soccerResponseDto.setMessage(e.getMessage());
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(soccerResponseDto);
        }

        return ResponseEntity.ok(soccerResponseDto);
    }

    public ResponseEntity<SoccerResponseDto<ConfigurationDto>> configurePercentage(ConfigurationDto configurationDto) {
        SoccerResponseDto<ConfigurationDto> soccerResponseDto = new SoccerResponseDto<>();
        soccerResponseDto.setStatus(true);
        try {
            ConfigurationEntity configuration = configurationMapper.configurationDtoToConfigurationEntity(configurationDto);
            ConfigurationEntity configurationEntity = configurationUseCaseService.configurePercentage(configuration);
            ConfigurationDto configurationResponseDto = configurationMapper.configurationEntityToConfigurationDto(configurationEntity);
            soccerResponseDto.setPayload(configurationResponseDto);
        } catch (SoccerException e) {
            soccerResponseDto.setStatus(false);
            soccerResponseDto.setMessage(e.getMessage());
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(soccerResponseDto);
        }
        return ResponseEntity.ok(soccerResponseDto);
    }

    public ResponseEntity<SoccerResponseDto<WeekDto>> initWeek() {
        SoccerResponseDto<WeekDto> soccerResponseDto = new SoccerResponseDto<>();
        soccerResponseDto.setStatus(true);
        try {
            WeekEntity weekEntity = weekUseCaseService.initWeek();
            WeekDto weekDto = weekMapper.weekEntityToWeekDto(weekEntity);
            soccerResponseDto.setPayload(weekDto);
        } catch (SoccerException e) {
            soccerResponseDto.setStatus(false);
            soccerResponseDto.setMessage(e.getMessage());
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(soccerResponseDto);
        }
        return ResponseEntity.ok(soccerResponseDto);
    }
}
