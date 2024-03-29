package com.example.demo.dto.user;

//ADMIN DTO
public class UserNoteRequestDTO {
    private String message;

    public UserNoteRequestDTO(){};
    public UserNoteRequestDTO(String message) {
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
        return "UserNoteRequestDTO{" +
                "message='" + message + '\'' +
                '}';
    }
}
