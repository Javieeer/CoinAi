package com.coinai.api.user.mapper;

import com.coinai.api.user.dto.request.RegisterRequest;
import com.coinai.api.user.dto.response.RegisterResponse;
import com.coinai.api.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "profilePicture", ignore = true)
    @Mapping(target = "preferredCurrency", constant = "COP")
    @Mapping(target = "timezone", constant = "America/Bogota")
    @Mapping(target = "language", constant = "es")
    @Mapping(target = "emailVerified", constant = "false")
    @Mapping(target = "status", expression = "java(com.coinai.api.user.entity.UserStatus.ACTIVE)")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    User toEntity(RegisterRequest request);

    RegisterResponse toResponse(User user);

}