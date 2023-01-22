package com.example.demo.service.interfaces;

import com.example.demo.dto.driver.DriverDocumentsRequestDTO;
import com.example.demo.dto.driver.DriverDocumentsResponseDTO;
import com.example.demo.dto.driver.DriverRequestDTO;
import com.example.demo.dto.driver.DriverResponseDTO;
import com.example.demo.model.Document;
import com.example.demo.model.Driver;
import com.example.demo.model.Ride;
import com.example.demo.model.User;

import java.util.List;

public interface IDriverService {
    public List<User> getAll();
    public Driver findDriver(Integer driverId);
    public DriverResponseDTO insert(Driver driver);
    public DriverResponseDTO update(DriverRequestDTO driver, Integer id);
    public Driver delete(Integer driverId);
    public void deleteAll();
    List<Ride> getRides(Integer id);
    DriverDocumentsResponseDTO addDocument(Integer id, DriverDocumentsRequestDTO docs);
    List<Document> getDocuments(Integer id);

}
