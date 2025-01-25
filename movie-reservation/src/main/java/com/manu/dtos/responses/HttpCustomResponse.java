package com.manu.dtos.responses;

public class HttpCustomResponse<T> {

    private int statusCode;
    private T content;
    private String message;

    public HttpCustomResponse(int statusCode, T content, String message) {
        this.statusCode = statusCode;
        this.content = content;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public T getContent() {
        return content;
    }

    public String getMessage() {
        return message;
    }
}
