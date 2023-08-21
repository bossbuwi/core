package com.horizon.core.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class IdentityDataDto {
    private String email;
    private UUID sub;
}
