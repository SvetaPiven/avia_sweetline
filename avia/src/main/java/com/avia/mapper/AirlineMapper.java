package com.avia.mapper;

import com.avia.dto.AirlineDto;
import com.avia.model.entity.Airline;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AirlineMapper {
    Airline toEntity(AirlineDto airlineDto);

    AirlineDto toDto(Airline airline);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Airline partialUpdate(AirlineDto airlineDto, @MappingTarget Airline airline);
}