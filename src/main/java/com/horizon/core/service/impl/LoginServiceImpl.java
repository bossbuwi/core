package com.horizon.core.service.impl;

import com.horizon.core.domain.UserDto;
import com.horizon.core.request.LoginRequest;
import com.horizon.core.service.LoginService;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Override
    public UserDto getLoginCredentials(LoginRequest loginRequest) {
        UserDto userDto = new UserDto();
        userDto.setEmail(loginRequest.getEmail());
        userDto.setPassword(loginRequest.getPassword());
        return userDto;
    }
}
