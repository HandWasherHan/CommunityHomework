package com.han.community.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Response<T> {
    private int statusCode;
    private String message;
    private T entity;

    public Response() {

    }

    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String str = objectMapper.writeValueAsString(this);
            return str;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Response fail(String message) {
        return new Response(400, message, null);
    }

    public static Response fail(int code, String message) {
        return new Response(code, message, null);
    }

    public static Response success(String message) {
        return new Response(200, message, null);
    }
}
