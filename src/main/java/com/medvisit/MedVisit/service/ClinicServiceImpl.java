package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.ClinicDto;
import com.medvisit.MedVisit.entity.Clinic;
import com.medvisit.MedVisit.repository.ClinicRepository;
import com.medvisit.MedVisit.service.ClinicService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClinicServiceImpl implements ClinicService {
    private final ClinicRepository clinicRepository;

    @Override
    public ClinicDto createClinic(ClinicDto dto) {
        Clinic clinic = new Clinic();
        BeanUtils.copyProperties(dto, clinic);
        return toDto(clinicRepository.save(clinic));
    }

    @Override
    public ClinicDto getClinicById(Long id) {
        return clinicRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Clinic not found"));
    }

    @Override
    public List<ClinicDto> getAllClinics() {
        return clinicRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClinicDto updateClinic(Long id, ClinicDto dto) {
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clinic not found"));
        BeanUtils.copyProperties(dto, clinic, "clinicId");
        return toDto(clinicRepository.save(clinic));
    }

    @Override
    public void deleteClinic(Long id) {
        clinicRepository.deleteById(id);
    }

    private ClinicDto toDto(Clinic clinic) {
        ClinicDto dto = new ClinicDto();
        BeanUtils.copyProperties(clinic, dto);
        return dto;
    }
}
