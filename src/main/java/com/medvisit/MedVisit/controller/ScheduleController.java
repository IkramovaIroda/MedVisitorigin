package com.medvisit.MedVisit.controller;

import com.medvisit.MedVisit.dto.ScheduleDto;
import com.medvisit.MedVisit.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ScheduleDto create(@RequestBody ScheduleDto dto) {
        return scheduleService.create(dto);
    }

    @PostMapping("/bulk-monthly")
    public List<ScheduleDto> createMonthlySchedule(@RequestBody Map<String, Object> request) {
        Long employeeId = Long.valueOf(request.get("employeeId").toString());
        String startDate = request.get("startDate").toString();
        String endDate = request.get("endDate").toString();
        String startTime = request.get("startTime").toString();
        String endTime = request.get("endTime").toString();
        
        // Parse days of week if provided
        @SuppressWarnings("unchecked")
        List<String> daysOfWeek = (List<String>) request.getOrDefault("daysOfWeek", null);
        
        return scheduleService.createMonthlySchedule(
            employeeId, startDate, endDate, startTime, endTime, daysOfWeek
        );
    }

    @GetMapping("/{id}")
    public ScheduleDto get(@PathVariable Long id) {
        return scheduleService.getById(id);
    }

    @GetMapping
    public List<ScheduleDto> getAll() {
        return scheduleService.getAll();
    }

    @PutMapping("/{id}")
    public ScheduleDto update(@PathVariable Long id, @RequestBody ScheduleDto dto) {
        return scheduleService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        scheduleService.delete(id);
    }
}
