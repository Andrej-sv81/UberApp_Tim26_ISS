package com.example.demo.dto;

import javax.validation.constraints.NotBlank;

public class ExplanationDTO {
    @NotBlank
    private String reason;

    public ExplanationDTO() {
    }

    public ExplanationDTO(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "ExplanationDTO{" +
                "reason='" + reason + '\'' +
                '}';
    }
}
