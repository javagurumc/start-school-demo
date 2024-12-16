package com.moma.start_school_demo.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CompanyServiceTest {

    private CompanyService service;
    private CompanyRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(CompanyRepository.class);
        service = new CompanyService(repository);
    }

    @Test
    void givenDataExists_WhenGetAll_ReturnAllData() {
        //Given
        var company = new Company();
        company.setId(1L);
        company.setName("abc");
        company.setBankAccount("LVHABA121232434353");
        company.setAddress("Riga, Latvia");
        company.setFirstRegistration(LocalDate.of(2010, 01, 02));
        when(repository.findAll()).thenReturn(List.of(company));

        //When
        var result = service.getAll();

        //Then
        assertThat(result)
                .containsExactly(company);
    }

    @Test
    void givenAllDataSupplied_WhenCreate_ThenReturnCreatedCompany() {
        //Given
        var newCompany = new Company();
        newCompany.setName("abc");
        newCompany.setBankAccount("LVHABA121232434353");
        newCompany.setAddress("Riga, Latvia");
        newCompany.setFirstRegistration(LocalDate.of(2010, 01, 02));

        var createdCompany = new Company();
        createdCompany.setId(1L);
        createdCompany.setName("abc");
        createdCompany.setBankAccount("LVHABA121232434353");
        createdCompany.setAddress("Riga, Latvia");
        createdCompany.setFirstRegistration(LocalDate.of(2010, 01, 02));

        when(repository.save(newCompany)).thenReturn(createdCompany);

        //When
        var result = service.create(newCompany);

        //Then
        assertThat(result)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "abc")
                .hasFieldOrPropertyWithValue("bankAccount", "LVHABA121232434353")
                .hasFieldOrPropertyWithValue("address", "Riga, Latvia")
                .hasFieldOrPropertyWithValue("firstRegistration", LocalDate.of(2010, 01, 02));
    }
}