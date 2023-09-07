package com.horizon.core.resource;

import com.horizon.core.domain.UserDto;
import com.horizon.core.infrastructure.supabase.api.SupabaseAuthorizationApi;
import com.horizon.core.request.AuthRequest;
import com.horizon.core.response.AuthResponse;
import com.horizon.core.service.impl.AuthServiceImpl;
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
    private AuthServiceImpl loginService;
    @Inject
    private SupabaseAuthorizationApi supabaseAuthorizationApi;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody AuthRequest authRequest) {
        UserDto userDto = loginService.getLoginCredentials(authRequest);
        return new ResponseEntity<>(supabaseAuthorizationApi.signup(userDto), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        UserDto userDto = loginService.getLoginCredentials(authRequest);
        return new ResponseEntity<>(supabaseAuthorizationApi.login(userDto), HttpStatus.OK);
    }
}
