package com.multipledatasource.example.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multipledatasource.example.employee.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

}
