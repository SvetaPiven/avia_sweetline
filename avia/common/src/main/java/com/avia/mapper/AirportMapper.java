package com.avia.mapper;

import com.avia.model.entity.Airport;
import com.avia.model.request.AirportRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AirportMapper {
    @Mappings({
            @Mapping(target = "created", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))"),
            @Mapping(target = "changed", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))"),
//            @Mapping(target = "isDeleted", constant = "false")
    })
    Airport toEntity(AirportRequest airportRequest);

    @Mappings({
            @Mapping(target = "created", ignore = true),
            @Mapping(target = "changed", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))"),
//            @Mapping(target = "isDeleted", ignore = true)
    })
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Airport partialUpdate(AirportRequest airportRequest, @MappingTarget Airport airport);
}