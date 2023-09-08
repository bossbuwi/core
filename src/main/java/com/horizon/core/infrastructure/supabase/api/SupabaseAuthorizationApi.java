package com.horizon.core.infrastructure.supabase.api;

import com.horizon.core.common.HttpStructure;
import com.horizon.core.domain.UserDto;
import com.horizon.core.infrastructure.supabase.structure.SupabaseEndpoint;
import com.horizon.core.infrastructure.supabase.structure.SupabaseHeader;
import com.horizon.core.response.AuthResponse;
import com.horizon.core.response.UserResponse;
import com.horizon.core.security.service.impl.UserServiceImpl;
import com.horizon.core.utils.AuthUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import java.net.URI;
import java.util.Objects;

@Service
public class SupabaseAuthorizationApi {

    @Value("${supabase.publickey}")
    private String supabasePublicKey;
    @Value("${supabase.server}")
    private String supabaseServer;
    @Inject
    private RestTemplate restTemplate;
    @Inject
    private UserServiceImpl userService;
    @Inject
    private AuthUtil authUtil;

    public AuthResponse signup(UserDto userDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(SupabaseHeader.API_KEY, supabasePublicKey);

        String resourceUrl = supabaseServer + SupabaseEndpoint.SIGNUP;

        HttpEntity<UserDto> entity = new HttpEntity<>(userDto, headers);
        ResponseEntity<AuthResponse> response = restTemplate.exchange(
                resourceUrl,
                HttpMethod.POST,
                entity,
                AuthResponse.class);

        UserResponse userResponse = Objects.requireNonNull(response.getBody()).getUser();

        UserDetails userDetails = userService.loadUserByUsername(userResponse.getId());
        UsernamePasswordAuthenticationToken authenticationToken = authUtil.createToken(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        return response.getBody();
    }

    public AuthResponse login(UserDto userDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(SupabaseHeader.API_KEY, supabasePublicKey);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String queryValue = HttpStructure.PASSWORD;
        params.add(HttpStructure.GRANT_TYPE, queryValue);

        String resourceUrl = supabaseServer + SupabaseEndpoint.TOKEN;
        URI url = UriComponentsBuilder
                .fromUriString(resourceUrl)
                .queryParams(params)
                .build()
                .toUri();

        HttpEntity<UserDto> entity = new HttpEntity<>(userDto, headers);
        ResponseEntity<AuthResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                AuthResponse.class
        );

        UserResponse userResponse = Objects.requireNonNull(response.getBody()).getUser();

        UserDetails userDetails = userService.loadUserByUsername(userResponse.getId());
        UsernamePasswordAuthenticationToken authenticationToken = authUtil.createToken(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        return response.getBody();
    }
}
