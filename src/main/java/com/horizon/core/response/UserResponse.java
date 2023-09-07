package com.horizon.core.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {

    @JsonProperty("id")
    private String id;
    @JsonProperty("aud")
    private String aud;
    @JsonProperty("role")
    private String role;
    @JsonProperty("email")
    private String email;
}
