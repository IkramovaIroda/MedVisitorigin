package com.medvisit.MedVisit.repository;

import com.medvisit.MedVisit.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    boolean existsByEmployeeEmployeeIdAndDate(Long employeeId, LocalDate date);
}
