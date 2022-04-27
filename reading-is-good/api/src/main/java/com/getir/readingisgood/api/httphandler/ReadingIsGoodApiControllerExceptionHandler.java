package com.getir.readingisgood.api.httphandler;


import com.getir.framework.api.httphandler.ControllerExceptionHandler;
import com.getir.framework.api.model.error.contract.builder.ErrorContractBuilder;
import com.getir.framework.core.model.error.GenericError;
import com.getir.framework.httpclient.exception.HttpClientException;
import io.jsonwebtoken.JwtException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.getir.framework.core.model.exception.ExceptionState.*;

@RestControllerAdvice
public class ReadingIsGoodApiControllerExceptionHandler extends ControllerExceptionHandler {

    public ReadingIsGoodApiControllerExceptionHandler(final ErrorContractBuilder errorContractBuilder) {
        super(errorContractBuilder);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity handleJwtException(final JwtException jwtException) {
        logger.error(String.format("An exception occured with code: %s, message: %s ", UNKNOWN, jwtException.getMessage()), jwtException);
        return buildErrorResponseEntity(INVALID, GenericError.CODE);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity handleDuplicateKeyException(final DuplicateKeyException duplicateKeyException) {
        logger.error(String.format("An exception occured with code: %s, message: %s ", UNKNOWN, duplicateKeyException.getMessage()), duplicateKeyException);
        return buildErrorResponseEntity(ALREADY_EXIST, "DKE-1");
    }

}

