package com.avia.mapper;

import com.avia.model.entity.Ticket;
import com.avia.model.request.TicketRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TicketMapper {

            @Mapping(target = "idPass.idPass", source = "idPass")
            @Mapping(target = "idTicketStatus.idTicketStatus", source = "idTicketStatus", defaultValue = "6")
            @Mapping(target = "idTicketClass.idTicketClass", source = "idTicketClass")
            @Mapping(target = "idFlight.idFlight", source = "idFlight")
            @Mapping(target = "idAirline.idAirline", source = "idAirline")
            @Mapping(target = "numberPlace", constant = "On registration")
            @Mapping(target = "created", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))")
            @Mapping(target = "changed", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))")
    Ticket toEntity(TicketRequest ticketRequest);

            @Mapping(target = "idPass.idPass", source = "idPass")
            @Mapping(target = "idTicketStatus.idTicketStatus", source = "idTicketStatus")
            @Mapping(target = "idTicketClass.idTicketClass", source = "idTicketClass")
            @Mapping(target = "idFlight.idFlight", source = "idFlight")
            @Mapping(target = "idAirline.idAirline", source = "idAirline")
            @Mapping(target = "changed", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Ticket partialUpdate(TicketRequest ticketRequest, @MappingTarget Ticket ticket);
}