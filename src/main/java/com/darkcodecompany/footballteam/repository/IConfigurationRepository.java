package com.darkcodecompany.footballteam.repository;

import com.darkcodecompany.footballteam.model.ConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IConfigurationRepository extends JpaRepository<ConfigurationEntity, Integer> {
}
