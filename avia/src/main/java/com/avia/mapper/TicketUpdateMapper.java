package com.avia.mapper;

import com.avia.dto.TicketUpdateDto;
import com.avia.model.entity.Ticket;
import com.avia.model.entity.TicketClass;
import com.avia.model.entity.TicketStatus;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {PassengerMapper.class, TicketStatusMapper.class, FlightMapper.class, AirlineMapper.class})
public interface TicketUpdateMapper {
    @Mappings({
            @Mapping(target = "idTicket", ignore = true),
            @Mapping(target = "created", ignore = true),
            @Mapping(target = "changed", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))"),
            @Mapping(target = "isDeleted", ignore = true)
    })
    Ticket toEntity(TicketUpdateDto ticketUpdateDto);

    TicketUpdateDto toDto(Ticket ticket);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Ticket partialUpdate(TicketUpdateDto ticketUpdateDto, @MappingTarget Ticket ticket);

}