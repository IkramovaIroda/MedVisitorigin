package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.VisitLogDto;

import java.util.List;

public interface VisitLogService {
    VisitLogDto create(VisitLogDto dto);
    VisitLogDto getById(Long id);
    List<VisitLogDto> getAll();
    void delete(Long id);
}
