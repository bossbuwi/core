package com.horizon.core.service;

import com.horizon.core.domain.UserDto;
import com.horizon.core.request.LoginRequest;

public interface LoginService {
    UserDto getLoginCredentials(LoginRequest loginRequest);
}
