package com.example.demo.dto;

public class DriverDocumentsRequestDTO {
    private String name;
    private String documentImage;

    public DriverDocumentsRequestDTO() {
    }

    public DriverDocumentsRequestDTO(String name, String documentImage) {
        this.name = name;
        this.documentImage = documentImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocumentImage() {
        return documentImage;
    }

    public void setDocumentImage(String documentImage) {
        this.documentImage = documentImage;
    }

    @Override
    public String toString() {
        return "DriverDocumentsRequestDTO{" +
                "name='" + name + '\'' +
                ", documentImage='" + documentImage + '\'' +
                '}';
    }
}
