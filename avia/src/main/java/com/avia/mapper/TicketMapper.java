package com.avia.mapper;

import com.avia.dto.TicketDto;
import com.avia.model.entity.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {AirlineMapper.class, FlightMapper.class, PassengerMapper.class})
public interface TicketMapper {
    Ticket toEntity(TicketDto ticketDto);

    TicketDto toDto(Ticket ticket);

}