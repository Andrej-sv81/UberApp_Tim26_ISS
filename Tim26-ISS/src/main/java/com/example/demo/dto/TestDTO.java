package com.example.demo.dto;

import com.example.demo.dto.driver.DriverVehicleResponseDTO;


public class TestDTO {

    private Integer id;

    private TestVehicleDTO vehicle;

    private String routeJSON;

    public TestDTO(Integer id, TestVehicleDTO vehicle, String routeJSON) {
        this.id = id;
        this.vehicle = vehicle;
        this.routeJSON = routeJSON;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TestVehicleDTO getVehicle() {
        return vehicle;
    }

    public void setVehicle(TestVehicleDTO vehicle) {
        this.vehicle = vehicle;
    }

    public String getRouteJSON() {
        return routeJSON;
    }

    public void setRouteJSON(String routeJSON) {
        this.routeJSON = routeJSON;
    }
}
