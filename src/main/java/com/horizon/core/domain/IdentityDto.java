package com.horizon.core.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
public class IdentityDto {
    private String id;
    private UUID userId;
    private IdentityDataDto identityData;
    private String provider;
    private Timestamp lastSignInAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
