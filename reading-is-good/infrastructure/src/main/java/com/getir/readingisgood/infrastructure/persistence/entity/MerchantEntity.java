package com.getir.readingisgood.infrastructure.persistence.entity;

import com.getir.framework.data.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document("merchants")
public class MerchantEntity extends AbstractEntity {

    private CredentialsEntity credentials;
}
