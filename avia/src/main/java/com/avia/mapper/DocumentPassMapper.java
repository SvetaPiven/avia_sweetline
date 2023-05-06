package com.avia.mapper;

import com.avia.dto.requests.DocumentPassDto;
import com.avia.model.entity.DocumentPass;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DocumentPassMapper {
    DocumentPass toEntity(DocumentPassDto documentPassDt);

    DocumentPassDto toDto(DocumentPass documentPass);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DocumentPass partialUpdate(DocumentPassDto documentPassDt, @MappingTarget DocumentPass documentPass);
}