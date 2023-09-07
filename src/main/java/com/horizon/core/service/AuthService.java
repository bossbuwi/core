package com.horizon.core.service;

import com.horizon.core.domain.UserDto;
import com.horizon.core.request.AuthRequest;

public interface AuthService {

    UserDto getLoginCredentials(AuthRequest authRequest);
}
