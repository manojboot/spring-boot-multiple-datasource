package com.multipledatasource.example.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multipledatasource.example.company.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

}
