package com.getir.readingisgood.domain.model.value.converter;

import com.getir.readingisgood.domain.model.value.Credentials;
import com.getir.readingisgood.infrastructure.persistence.entity.CredentialsEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CredentialsConverter implements Converter<Credentials, CredentialsEntity> {

    @Override
    public CredentialsEntity convert(final Credentials credentials) {
        return CredentialsEntity.builder()
                .email(credentials.getEmail())
                .password(credentials.getPassword())
                .build();
    }
}
