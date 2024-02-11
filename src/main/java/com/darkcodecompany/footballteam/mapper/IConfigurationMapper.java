package com.darkcodecompany.footballteam.mapper;

import com.darkcodecompany.footballteam.dto.ConfigurationDto;
import com.darkcodecompany.footballteam.model.ConfigurationEntity;
import org.mapstruct.Mapper;

@Mapper( componentModel = "spring" )
public interface IConfigurationMapper {

    ConfigurationEntity configurationDtoToConfigurationEntity(ConfigurationDto configurationDto );
    ConfigurationDto configurationEntityToConfigurationDto( ConfigurationEntity configurationEntity );
}
