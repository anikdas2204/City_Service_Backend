package com.sap.cityservice.controller;

import com.sap.cityservice.model.CityModel;
import com.sap.cityservice.service.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(MockitoExtension.class)
class CityControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CityService cityService;

    @InjectMocks
    private CityController cityController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cityController).build();
    }

    @Test
    void testList() throws Exception {
        List<CityModel> cities = Arrays.asList(
                new CityModel("City1", 100, 10),
                new CityModel("City2", 50, 5)
        );
        when(cityService.list()).thenReturn(cities);

        mockMvc.perform(get("/api/v1/cities/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testListSorted() throws Exception {
        List<CityModel> cities = Arrays.asList(
                new CityModel("City1", 100, 10),
                new CityModel("City2", 50, 5)
        );
        when(cityService.findAllSorted(anyString(), anyString())).thenReturn(cities);

        mockMvc.perform(get("/api/v1/cities/listSorted")
                        .param("field", "name")
                        .param("direction", "asc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testFilterByName() throws Exception {
        List<CityModel> cities = Arrays.asList(
                new CityModel("City1", 100, 10),
                new CityModel("City2", 100, 5)
        );
        when(cityService.findByNameContaining(anyString())).thenReturn(cities);

        mockMvc.perform(get("/api/v1/cities/filterByName")
                        .param("name", "City"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testAddCity() throws Exception {
        CityModel city = new CityModel("New City", 100, 10);
        when(cityService.addCity(any(CityModel.class))).thenReturn(city);

        mockMvc.perform(post("/api/v1/cities/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1, \"name\": \"New City\" }"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
