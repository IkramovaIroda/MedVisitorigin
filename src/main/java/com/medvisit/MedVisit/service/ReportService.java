package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.ReportDto;

import java.util.List;

public interface ReportService {
    ReportDto create(ReportDto dto);
    ReportDto getById(Long id);
    List<ReportDto> getAll();
    List<ReportDto> getByClinic(Long clinicId);
    List<ReportDto> getArchived(boolean archived);
    void delete(Long id);
}
