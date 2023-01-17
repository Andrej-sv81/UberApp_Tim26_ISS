package com.example.demo.dto;


import com.example.demo.model.RejectionMessage;

public class RejectionDTO {
    private String reason;
    private String timeOfRejection;

    public RejectionDTO(){};

    public RejectionDTO(String reason, String timeOfRejection) {
        this.reason = reason;
        this.timeOfRejection = timeOfRejection;
    }
    public RejectionDTO(RejectionMessage message){
        if(message != null){
            this.reason = message.getRejectionReason();
            this.timeOfRejection = message.getTimeOfRejection().toString();
        }else {
            this.reason = "";
            this.timeOfRejection = "";
        }

    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTimeOfRejection() {
        return timeOfRejection;
    }

    public void setTimeOfRejection(String timeOfRejection) {
        this.timeOfRejection = timeOfRejection;
    }

    @Override
    public String toString() {
        return "RejectionDTO{" +
                "reason='" + reason + '\'' +
                ", timeOfRejection=" + timeOfRejection +
                '}';
    }
}
