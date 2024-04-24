package com.sap.cityservice.repository;

import com.sap.cityservice.model.CityModel;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CityRepository extends CrudRepository<CityModel, Long> {
    List<CityModel> findAll(Sort sort);
    List<CityModel> findByNameContaining(String name);
}