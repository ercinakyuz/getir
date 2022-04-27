package com.getir.readingisgood.domain.model.value.builder;

import com.getir.readingisgood.domain.model.value.Credentials;
import com.getir.readingisgood.domain.model.value.builder.args.BuildCredentialsArgs;
import com.getir.readingisgood.domain.model.value.dto.LoadCredentialDto;
import com.getir.readingisgood.infrastructure.persistence.entity.CredentialsEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CredentialsBuilder {

    public Credentials buildCredentials(final CredentialsEntity credentialEntity){
        return Credentials.load(LoadCredentialDto.builder()
                .email(credentialEntity.getEmail())
                .password(credentialEntity.getPassword())
                .build());
    }

    public Credentials buildNewCredentials(final BuildCredentialsArgs args){
        return Credentials.create(LoadCredentialDto.builder()
                .email(args.getEmail())
                .password(args.getPassword())
                .build());
    }
}
