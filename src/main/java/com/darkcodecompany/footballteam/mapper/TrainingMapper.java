package com.darkcodecompany.footballteam.mapper;

import com.darkcodecompany.footballteam.dto.TrainingRequestDto;
import com.darkcodecompany.footballteam.exception.SoccerException;
import com.darkcodecompany.footballteam.model.PlayerEntity;
import com.darkcodecompany.footballteam.model.StatEntity;
import com.darkcodecompany.footballteam.model.TrainingEntity;
import com.darkcodecompany.footballteam.model.WeekEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TrainingMapper  implements ITrainingMapper {

    private IPlayerMapper playerMapper;
    private IWeekMapper weekMapper;

    @Autowired
    public TrainingMapper(IPlayerMapper playerMapper, IWeekMapper weekMapper) {
        this.playerMapper = playerMapper;
        this.weekMapper = weekMapper;
    }

    public TrainingEntity convert(TrainingRequestDto trainingRequestDto) throws SoccerException {
        TrainingEntity trainingEntities = TrainingEntity.builder()
                .players(new ArrayList<>())
                .build();
        try {
            trainingRequestDto.getPlayers().forEach(player -> {
                PlayerEntity playerEntity = playerMapper.playerDtoToPlayerEntity(player);
                playerEntity.setStats(new ArrayList<>());
                player.getStats().forEach(stat -> {
                    StatEntity statEntity = StatEntity.builder().build();statEntity. setPower(stat.getPower());
                    statEntity.setPasses(stat.getPasses());
                    statEntity.setDistance(stat.getSpeed().getDistance());
                    statEntity.setTime(stat.getSpeed().getTime());
                    statEntity.setPlayer( playerEntity);
                    playerEntity.getStats().add(
                            statEntity
                    );
                });
                trainingEntities.getPlayers().add(playerEntity);
            });
            WeekEntity week = weekMapper.weekDtoToWeekEntity(trainingRequestDto.getConfiguration().getWeek());
            trainingEntities.setWeek(week);
        } catch (Exception e) {
            throw new SoccerException(SoccerException.ERROR_CONVERTING_TRAINING, e);
        }


        return trainingEntities;
    }

}
