package com.medvisit.MedVisit.repository;

import com.medvisit.MedVisit.entity.AppointmentSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentSlotRepository extends JpaRepository<AppointmentSlot, Long> {

    @Query("select a from AppointmentSlot a where a.schedule.scheduleId = :scheduleId")
    List<AppointmentSlot> findByScheduleId(@Param("scheduleId") Long scheduleId);
    List<AppointmentSlot> findByIsBookedFalse();
    
    @Query("SELECT slot FROM AppointmentSlot slot " +
           "JOIN slot.schedule sch " +
           "JOIN sch.employee emp " +
           "WHERE emp.employeeId = :employeeId " +
           "AND sch.date = :date " +
           "AND slot.isBooked = false")
    List<AppointmentSlot> findAvailableSlotsByEmployeeAndDate(
        @Param("employeeId") Long employeeId,
        @Param("date") LocalDate date
    );
    
    @Query("SELECT slot FROM AppointmentSlot slot " +
           "JOIN slot.schedule sch " +
           "JOIN sch.employee emp " +
           "WHERE LOWER(emp.specialization) LIKE LOWER(CONCAT('%', :specialty, '%')) " +
           "AND sch.date = :date " +
           "AND slot.isBooked = false")
    List<AppointmentSlot> findAvailableSlotsBySpecialtyAndDate(
        @Param("specialty") String specialty,
        @Param("date") LocalDate date
    );
}
