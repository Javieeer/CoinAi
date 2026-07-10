package com.coinai.api.security.service;

import com.coinai.api.user.entity.User;

public interface AuthenticatedUserService {

    User getCurrentUser();

}