package com.getir.framework.httpclient.configuration;

import lombok.Data;

@Data
public abstract class AbstractHttpClientProperties {

    private String baseUrl;

    private int timeout;
}
