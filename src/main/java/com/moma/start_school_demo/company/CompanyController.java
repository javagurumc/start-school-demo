package com.moma.start_school_demo.company;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {

    private final CompanyService service;

    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @GetMapping
    public List<Company> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Company create(@RequestBody Company company) {
        return service.create(company);
    }

}
