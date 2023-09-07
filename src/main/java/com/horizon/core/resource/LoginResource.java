package com.horizon.core.resource;

import com.horizon.core.domain.UserDto;
import com.horizon.core.infrastructure.supabase.api.AuthorizationApi;
import com.horizon.core.request.LoginRequest;
import com.horizon.core.response.LoginResponse;
import com.horizon.core.service.impl.LoginServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/auth")
public class LoginResource {
    @Inject
    private LoginServiceImpl loginService;
    @Inject
    private AuthorizationApi authorizationApi;

    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> signup(@RequestBody LoginRequest loginRequest) {
        UserDto userDto = loginService.getLoginCredentials(loginRequest);
        return new ResponseEntity<>(authorizationApi.signup(userDto), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        UserDto userDto = loginService.getLoginCredentials(loginRequest);
        return new ResponseEntity<>(authorizationApi.login(userDto), HttpStatus.OK);
    }
}
