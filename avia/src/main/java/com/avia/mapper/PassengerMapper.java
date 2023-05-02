package com.avia.mapper;

import com.avia.dto.PassengerDto;
import com.avia.model.entity.Passenger;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PassengerMapper {
    @Mapping(target = "isDeleted", constant = "false")
    Passenger toEntity(PassengerDto passengerDto);

    PassengerDto toDto(Passenger passenger);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Passenger partialUpdate(PassengerDto passengerDto, @MappingTarget Passenger passenger);
}