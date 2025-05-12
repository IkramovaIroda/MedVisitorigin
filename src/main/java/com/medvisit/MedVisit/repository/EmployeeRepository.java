package com.medvisit.MedVisit.repository;

import com.medvisit.MedVisit.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
