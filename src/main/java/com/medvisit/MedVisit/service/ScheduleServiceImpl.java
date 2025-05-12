package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.ScheduleDto;
import com.medvisit.MedVisit.entity.Employee;
import com.medvisit.MedVisit.entity.Schedule;
import com.medvisit.MedVisit.repository.EmployeeRepository;
import com.medvisit.MedVisit.repository.ScheduleRepository;
import com.medvisit.MedVisit.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
