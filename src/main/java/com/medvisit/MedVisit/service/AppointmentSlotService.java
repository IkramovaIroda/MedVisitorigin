package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.AppointmentSlotDto;
import com.medvisit.MedVisit.entity.AppointmentSlot;
import com.medvisit.MedVisit.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentSlotService {
    
    AppointmentSlotDto create(AppointmentSlotDto dto);
    
    AppointmentSlotDto getById(Long id);
    
    List<AppointmentSlotDto> getAll();
    
    List<AppointmentSlotDto> getByScheduleId(Long scheduleId);
    
    List<AppointmentSlotDto> getAvailableSlots();
    
    List<AppointmentSlotDto> getAvailableSlotsByEmployeeAndDate(Long employeeId, LocalDate date);
    
    List<AppointmentSlotDto> getAvailableSlotsBySpecialtyAndDate(String specialty, LocalDate date);
    
    AppointmentSlotDto bookSlot(Long slotId, Long visitId);
    
    AppointmentSlotDto releaseSlot(Long slotId);
    
    void generateTimeSlotsForSchedule(Long scheduleId, Integer serviceDuration);
}
