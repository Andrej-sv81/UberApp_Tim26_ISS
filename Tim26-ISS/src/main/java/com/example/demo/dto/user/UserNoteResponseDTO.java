package com.example.demo.dto.user;

public class UserNoteResponseDTO {
    private Integer id;
    private String date;
    private String message;

    public UserNoteResponseDTO(){};
    public UserNoteResponseDTO(Integer id, String date, String message) {
        this.id = id;
        this.date = date;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "UserNoteResponseDTO{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
