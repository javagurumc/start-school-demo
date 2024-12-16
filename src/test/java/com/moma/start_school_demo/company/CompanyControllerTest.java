package com.moma.start_school_demo.company;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        var company = new Company();
        company.setId(1L);
        company.setName("abc");
        company.setBankAccount("LVHABA121232434353");
        company.setAddress("Riga, Latvia");
        company.setFirstRegistration(LocalDate.of(2010, 01, 02));

        when(service.getAll()).thenReturn(List.of(company));

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

    @Test
    void givenAllDataSupplied_WhenCreate_ThenReturnNewObject() throws Exception {
        //Given
        var savedCompany = new Company();
        savedCompany.setId(1L);
        savedCompany.setName("abc");
        savedCompany.setBankAccount("LVHABA121232434353");
        savedCompany.setAddress("Riga, Latvia");
        savedCompany.setFirstRegistration(LocalDate.of(2010, 01, 02));

        when(service.create(any())).thenReturn(savedCompany);

        //When
        this.mockMvc.perform(post("/api/v1/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {  
                                    "name": "abc",
                                    "bankAccount": "LVHABA121232434353",
                                    "address": "Riga, Latvia",
                                    "firstRegistration": "2010-01-02"
                                }                                
                                """))
                //Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                                "id": 1,
                                "name": "abc",
                                "bankAccount": "LVHABA121232434353",
                                "address": "Riga, Latvia",
                                "firstRegistration": "2010-01-02"
                            }
                        """));

        ArgumentCaptor<Company> argumentCaptor = ArgumentCaptor.forClass(Company.class);
        verify(service).create(argumentCaptor.capture());

        var value = argumentCaptor.getValue();
        assertThat(value)
                .hasFieldOrPropertyWithValue("name", "abc")
                .hasFieldOrPropertyWithValue("bankAccount", "LVHABA121232434353")
                .hasFieldOrPropertyWithValue("address", "Riga, Latvia")
                .hasFieldOrPropertyWithValue("firstRegistration", LocalDate.of(2010, 01, 02));
    }
}