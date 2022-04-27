package com.getir.readingisgood.domain.model.value;

import com.getir.readingisgood.domain.model.value.dto.LoadCredentialDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

@Value
@Builder(access = AccessLevel.PRIVATE)
public class Credentials {

    private String email;

    private String password;

    public static Credentials load(final LoadCredentialDto loadCredentialDto) {
        validateLoadCredentialDto(loadCredentialDto);
        return Credentials.builder()
                .email(loadCredentialDto.getEmail())
                .password(loadCredentialDto.getPassword())
                .build();
    }

    public static Credentials create(final LoadCredentialDto loadCredentialDto) {
        validateLoadCredentialDto(loadCredentialDto);
        return Credentials.builder()
                .email(loadCredentialDto.getEmail())
                .password(hashPassword(loadCredentialDto.getPassword()))
                .build();
    }

    private static void validateLoadCredentialDto(final LoadCredentialDto loadCredentialDto) {

    }

    private static String hashPassword(final String password) {
        return sha256Hex(password);
    }

    public boolean isValidPassword(final String password) {
        return this.password.equals(hashPassword(password));

    }
}
