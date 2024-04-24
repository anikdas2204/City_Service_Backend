package com.sap.cityservice.service;

import com.sap.cityservice.model.CityModel;
import com.sap.cityservice.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

    @Test
    void testFindAll() {
        List<CityModel> cities = Arrays.asList(new CityModel(), new CityModel());
        when(cityRepository.findAll()).thenReturn(cities);

        List<CityModel> result = cityService.findAll();

        assertEquals(cities, result);
    }

    @Test
    void testSave() {
        CityModel city = new CityModel();
        when(cityRepository.save(city)).thenReturn(city);

        CityModel result = cityService.save(city);

        assertEquals(city, result);
    }

    @Test
    void testSaveAll() {
        List<CityModel> cityModels = Arrays.asList(
                new CityModel("City1", 10000, 50),
                new CityModel("City2", 20000, 100)
        );
        cityService.save(cityModels);

        for (CityModel cityModel : cityModels) {
            assertEquals(cityModel.getPopulation() / cityModel.getArea(), cityModel.getDensity());
        }

        verify(cityRepository).saveAll(cityModels);
    }


    @Test
    void testList() {
        List<CityModel> cities = Arrays.asList(new CityModel(), new CityModel());
        when(cityRepository.findAll()).thenReturn(cities);

        Iterable<CityModel> result = cityService.list();

        assertEquals(cities, result);
    }

    @Test
    void testFindAllSorted() {
        List<CityModel> cities = Arrays.asList(new CityModel(), new CityModel());
        when(cityRepository.findAll(any(Sort.class))).thenReturn(cities);

        List<CityModel> result = cityService.findAllSorted("name", "asc");

        assertEquals(cities, result);
    }

    @Test
    void testFindByNameContaining() {
        List<CityModel> cities = Arrays.asList(new CityModel(), new CityModel());
        when(cityRepository.findByNameContaining(anyString())).thenReturn(cities);

        List<CityModel> result = cityService.findByNameContaining("test");

        assertEquals(cities, result);
    }

    @Test
    void testAddCity() {
        CityModel city = new CityModel();
        city.setName("New City");
        when(cityRepository.findByNameContaining("New City")).thenReturn(List.of());
        when(cityRepository.save(city)).thenReturn(city);

        CityModel result = cityService.addCity(city);

        assertEquals(city, result);
    }

    @Test
    void testAddCityWithExistingCity() {
        CityModel city = new CityModel();
        city.setName("Existing City");
        when(cityRepository.findByNameContaining("Existing City")).thenReturn(List.of(new CityModel()));

        try {
            cityService.addCity(city);
        } catch (RuntimeException e) {
            assertEquals("A city with the same name already exists.", e.getMessage());
        }
    }
}
