package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class DriverMultipleDocsResponseDTO {
    List<DriverDocumentsResponseDTO> docs;

    public DriverMultipleDocsResponseDTO() {
        this.docs = new ArrayList<DriverDocumentsResponseDTO>();
    }

    public DriverMultipleDocsResponseDTO(List<DriverDocumentsResponseDTO> docs) {
        this.docs = docs;
    }

    public List<DriverDocumentsResponseDTO> getDocs() {
        return docs;
    }

    public void setDocs(List<DriverDocumentsResponseDTO> docs) {
        this.docs = docs;
    }

    @Override
    public String toString() {
        return "DriverMultipleDocsResponseDTO{" +
                "docs=" + docs +
                '}';
    }
}
