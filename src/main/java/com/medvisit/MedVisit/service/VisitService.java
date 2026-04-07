package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.VisitDto;
import com.medvisit.MedVisit.entity.Employee;

import java.time.LocalDate;
import java.util.List;

public interface VisitService {
    VisitDto createVisit(VisitDto dto);
    VisitDto getVisitById(Long id);
    List<VisitDto> getAllVisits();
    VisitDto updateVisit(Long id, VisitDto dto);
    void deleteVisit(Long id);
    
    VisitDto createVisitWithSlot(VisitDto dto, Long slotId);
    List<Employee> findDoctorsByServiceAndDate(Long serviceId, LocalDate date);
}
