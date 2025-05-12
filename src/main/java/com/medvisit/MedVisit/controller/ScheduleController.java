package com.medvisit.MedVisit.controller;

import com.medvisit.MedVisit.dto.ScheduleDto;
import com.medvisit.MedVisit.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ScheduleDto create(@RequestBody ScheduleDto dto) {
        return scheduleService.create(dto);
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
