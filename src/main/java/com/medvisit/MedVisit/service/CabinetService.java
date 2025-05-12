package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.CabinetDto;

import java.util.List;

public interface CabinetService {
    CabinetDto create(CabinetDto dto);
    CabinetDto getById(Long id);
    List<CabinetDto> getAll();
    List<CabinetDto> getByClinic(Long clinicId);
    void delete(Long id);
}
