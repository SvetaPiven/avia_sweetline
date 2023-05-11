package com.avia.mapper;

import com.avia.model.dto.DocumentTypeDto;
import com.avia.model.dto.TicketClassDto;
import com.avia.model.entity.DocumentType;
import com.avia.model.entity.TicketClass;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TicketClassMapper {
    @Mappings({
            @Mapping(target = "created", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))"),
            @Mapping(target = "changed", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))"),
            @Mapping(target = "isDeleted", constant = "false")
    })
    TicketClass toEntity(TicketClassDto ticketClassDto);

    DocumentTypeDto toDto(DocumentType documentType);

    @Mappings({
            @Mapping(target = "created", ignore = true),
            @Mapping(target = "changed", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))"),
            @Mapping(target = "isDeleted", ignore = true)
    })
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TicketClass partialUpdate(TicketClassDto ticketClassDto, @MappingTarget TicketClass ticketClass);
}