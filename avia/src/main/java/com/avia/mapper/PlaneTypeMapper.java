package com.avia.mapper;

import com.avia.model.dto.PlaneTypeDto;
import com.avia.model.entity.PlaneType;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlaneTypeMapper {
    @Mappings({
            @Mapping(target = "created", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))"),
            @Mapping(target = "changed", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))"),
            @Mapping(target = "isDeleted", constant = "false")
    })
    PlaneType toEntity(PlaneTypeDto planeTypeDto);

    PlaneTypeDto toDto(PlaneType planeType);

    @Mappings({
            @Mapping(target = "created", ignore = true),
            @Mapping(target = "changed", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))"),
            @Mapping(target = "isDeleted", ignore = true)
    })
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PlaneType partialUpdate(PlaneTypeDto planeTypeDto, @MappingTarget PlaneType planeType);
}