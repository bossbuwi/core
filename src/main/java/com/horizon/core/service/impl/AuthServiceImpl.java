package com.horizon.core.service.impl;

import com.horizon.core.domain.UserDto;
import com.horizon.core.request.AuthRequest;
import com.horizon.core.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public UserDto getLoginCredentials(AuthRequest authRequest) {
        UserDto userDto = new UserDto();
        userDto.setEmail(authRequest.getEmail());
        userDto.setPassword(authRequest.getPassword());
        return userDto;
    }
}
