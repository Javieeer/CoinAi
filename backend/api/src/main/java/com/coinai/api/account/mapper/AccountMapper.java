package com.coinai.api.account.mapper;

import com.coinai.api.account.dto.response.AccountResponse;
import com.coinai.api.account.entity.Account;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountResponse toResponse(Account account);

    List<AccountResponse> toResponseList(List<Account> accounts);

}