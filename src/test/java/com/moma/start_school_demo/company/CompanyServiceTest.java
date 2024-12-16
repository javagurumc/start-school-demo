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
        var company = new Company(1L, "abc", "LVHABA121232434353", "Riga, Latvia", LocalDate.of(2010, 01, 02));
        when(repository.findAll()).thenReturn(List.of(company));

        //When
        var result = service.getAll();

        //Then
        assertThat(result)
                .containsExactly(company);
    }
}