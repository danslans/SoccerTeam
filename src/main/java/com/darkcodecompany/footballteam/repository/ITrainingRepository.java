package com.darkcodecompany.footballteam.repository;

import com.darkcodecompany.footballteam.model.TrainingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITrainingRepository extends JpaRepository<TrainingEntity,Integer> {

}
