package com.avia.mapper;

import com.avia.dto.TicketDto;
import com.avia.model.entity.Ticket;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {PassengerMapper.class, TicketStatusMapper.class, FlightMapper.class, AirlineMapper.class})
public interface TicketMapper {
    Ticket toEntity(TicketDto ticketDto);

    TicketDto toDto(Ticket ticket);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Ticket partialUpdate(TicketDto ticketDto, @MappingTarget Ticket ticket);
}