package com.darkcodecompany.footballteam.service;

import com.darkcodecompany.footballteam.exception.SoccerException;
import com.darkcodecompany.footballteam.model.PlayerEntity;
import com.darkcodecompany.footballteam.model.TrainingEntity;
import com.darkcodecompany.footballteam.model.WeekEntity;
import com.darkcodecompany.footballteam.repository.IWeekRepository;
import com.darkcodecompany.footballteam.service.impl.TitularTeamUseCaseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class ITitularTeamUseCaseServiceTest {

    @Mock
    private IWeekRepository weekRepository;
    @BeforeEach
    void setUp() {
    }

    @Test
    void when_obtainTitularTeamWithMinusThreeTraining_expected_Error() {
        ITitularTeamUseCaseService titularTeamUseCaseServiceInstance = new TitularTeamUseCaseServiceImpl(weekRepository);
        int weekId = 1;

        WeekEntity week = new WeekEntity();
        week.setId(1);
        week.setTrainings(Arrays.asList(TrainingEntity.builder().build(),
                TrainingEntity.builder().build()
        ));

        List<PlayerEntity> playerEntities = Arrays.asList();

        Mockito.when(weekRepository.findById(weekId)).thenReturn(Optional.of(week));
        Mockito.when(weekRepository.findByIdAndGroupByPlayer(weekId)).thenReturn(playerEntities);
        try {
            titularTeamUseCaseServiceInstance.obtainTitularTeam(weekId);
            fail( "Expected SoccerException was not thrown" );
        } catch (SoccerException e) {
            assertEquals( SoccerException.ERROR_DATA_INSUFFICIENT, e.getMessage() );
        }
    }

    @Test
    void when_obtainTitularTeamErrorCalculateScore_expected_Error() {
        ITitularTeamUseCaseService titularTeamUseCaseServiceInstance = new TitularTeamUseCaseServiceImpl(weekRepository);
        int weekId = 1;

        WeekEntity week = new WeekEntity();
        week.setId(1);
        week.setTrainings(Arrays.asList(TrainingEntity.builder().build(),
                TrainingEntity.builder().build(),
                TrainingEntity.builder().build()
        ));

        Mockito.when(weekRepository.findById(weekId)).thenReturn(Optional.of(week));
        Mockito.doThrow(new IllegalArgumentException()).when(weekRepository).findByIdAndGroupByPlayer(weekId) ;
        try {
            titularTeamUseCaseServiceInstance.obtainTitularTeam(weekId);
            fail( "Expected SoccerException was not thrown" );
        } catch (SoccerException e) {
            assertEquals( SoccerException.ERROR_CALCULATE_SCORE, e.getMessage() );
        }
    }
}