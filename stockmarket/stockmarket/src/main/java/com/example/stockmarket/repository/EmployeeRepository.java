package com.example.stockmarket.repository;

import com.example.stockmarket.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {


    Optional<Employee> findEmployeeByEmail(String email);
    Employee findEmployeeByFirstname(String firstname);
}
