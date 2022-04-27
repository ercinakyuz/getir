package com.getir.framework.httpclient.configuration;

import com.getir.framework.httpclient.exception.handler.HttpClientExceptionHandler;
import com.getir.framework.httpclient.logging.LoggingInterceptor;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

public abstract class AbstractHttpClientConfiguration {

    protected RestTemplate restTemplate(final AbstractHttpClientProperties httpClientProperties) {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(httpClientProperties.getBaseUrl()));
        restTemplate.getInterceptors().add(new LoggingInterceptor());
        final SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setReadTimeout(httpClientProperties.getTimeout() * 1000);
        simpleClientHttpRequestFactory.setOutputStreaming(false);
        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(simpleClientHttpRequestFactory));
        restTemplate.setErrorHandler(new HttpClientExceptionHandler());
        return restTemplate;
    }
}
