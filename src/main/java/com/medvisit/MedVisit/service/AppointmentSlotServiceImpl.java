package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.AppointmentSlotDto;
import com.medvisit.MedVisit.entity.AppointmentSlot;
import com.medvisit.MedVisit.entity.Schedule;
import com.medvisit.MedVisit.entity.Visit;
import com.medvisit.MedVisit.repository.AppointmentSlotRepository;
import com.medvisit.MedVisit.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentSlotServiceImpl implements AppointmentSlotService {

    private final AppointmentSlotRepository appointmentSlotRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    public AppointmentSlotDto create(AppointmentSlotDto dto) {
        AppointmentSlot slot = new AppointmentSlot();
        copyDtoToEntity(dto, slot);
        return toDto(appointmentSlotRepository.save(slot));
    }

    @Override
    public AppointmentSlotDto getById(Long id) {
        return appointmentSlotRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Appointment slot not found"));
    }

    @Override
    public List<AppointmentSlotDto> getAll() {
        return appointmentSlotRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentSlotDto> getByScheduleId(Long scheduleId) {
        return appointmentSlotRepository.findByScheduleId(scheduleId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentSlotDto> getAvailableSlots() {
        return appointmentSlotRepository.findByIsBookedFalse().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentSlotDto> getAvailableSlotsByEmployeeAndDate(Long employeeId, LocalDate date) {
        return appointmentSlotRepository.findAvailableSlotsByEmployeeAndDate(employeeId, date).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentSlotDto> getAvailableSlotsBySpecialtyAndDate(String specialty, LocalDate date) {
        return appointmentSlotRepository.findAvailableSlotsBySpecialtyAndDate(specialty, date).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AppointmentSlotDto bookSlot(Long slotId, Long visitId) {
        AppointmentSlot slot = appointmentSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Appointment slot not found"));
        
        if (slot.getIsBooked()) {
            throw new RuntimeException("This time slot is already booked");
        }
        
        slot.setIsBooked(true);
        
        if (visitId != null) {
            Visit visit = new Visit();
            visit.setVisitId(visitId);
            slot.setVisit(visit);
        }
        
        return toDto(appointmentSlotRepository.save(slot));
    }

    @Override
    @Transactional
    public AppointmentSlotDto releaseSlot(Long slotId) {
        AppointmentSlot slot = appointmentSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Appointment slot not found"));
        
        slot.setIsBooked(false);
        slot.setVisit(null);
        
        return toDto(appointmentSlotRepository.save(slot));
    }

    @Override
    @Transactional
    public void generateTimeSlotsForSchedule(Long scheduleId, Integer serviceDuration) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        
        // Default duration to 30 minutes if not specified
        int durationMinutes = (serviceDuration != null && serviceDuration > 0) ? serviceDuration : 30;
        
        LocalTime currentTime = schedule.getStartTime();
        LocalTime endTime = schedule.getEndTime();
        
        // Generate slots with small buffer between appointments (5 minutes)
        int bufferMinutes = 5;
        
        while (currentTime.plusMinutes(durationMinutes).isBefore(endTime) || 
               currentTime.plusMinutes(durationMinutes).equals(endTime)) {
            
            LocalTime slotEndTime = currentTime.plusMinutes(durationMinutes);
            
            // Check if slot already exists
            LocalTime finalCurrentTime = currentTime;
            boolean slotExists = schedule.getAppointmentSlots().stream()
                    .anyMatch(slot -> slot.getStartTime().equals(finalCurrentTime) &&
                             slot.getEndTime().equals(slotEndTime));
            
            if (!slotExists) {
                AppointmentSlot slot = new AppointmentSlot();
                slot.setSchedule(schedule);
                slot.setStartTime(currentTime);
                slot.setEndTime(slotEndTime);
                slot.setIsBooked(false);
                appointmentSlotRepository.save(slot);
            }
            
            // Move to next slot start time
            currentTime = slotEndTime.plusMinutes(bufferMinutes);
        }
    }

    private void copyDtoToEntity(AppointmentSlotDto dto, AppointmentSlot slot) {
        slot.setStartTime(dto.getStartTime());
        slot.setEndTime(dto.getEndTime());
        slot.setIsBooked(dto.getIsBooked());
        
        if (dto.getScheduleId() != null) {
            Schedule schedule = scheduleRepository.findById(dto.getScheduleId())
                    .orElseThrow(() -> new RuntimeException("Schedule not found"));
            slot.setSchedule(schedule);
        }
        
        if (dto.getVisitId() != null) {
            Visit visit = new Visit();
            visit.setVisitId(dto.getVisitId());
            slot.setVisit(visit);
        }
    }

    private AppointmentSlotDto toDto(AppointmentSlot slot) {
        return new AppointmentSlotDto(
                slot.getSlotId(),
                slot.getSchedule().getScheduleId(),
                slot.getStartTime(),
                slot.getEndTime(),
                slot.getIsBooked(),
                slot.getVisit() != null ? slot.getVisit().getVisitId() : null
        );
    }
}
