package com.getir.framework.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractEntity {

    @Id
    private UUID id;

    private String createdBy;

    private Instant createdAt;

    private String modifiedBy;

    private Instant modifiedAt;
}
