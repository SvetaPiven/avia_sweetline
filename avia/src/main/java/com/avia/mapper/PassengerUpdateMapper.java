package com.avia.mapper;

import com.avia.dto.PassengerUpdateDto;
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
public interface PassengerUpdateMapper {
    @Mappings({
            @Mapping(target = "idPass", ignore = true),
            @Mapping(target = "created", ignore = true),
            @Mapping(target = "changed", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))"),
            @Mapping(target = "isDeleted", ignore = true)
    })

    Passenger toEntity(PassengerUpdateDto passengerUpdateDto);

    PassengerUpdateDto toDto(Passenger passenger);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Passenger partialUpdate(PassengerUpdateDto passengerUpdateDto, @MappingTarget Passenger passenger);
}