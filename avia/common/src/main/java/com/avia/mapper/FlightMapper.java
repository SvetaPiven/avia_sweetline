package com.avia.mapper;

import com.avia.model.entity.Flight;
import com.avia.model.request.FlightRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {AirportMapper.class})
public interface FlightMapper {

    @Mapping(target = "planeType.idPlaneType", source = "idPlaneType")
    @Mapping(target = "departureAirport.idAirport", source = "idDepartureAirport")
    @Mapping(target = "arrivalAirport.idAirport", source = "idArrivalAirport")
    @Mapping(target = "flightStatus.idFlightStatus", source = "idFlightStatus")
    @Mapping(target = "created", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))")
    @Mapping(target = "changed", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))")
    Flight toEntity(FlightRequest flightRequest);

    @Mapping(target = "planeType.idPlaneType", source = "idPlaneType")
    @Mapping(target = "departureAirport.idAirport", source = "idDepartureAirport")
    @Mapping(target = "arrivalAirport.idAirport", source = "idArrivalAirport")
    @Mapping(target = "flightStatus.idFlightStatus", source = "idFlightStatus")
    @Mapping(target = "changed", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Flight partialUpdate(FlightRequest flightRequest, @MappingTarget Flight flight);
}