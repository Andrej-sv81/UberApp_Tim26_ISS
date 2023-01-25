package com.example.demo.model;

import javax.persistence.*;

@Entity
public class VehicleType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private VehicleTypeEnum name;

    @Column(name="price_per_km", nullable = false)
    private int price;

    public VehicleType(){
        super();
    }

    public VehicleType(VehicleTypeEnum name, int price) {
        this.name = name;
        this.price = price;
    }

    public VehicleType(String type){
        this.name = VehicleTypeEnum.valueOf(type);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public VehicleTypeEnum getName() {
        return name;
    }

    public void setName(VehicleTypeEnum name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "VehicleType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
