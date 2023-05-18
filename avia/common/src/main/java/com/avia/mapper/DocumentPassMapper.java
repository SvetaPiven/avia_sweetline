package com.avia.mapper;

import com.avia.model.entity.DocumentPass;
import com.avia.model.dto.DocumentPassDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DocumentPassMapper {
    @Mappings({
            @Mapping(target = "idDocumentPass", ignore = true),
            @Mapping(target = "idDocumentType.idDocumentType", source = "idDocumentType"),
            @Mapping(target = "idPass.idPass", source = "idPass"),
            @Mapping(target = "created", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))"),
            @Mapping(target = "changed", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))"),
            @Mapping(target = "isDeleted", constant = "false")
    })
    DocumentPass toEntity(DocumentPassDto documentPassDto);

    @Mappings({
            @Mapping(target = "idDocumentPass", ignore = true),
            @Mapping(target = "idDocumentType.idDocumentType", source = "idDocumentType"),
            @Mapping(target = "idPass.idPass", source = "idPass"),
            @Mapping(target = "created", ignore = true),
            @Mapping(target = "changed", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))"),
            @Mapping(target = "isDeleted", ignore = true)
    })
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DocumentPass partialUpdate(DocumentPassDto documentPassDt, @MappingTarget DocumentPass documentPass);
}