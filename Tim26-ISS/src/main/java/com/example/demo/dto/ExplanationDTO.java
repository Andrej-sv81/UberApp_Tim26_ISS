package com.example.demo.dto;

public class ExplanationDTO {
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
