package com.darkcodecompany.footballteam.mapper;

import com.darkcodecompany.footballteam.dto.PlayerDto;
import com.darkcodecompany.footballteam.model.PlayerEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IPlayerMapper {
    IPlayerMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(IPlayerMapper.class);

    PlayerEntity playerDtoToPlayerEntity(PlayerDto playerDto);
    List<PlayerDto> playerEntityToPlayerDto(List<PlayerEntity> playerEntities);

}
