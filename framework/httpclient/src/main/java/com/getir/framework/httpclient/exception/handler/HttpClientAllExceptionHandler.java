package com.getir.framework.httpclient.exception.handler;

import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class HttpClientAllExceptionHandler extends HttpClientExceptionHandler {

    @Override
    public boolean hasError(final ClientHttpResponse clientHttpResponse) throws IOException {
        return !clientHttpResponse.getStatusCode().is2xxSuccessful();
    }
}
