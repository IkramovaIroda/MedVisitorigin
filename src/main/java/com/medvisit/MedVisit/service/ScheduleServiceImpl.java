package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.ScheduleDto;
import com.medvisit.MedVisit.entity.Employee;
import com.medvisit.MedVisit.entity.Schedule;
import com.medvisit.MedVisit.repository.EmployeeRepository;
import com.medvisit.MedVisit.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public ScheduleDto create(ScheduleDto dto) {
        Schedule schedule = new Schedule();
        copyDtoToEntity(dto, schedule);
        return toDto(scheduleRepository.save(schedule));
    }

    @Override
    @Transactional
    public List<ScheduleDto> createMonthlySchedule(Long employeeId, String startDate, String endDate,
                                                    String startTime, String endTime, List<String> daysOfWeek) {
        // Validate employee exists
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));
        
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        
        // Parse days of week if provided (e.g., ["MONDAY", "WEDNESDAY", "FRIDAY"])
        List<DayOfWeek> targetDays = null;
        if (daysOfWeek != null && !daysOfWeek.isEmpty()) {
            targetDays = daysOfWeek.stream()
                    .map(DayOfWeek::valueOf)
                    .collect(Collectors.toList());
        }
        
        List<ScheduleDto> createdSchedules = new ArrayList<>();
        LocalDate currentDate = start;
        
        while (!currentDate.isAfter(end)) {
            // If specific days are provided, check if current day matches
            boolean shouldCreate = (targetDays == null || targetDays.contains(currentDate.getDayOfWeek()));
            
            if (shouldCreate) {
                // Check if schedule already exists for this date and employee
                boolean exists = scheduleRepository.existsByEmployeeEmployeeIdAndDate(employeeId, currentDate);
                
                if (!exists) {
                    Schedule schedule = new Schedule();
                    schedule.setEmployee(employee);
                    schedule.setDate(currentDate);
                    schedule.setStartTime(LocalTime.parse(startTime));
                    schedule.setEndTime(LocalTime.parse(endTime));
                    
                    Schedule saved = scheduleRepository.save(schedule);
                    createdSchedules.add(toDto(saved));
                }
            }
            
            currentDate = currentDate.plusDays(1);
        }
        
        System.out.println("Created " + createdSchedules.size() + " schedules for employee " + employeeId);
        return createdSchedules;
    }

    @Override
    public ScheduleDto getById(Long id) {
        return scheduleRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
    }

    @Override
    public List<ScheduleDto> getAll() {
        return scheduleRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleDto update(Long id, ScheduleDto dto) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        copyDtoToEntity(dto, schedule);
        return toDto(scheduleRepository.save(schedule));
    }

    @Override
    public void delete(Long id) {
        scheduleRepository.deleteById(id);
    }

    private void copyDtoToEntity(ScheduleDto dto, Schedule schedule) {
        schedule.setDate(dto.getDate());
        schedule.setStartTime(dto.getStartTime());
        schedule.setEndTime(dto.getEndTime());
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        schedule.setEmployee(employee);
    }

    private ScheduleDto toDto(Schedule schedule) {
        return new ScheduleDto(
                schedule.getScheduleId(),
                schedule.getDate(),
                schedule.getStartTime(),
                schedule.getEndTime(),
                schedule.getEmployee().getEmployeeId()
        );
    }
}
