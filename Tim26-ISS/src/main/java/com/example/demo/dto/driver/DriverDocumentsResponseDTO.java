package com.example.demo.dto.driver;

import com.example.demo.model.Document;

import java.util.ArrayList;
import java.util.List;

public class DriverDocumentsResponseDTO {

    private Integer id;
    private String name;
    private String documentImage;
    private Integer driverId;

    public DriverDocumentsResponseDTO() {
    }

    public DriverDocumentsResponseDTO(Integer id, String name, String documentImage, Integer driverId) {
        this.id = id;
        this.name = name;
        this.documentImage = documentImage;
        this.driverId = driverId;
    }

    public DriverDocumentsResponseDTO(Document doc) {
        this.id = doc.getId();
        this.name = doc.getName();
        this.documentImage = doc.getPicture();
        this.driverId = doc.getDriver().getId();
    }

    public static List<DriverDocumentsResponseDTO> returnDocs(List<Document> documents){
        List<DriverDocumentsResponseDTO> docs = new ArrayList<>();
        for (Document doc : documents){
            DriverDocumentsResponseDTO d = new DriverDocumentsResponseDTO(doc);
            docs.add(d);
        }
        return docs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    @Override
    public String toString() {
        return "DriverDocumentsRequestDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", documentImage='" + documentImage + '\'' +
                ", driverId=" + driverId +
                '}';
    }
}
