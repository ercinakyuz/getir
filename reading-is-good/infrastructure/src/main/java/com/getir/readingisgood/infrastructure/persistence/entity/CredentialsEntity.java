package com.getir.readingisgood.infrastructure.persistence.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
public class CredentialsEntity {

    @Indexed(unique = true)
    private String email;

    private String password;
}
