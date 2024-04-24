package com.sap.cityservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class CityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double area;
    private int population;
    private double density;

    public CityModel() {
    }

    public CityModel(String name, double area, int population) {
        this.name = name;
        this.area = area;
        this.population = population;
        this.density = density;
    }
}
