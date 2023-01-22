package com.example.demo.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserLoginRequestDTO {
    @Email(message = "Email needs to be in the correct format!")
    private String email;
    @NotBlank(message = "Password field can't be empty!")
    private String password;

    public UserLoginRequestDTO(){};
    public UserLoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLoginRequestDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
