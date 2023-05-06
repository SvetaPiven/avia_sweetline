package com.avia.mapper;

import com.avia.dto.requests.TicketStatusDto;
import com.avia.model.entity.TicketStatus;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TicketStatusMapper {
    TicketStatus toEntity(TicketStatusDto ticketStatusDto);

    TicketStatusDto toDto(TicketStatus ticketStatus);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TicketStatus partialUpdate(TicketStatusDto ticketStatusDto, @MappingTarget TicketStatus ticketStatus);
}