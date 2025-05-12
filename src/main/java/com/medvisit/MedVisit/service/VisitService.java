package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.VisitDto;

import java.util.List;

public interface VisitService {
    VisitDto createVisit(VisitDto dto);
    VisitDto getVisitById(Long id);
    List<VisitDto> getAllVisits();
    VisitDto updateVisit(Long id, VisitDto dto);
    void deleteVisit(Long id);
}
