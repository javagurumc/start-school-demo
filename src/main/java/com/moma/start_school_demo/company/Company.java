package com.moma.start_school_demo.company;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "company")
public class Company {

    @Id
    private Long id;
    private String name;
    private String bankAccount;
    private String address;
    private LocalDate firstRegistration;

    public Company(Long id, String name, String bankAccount, String address, LocalDate firstRegistration) {
        this.id = id;
        this.name = name;
        this.bankAccount = bankAccount;
        this.address = address;
        this.firstRegistration = firstRegistration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getFirstRegistration() {
        return firstRegistration;
    }

    public void setFirstRegistration(LocalDate firstRegistration) {
        this.firstRegistration = firstRegistration;
    }
}
