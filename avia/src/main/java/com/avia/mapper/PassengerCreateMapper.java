package com.avia.mapper;

import com.avia.dto.PassengerCreateDto;
import com.avia.model.entity.Passenger;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PassengerCreateMapper {
    @Mappings({
            @Mapping(target = "idPass", ignore = true),
            @Mapping(target = "miles", constant = "0.0"),
            @Mapping(target = "created", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))"),
            @Mapping(target = "isDeleted", constant = "false")
    })

    Passenger toEntity(PassengerCreateDto passengerCreateDto);

    PassengerCreateDto toDto(Passenger passenger);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Passenger partialUpdate(PassengerCreateDto passengerCreateDto, @MappingTarget Passenger passenger);
}