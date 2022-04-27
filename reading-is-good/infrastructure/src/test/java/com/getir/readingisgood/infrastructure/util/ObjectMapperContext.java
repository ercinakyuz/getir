package com.getir.readingisgood.infrastructure.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;

public class ObjectMapperContext {

    private final ObjectMapper objectMapper;

    public ObjectMapperContext() {
        objectMapper = new ObjectMapper();
    }

    public byte[] objectToByteArray(final Object object) throws IOException {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper.writeValueAsBytes(object);
    }

    public String objectToString(final Object object) throws IOException {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper.writeValueAsString(object);
    }

    public <TResponse> TResponse mvcResultToResponse(final Class<TResponse> classOfResponse, final MvcResult mvcResult) throws IOException {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), classOfResponse);
    }
}
