package com.horizon.core.infrastructure.supabase.api;

import com.horizon.core.domain.UserDto;
import com.horizon.core.infrastructure.supabase.structure.SupabaseEndpoint;
import com.horizon.core.infrastructure.supabase.structure.SupabaseHeader;
import com.horizon.core.response.LoginResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;

@Service
public class AuthorizationApi {
    @Value("${supabase.publickey}")
    private String supabasePublicKey;
    @Value("${supabase.server}")
    private String supabaseServer;
    @Inject
    private RestTemplate restTemplate;

    public LoginResponse signup(UserDto userDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(SupabaseHeader.API_KEY, supabasePublicKey);

        String resourceUrl = supabaseServer + SupabaseEndpoint.SIGNUP;

        HttpEntity<UserDto> entity = new HttpEntity<>(userDto, headers);
        ResponseEntity<LoginResponse> response = restTemplate.exchange(
                resourceUrl,
                HttpMethod.POST,
                entity,
                LoginResponse.class);

        return response.getBody();
    }
}
