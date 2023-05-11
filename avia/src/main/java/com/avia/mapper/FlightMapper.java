package com.avia.mapper;

import com.avia.model.dto.FlightDto;
import com.avia.model.entity.Flight;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {AirportMapper.class})
public interface FlightMapper {
    @Mappings({
            @Mapping(target = "idFlight", ignore = true),
            @Mapping(target = "idPlaneType.idPlaneType", source = "idPlaneType"),
            @Mapping(target = "idDepartureAirport.idAirport", source = "idDepartureAirport"),
            @Mapping(target = "idArrivalAirport.idAirport", source = "idArrivalAirport"),
            @Mapping(target = "idFlightStatus.idFlightStatus", source = "idFlightStatus"),
            @Mapping(target = "created", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))"),
            @Mapping(target = "changed", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))"),
            @Mapping(target = "isDeleted", constant = "false")
    })
    Flight toEntity(FlightDto flightDto);

    @Mappings({
            @Mapping(target = "idFlight", ignore = true),
            @Mapping(target = "idPlaneType.idPlaneType", source = "idPlaneType"),
            @Mapping(target = "idDepartureAirport.idAirport", source = "idDepartureAirport"),
            @Mapping(target = "idArrivalAirport.idAirport", source = "idArrivalAirport"),
            @Mapping(target = "idFlightStatus.idFlightStatus", source = "idFlightStatus"),
            @Mapping(target = "changed", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))"),
            @Mapping(target = "isDeleted", constant = "false")
    })
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Flight partialUpdate(FlightDto flightDto, @MappingTarget Flight flight);
}