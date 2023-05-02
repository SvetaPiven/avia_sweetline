package com.avia.mapper;

import com.avia.dto.AirportDto;
import com.avia.model.entity.Airport;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AirportMapper {
    Airport toEntity(AirportDto airportDto);

    AirportDto toDto(Airport airport);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Airport partialUpdate(AirportDto airportDto, @MappingTarget Airport airport);
}