package com.example.demo.dto.user;

import javax.validation.constraints.Pattern;

public class ResetPasswordDTO {
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$", message = "Password must be at least 6 characters long and contain 1 letter and 1 digit!")
    private String newPassword;
    @Pattern(regexp = "^\\d{7}$", message = "Reset code must be 7 digits long!")
    private String code;

    public ResetPasswordDTO(){};

    public ResetPasswordDTO(String newPassword, String code) {
        this.newPassword = newPassword;
        this.code = code;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String oldPassword) {
        this.code = oldPassword;
    }
}
