package com.avia.mapper;

import com.avia.dto.FlightDto;
import com.avia.model.entity.Flight;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {AirportMapper.class})
public interface FlightMapper {
    Flight toEntity(FlightDto flightDto);

    FlightDto toDto(Flight flight);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Flight partialUpdate(FlightDto flightDto, @MappingTarget Flight flight);
}