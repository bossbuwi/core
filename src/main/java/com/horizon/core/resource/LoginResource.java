package com.horizon.core.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
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
@RequestMapping("/api")
public class LoginResource {
    @Inject
    private LoginServiceImpl loginService;
    @Inject
    private AuthorizationApi authorizationApi;

    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> signup(@RequestBody LoginRequest loginRequest) throws JsonProcessingException {
        UserDto userDto = loginService.getLoginCredentials(loginRequest);
        return new ResponseEntity<>(authorizationApi.signup(userDto), HttpStatus.OK);
    }
}
