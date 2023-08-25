package com.horizon.core.resource;

import com.horizon.core.domain.UserDto;
import com.horizon.core.infrastructure.supabase.api.SupabaseAuthorizationApi;
import com.horizon.core.request.LoginRequest;
import com.horizon.core.response.AuthResponse;
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
public class AuthResource {
    @Inject
    private LoginServiceImpl loginService;
    @Inject
    private SupabaseAuthorizationApi supabaseAuthorizationApi;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody LoginRequest loginRequest) {
        UserDto userDto = loginService.getLoginCredentials(loginRequest);
        return new ResponseEntity<>(supabaseAuthorizationApi.signup(userDto), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        UserDto userDto = loginService.getLoginCredentials(loginRequest);
        return new ResponseEntity<>(supabaseAuthorizationApi.login(userDto), HttpStatus.OK);
    }
}
