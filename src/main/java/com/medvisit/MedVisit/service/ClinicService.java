package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.ClinicDto;
import java.util.List;

public interface ClinicService {
    ClinicDto createClinic(ClinicDto dto);
    ClinicDto getClinicById(Long id);
    List<ClinicDto> getAllClinics();
    ClinicDto updateClinic(Long id, ClinicDto dto);
    void deleteClinic(Long id);
}
