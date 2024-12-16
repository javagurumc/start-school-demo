package com.moma.start_school_demo.company;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CompanyController.class)
class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService service;

    @Test
    void givenDataExists_WhenGetAll_ThenReturnAllData() throws Exception {
        //Given
        when(service.getAll()).thenReturn(List.of(
                new Company(1L, "abc", "LVHABA121232434353", "Riga, Latvia", LocalDate.of(2010, 01, 02)))
        );

        //When
        this.mockMvc.perform(get("/api/v1/companies"))
                //Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [{  
                                "id": 1,
                                "name": "abc",
                                "bankAccount": "LVHABA121232434353",
                                "address": "Riga, Latvia",
                                "firstRegistration": "2010-01-02"
                            }]
                        """));
    }
}