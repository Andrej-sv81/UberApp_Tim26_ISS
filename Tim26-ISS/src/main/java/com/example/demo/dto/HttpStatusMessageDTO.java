package com.example.demo.dto;

public class HttpStatusMessageDTO {

    private String message;

    public HttpStatusMessageDTO(){};
    public HttpStatusMessageDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorMessageDTO{" +
                "message='" + message + '\'' +
                '}';
    }
}
