package com.medvisit.MedVisit.repository;

import com.medvisit.MedVisit.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    List<Employee> findBySpecializationContainingIgnoreCase(String specialization);
    
    @Query("SELECT DISTINCT emp FROM Employee emp " +
           "JOIN emp.services svc " +
           "JOIN Schedule sch ON sch.employee = emp " +
           "WHERE LOWER(emp.specialization) LIKE LOWER(CONCAT('%', :specialty, '%')) " +
           "AND sch.date = :date")
    List<Employee> findEmployeesBySpecialtyAndDate(
        @Param("specialty") String specialty,
        @Param("date") LocalDate date
    );
}
