package com.taxidispatcher.core;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

public abstract class TestHelper {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public TestHelper(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    protected ResultActions getJson(String url, MultiValueMap<String, String> params, String bearer) throws Exception {
        var req = get(url)
                .contentType(MediaType.APPLICATION_JSON);

        if (bearer != null && !bearer.isBlank()) {
            if (!bearer.startsWith("Bearer ")) bearer = "Bearer " + bearer;

            req.header("Authorization", bearer);
        }

        if (Optional.ofNullable(params).isPresent()) {
            req.params(params);
        }

        return mockMvc.perform(req);
    }

    protected ResultActions getJson(String url, String bearer) throws Exception {
        return getJson(url, null, bearer);
    }

    protected ResultActions getJson(String url, MultiValueMap<String, String> params) throws Exception {
        return getJson(url, params, null);
    }

    protected ResultActions getJson(String url) throws Exception {
        return getJson(url, null, null);
    }

    protected ResultActions postJson(String url, String body, String bearer) throws Exception {
        var req = post(url)
                .contentType(MediaType.APPLICATION_JSON);

        if (bearer != null && !bearer.isBlank()) {
            if (!bearer.startsWith("Bearer ")) bearer = "Bearer " + bearer;

            req.header("Authorization", bearer);
        }

        if (body != null && !body.isBlank()) {
            req.content(body);
        }

        return mockMvc.perform(req);
    }

    protected ResultActions postJson(String url, String body) throws Exception {
        return postJson(url, body, null);
    }

    protected ResultActions patchJson(String url, String body, String bearer) throws Exception {
        var req = patch(url)
                .contentType(MediaType.APPLICATION_JSON);

        if (bearer != null && !bearer.isBlank()) {
            if (!bearer.startsWith("Bearer ")) bearer = "Bearer " + bearer;

            req.header("Authorization", bearer);
        }

        if (body != null && !body.isBlank()) {
            req.content(body);
        }

        return mockMvc.perform(req);
    }

    protected ResultActions patchJson(String url, String body) throws Exception {
        return patchJson(url, body, null);
    }

    protected ResultActions deleteJson(String url, String body, String bearer) throws Exception {
        var req = delete(url)
                .contentType(MediaType.APPLICATION_JSON);

        if (bearer != null && !bearer.isBlank()) {
            if (!bearer.startsWith("Bearer ")) bearer = "Bearer " + bearer;

            req.header("Authorization", bearer);
        }

        if (body != null && !body.isBlank()) {
            req.content(body);
        }

        return mockMvc.perform(req);
    }

    protected ResultActions deleteJson(String url, String body) throws Exception {
        return deleteJson(url, body, null);
    }

    protected String convertString(Object object) throws Exception {
        return objectMapper.writeValueAsString(object);
    }

    protected <T> T read(String str, Class<T> valueType) throws Exception {
        return objectMapper.readValue(str, valueType);
    }

    protected void log(String message) {
        System.out.println(message);
    }
}
