package com.avia.mapper;

import com.avia.model.entity.Ticket;
import com.avia.model.request.TicketRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TicketMapper {

    @Mapping(target = "passenger.idPass", source = "idPass")
    @Mapping(target = "ticketStatus.idTicketStatus", source = "idTicketStatus", defaultValue = "6")
    @Mapping(target = "ticketClass.idTicketClass", source = "idTicketClass")
    @Mapping(target = "flight.idFlight", source = "idFlight")
    @Mapping(target = "airline.idAirline", source = "idAirline")
    @Mapping(target = "numberPlace", constant = "On registration")
    @Mapping(target = "created", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))")
    @Mapping(target = "changed", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))")
    Ticket toEntity(TicketRequest ticketRequest);

    @Mapping(target = "passenger.idPass", source = "idPass")
    @Mapping(target = "ticketStatus.idTicketStatus", source = "idTicketStatus")
    @Mapping(target = "ticketClass.idTicketClass", source = "idTicketClass")
    @Mapping(target = "flight.idFlight", source = "idFlight")
    @Mapping(target = "airline.idAirline", source = "idAirline")
    @Mapping(target = "changed", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Ticket partialUpdate(TicketRequest ticketRequest, @MappingTarget Ticket ticket);
}