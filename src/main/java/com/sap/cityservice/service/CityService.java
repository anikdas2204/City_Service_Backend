package com.sap.cityservice.service;

import com.sap.cityservice.model.CityModel;
import com.sap.cityservice.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<CityModel> findAll() {
        return (List<CityModel>) cityRepository.findAll();
    }

    public CityModel save(CityModel cityModel) {
        return cityRepository.save(cityModel);
    }
    public void save(List<CityModel> cityModels) {
        cityModels.forEach(cityModel -> cityModel.setDensity(cityModel.getPopulation() / cityModel.getArea()));
        cityRepository.saveAll(cityModels);
    }

    public Iterable<CityModel> list() {
        return cityRepository.findAll();
    }
    public List<CityModel> findAllSorted(String field, String direction) {
        return cityRepository.findAll(Sort.by(Sort.Direction.fromString(direction), field));
    }

    public List<CityModel> findByNameContaining(String name) {
        return cityRepository.findByNameContaining(name);
    }

    public CityModel addCity(CityModel cityModel) {
        List<CityModel> existingCities = cityRepository.findByNameContaining(cityModel.getName());
        if (!existingCities.isEmpty()) {
            throw new RuntimeException("A city with the same name already exists.");
        }
        cityModel.setDensity(cityModel.getPopulation() / cityModel.getArea());
        return cityRepository.save(cityModel);
    }

    public long count() {
        return cityRepository.count();
    }
}