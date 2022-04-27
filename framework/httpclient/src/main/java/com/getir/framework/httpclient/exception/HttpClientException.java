package com.getir.framework.httpclient.exception;

import lombok.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;



@Value
public class HttpClientException extends RuntimeException {

    private HttpStatus httpStatus;
    private Object responseBody;
    private HttpHeaders responseHeaders;

    public HttpClientException(final String message, final HttpStatus httpStatus, final Object responseBody, final HttpHeaders responseHeaders) {
        super(message);
        this.httpStatus = httpStatus;
        this.responseBody = responseBody;
        this.responseHeaders = responseHeaders;
    }
}