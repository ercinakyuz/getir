package com.getir.framework.api.model.error.response;


import com.getir.framework.api.model.error.contract.ErrorContract;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private ErrorContract error;
}
