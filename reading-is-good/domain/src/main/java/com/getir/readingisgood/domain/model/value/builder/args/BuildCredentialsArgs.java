package com.getir.readingisgood.domain.model.value.builder.args;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BuildCredentialsArgs {

    private String email;

    private String password;
}
