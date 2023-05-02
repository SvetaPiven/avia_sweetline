package com.avia.mapper;

import com.avia.dto.TicketCreateDto;
import com.avia.model.entity.Ticket;
import com.avia.model.entity.TicketClass;
import com.avia.model.entity.TicketStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {AirlineMapper.class, FlightMapper.class, PassengerMapper.class, TicketClass.class, TicketStatus.class})
public interface TicketCreateMapper {
    @Mappings({
            @Mapping(target = "idTicket", ignore = true),
            @Mapping(target = "created", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))"),
            @Mapping(target = "isDeleted", constant = "false")
    })
    Ticket toEntity(TicketCreateDto ticketCreateDto);

    TicketCreateDto toDto(Ticket ticket);

}