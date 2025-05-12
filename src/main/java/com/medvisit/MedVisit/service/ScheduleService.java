package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.ScheduleDto;

import java.util.List;

public interface ScheduleService {
    ScheduleDto create(ScheduleDto dto);
    ScheduleDto getById(Long id);
    List<ScheduleDto> getAll();
    ScheduleDto update(Long id, ScheduleDto dto);
    void delete(Long id);
}
