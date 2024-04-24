package com.sap.cityservice.controller;

import com.sap.cityservice.model.CityModel;
import com.sap.cityservice.service.CityService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:3000/")
@CrossOrigin(origins = "https://city-service-frontend.onrender.com/")
@RestController
@RequestMapping("/api/v1/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/list")
    public Iterable<CityModel> list() {
        return cityService.list();
    }

    @GetMapping("/listSorted")
    public List<CityModel> listSorted(@RequestParam String field, @RequestParam String direction) {
        return cityService.findAllSorted(field, direction);
    }

    @GetMapping("/filterByName")
    public List<CityModel> filterByName(@RequestParam String name) {
        return cityService.findByNameContaining(name);
    }

    @PostMapping("/add")
    public CityModel addCity(@RequestBody CityModel cityModel) {
        return cityService.addCity(cityModel);
    }
}