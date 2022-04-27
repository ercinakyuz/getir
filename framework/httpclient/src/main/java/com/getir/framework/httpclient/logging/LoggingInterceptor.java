package com.getir.framework.httpclient.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(final HttpRequest httpRequest, final byte[] requestBody, final ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {

        log.info("Request method: {}, uri: {}, headers:{}, body: {}", httpRequest.getMethod(), httpRequest.getURI(), httpRequest.getHeaders(), new String(requestBody, StandardCharsets.UTF_8));
        final ClientHttpResponse clientHttpResponse = clientHttpRequestExecution.execute(httpRequest, requestBody);
        log.info("Response status: {}, headers: {}, body: {}", clientHttpResponse.getStatusCode(), clientHttpResponse.getHeaders(), getBody(clientHttpResponse));
        return clientHttpResponse;
    }

    private String getBody(final ClientHttpResponse clientHttpResponse) {
        String body;
        try {
            body = StreamUtils.copyToString(clientHttpResponse.getBody(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            body = null;
        }
        return body;
    }
}
