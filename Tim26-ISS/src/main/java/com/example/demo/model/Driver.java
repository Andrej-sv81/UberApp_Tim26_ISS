package com.example.demo.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue("DRIVER")
public class Driver extends User {
    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Document> documents;
    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Ride> rides;
    @OneToOne
    private Vehicle vehicle;

    public Driver(){
        super();
    }
    public Driver(String name, String surname, String profilePicture, String telephoneNumber, String email, String address, String password, boolean blocked, boolean active, List<Document> documents, List<Ride> rides, Vehicle vehicle) {
        super(name, surname, profilePicture, telephoneNumber, email, address, password, blocked, active);
        this.documents = documents;
        this.rides = rides;
        this.vehicle = vehicle;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public List<Ride> getRides() {
        return rides;
    }

    public void setRides(List<Ride> rides) {
        this.rides = rides;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void addDocument(Document document) {
        documents.add(document);
        document.setDriver(this);
    }

    public void removeDocument(Document document) {
        documents.remove(document);
        document.setDriver(null);
    }

    public void addRide(Ride ride) {
        rides.add(ride);
        ride.setDriver(this);
    }

    public void removeRide(Ride ride) {
        rides.remove(ride);
        ride.setDriver(null);
    }

    @Override
    public String toString() {
        return super.toString()+"\nDriver{" +
                "documents=" + documents +
                ", rides=" + rides +
                ", vehicle=" + vehicle +
                '}';
    }
}
