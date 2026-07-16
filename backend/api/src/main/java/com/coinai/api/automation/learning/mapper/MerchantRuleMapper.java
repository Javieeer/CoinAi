package com.coinai.api.automation.learning.mapper;

import com.coinai.api.automation.learning.dto.response.MerchantRuleResponse;
import com.coinai.api.automation.learning.entity.MerchantRule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MerchantRuleMapper {

    @Mapping(target = "category", source = "category.name")
    MerchantRuleResponse toResponse(MerchantRule merchantRule);

}