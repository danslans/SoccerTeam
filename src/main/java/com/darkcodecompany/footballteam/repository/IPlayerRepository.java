package com.darkcodecompany.footballteam.repository;

import com.darkcodecompany.footballteam.model.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IPlayerRepository extends JpaRepository<PlayerEntity,Integer> {

}
