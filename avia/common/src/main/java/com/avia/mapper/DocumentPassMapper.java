package com.avia.mapper;

import com.avia.model.entity.DocumentPass;
import com.avia.model.request.DocumentPassRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DocumentPassMapper {

    @Mapping(target = "documentType.idDocumentType", source = "idDocumentType")
    @Mapping(target = "passenger.idPass", source = "idPass")
    @Mapping(target = "created", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))")
    @Mapping(target = "changed", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))")
    DocumentPass toEntity(DocumentPassRequest documentPassRequest);

    @Mapping(target = "documentType.idDocumentType", source = "idDocumentType")
    @Mapping(target = "passenger.idPass", source = "idPass")
    @Mapping(target = "changed", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DocumentPass partialUpdate(DocumentPassRequest documentPassDt, @MappingTarget DocumentPass documentPass);
}