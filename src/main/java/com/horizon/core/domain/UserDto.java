package com.horizon.core.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class UserDto {
    private UUID id;
    private String aud;
    private String role;
    private String email;
    private String password;
    private Timestamp emailConfirmedAt;
    private String phone;
    private Timestamp lastSignItAt;
    private List<IdentityDto> identities = new ArrayList<>();
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
