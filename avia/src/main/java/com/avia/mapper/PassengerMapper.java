package com.avia.mapper;

import com.avia.model.entity.requests.PassengerDto;
import com.avia.model.entity.Passenger;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {DocumentPassMapper.class})
public interface PassengerMapper {
    @Mappings({
            @Mapping(target = "idPass", ignore = true),
            @Mapping(target = "miles", constant = "0.0"),
            @Mapping(target = "created", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))"),
            @Mapping(target = "changed", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))"),
            @Mapping(target = "isDeleted", constant = "false")
    })
    Passenger toEntity(PassengerDto passengerDto);

    PassengerDto toDto(Passenger passenger);

    @Mappings({
            @Mapping(target = "idPass", ignore = true),
            @Mapping(target = "created", ignore = true),
            @Mapping(target = "changed", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))"),
            @Mapping(target = "isDeleted", ignore = true)
    })
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Passenger partialUpdate(PassengerDto passengerDto, @MappingTarget Passenger passenger);
}