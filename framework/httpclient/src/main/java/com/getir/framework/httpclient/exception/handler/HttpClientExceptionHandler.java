package com.getir.framework.httpclient.exception.handler;

import com.getir.framework.httpclient.exception.HttpClientException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class HttpClientExceptionHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(final ClientHttpResponse clientHttpResponse) throws IOException {
        return !clientHttpResponse.getStatusCode().is2xxSuccessful() && !clientHttpResponse.getStatusCode().is4xxClientError();
    }

    @Override
    public void handleError(final ClientHttpResponse clientHttpResponse) throws IOException {
        throw new HttpClientException("A http client exception occurred", clientHttpResponse.getStatusCode(), responseBody(clientHttpResponse), clientHttpResponse.getHeaders());
    }

    private Object responseBody(final ClientHttpResponse clientHttpResponse) {
        Object responseBody;
        try {
            responseBody = new ObjectMapper().readValue(clientHttpResponse.getBody(), Object.class);
        } catch (Exception e) {
            responseBody = null;
        }
        return responseBody;
    }
}
