package com.darkcodecompany.footballteam.repository;

import com.darkcodecompany.footballteam.model.PlayerEntity;
import com.darkcodecompany.footballteam.model.WeekEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IWeekRepository extends JpaRepository<WeekEntity,Integer> {

    @Query("SELECT distinct new PlayerEntity(p.id,p.name,sum(s.score))  FROM WeekEntity w " +
            "JOIN w.trainings t " +
            "JOIN t.players p "+
            "JOIN p.stats s "+
            "WHERE w.id=:id " +
            "GROUP BY s.id, p.id,p.name" +
            "")
    List<PlayerEntity> findByIdAndGroupByPlayer(int id);
}
