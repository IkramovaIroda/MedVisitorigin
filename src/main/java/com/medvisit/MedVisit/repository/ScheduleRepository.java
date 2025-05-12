package com.medvisit.MedVisit.repository;

import com.medvisit.MedVisit.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
