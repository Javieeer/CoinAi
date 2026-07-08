package com.coinai.api.account.mapper;

import com.coinai.api.account.dto.response.AccountResponse;
import com.coinai.api.account.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountResponse toResponse(Account account);

}