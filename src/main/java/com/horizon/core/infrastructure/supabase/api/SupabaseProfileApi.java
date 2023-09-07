package com.horizon.core.infrastructure.supabase.api;

import com.horizon.core.common.HttpStructure;
import com.horizon.core.infrastructure.supabase.structure.SupabaseEndpoint;
import com.horizon.core.infrastructure.supabase.structure.SupabaseHeader;
import com.horizon.core.response.UserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import java.net.URI;

@Service
public class SupabaseProfileApi {

    @Value("${supabase.publickey}")
    private String supabasePublicKey;
    @Value("${supabase.server}")
    private String supabaseServer;
    @Inject
    private RestTemplate restTemplate;

    public UserResponse getUserWithId(String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(SupabaseHeader.API_KEY, supabasePublicKey);
        headers.add(HttpHeaders.ACCEPT, HttpStructure.ACCEPTS_TYPE);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String queryValue = "eq." + id;
        params.add("id", queryValue);

        String resourceUrl = supabaseServer + SupabaseEndpoint.PROFILES;
        URI url = UriComponentsBuilder
                .fromUriString(resourceUrl)
                .queryParams(params)
                .build()
                .toUri();

        HttpEntity<Object> entity = new HttpEntity<>(null, headers);
        ResponseEntity<UserResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                UserResponse.class
        );

        return response.getBody();
    }
}
