package com.darkcodecompany.footballteam.repository;

import com.darkcodecompany.footballteam.model.StatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStatRepository extends JpaRepository<StatEntity,Integer> {
}
