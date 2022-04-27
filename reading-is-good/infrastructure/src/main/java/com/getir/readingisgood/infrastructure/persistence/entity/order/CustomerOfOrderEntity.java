package com.getir.readingisgood.infrastructure.persistence.entity.order;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.UUID;

@Data
@Builder
public class CustomerOfOrderEntity {

    @Indexed
    private UUID id;

    private String email;
}
