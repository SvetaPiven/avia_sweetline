package com.avia.mapper;

import com.avia.model.entity.User;
import com.avia.model.request.UserRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "authenticationInfo.email", source = "email")
    @Mapping(target = "authenticationInfo.userPassword", source = "userPassword")
    @Mapping(target = "idRole.idRole", source = "idRole", defaultValue = "1")
    @Mapping(target = "created", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))")
    @Mapping(target = "changed", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))")
    User toEntity(UserRequest userRequest);

    @Mapping(target = "authenticationInfo.email", source = "email")
    @Mapping(target = "authenticationInfo.userPassword", source = "userPassword")
    @Mapping(target = "idRole.idRole", source = "idRole")
    @Mapping(target = "changed", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserRequest userRequest, @MappingTarget User user);
}