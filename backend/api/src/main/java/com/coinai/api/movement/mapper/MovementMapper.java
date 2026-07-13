package com.coinai.api.movement.mapper;

import com.coinai.api.movement.dto.request.CreateMovementRequest;
import com.coinai.api.movement.dto.response.MovementResponse;
import com.coinai.api.movement.entity.Movement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovementMapper {

    Movement toEntity(CreateMovementRequest request);

    @Mapping(target = "accountId", source = "account.id")
    @Mapping(target = "destinationAccountId", source = "destinationAccount.id")
    @Mapping(target = "paymentMethodId", source = "paymentMethod.id")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "subcategoryId", source = "subcategory.id")
    MovementResponse toResponse(Movement movement);

    List<MovementResponse> toResponseList(List<Movement> movements);

}