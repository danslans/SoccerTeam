package com.darkcodecompany.footballteam.mapper;

import com.darkcodecompany.footballteam.dto.WeekDto;
import com.darkcodecompany.footballteam.model.WeekEntity;
import org.mapstruct.Mapper;

@Mapper( componentModel = "spring" )
public interface IWeekMapper {
    IWeekMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper( IWeekMapper.class );

    WeekEntity weekDtoToWeekEntity(WeekDto weekDto);
    WeekDto weekEntityToWeekDto(WeekEntity weekEntity);
}
