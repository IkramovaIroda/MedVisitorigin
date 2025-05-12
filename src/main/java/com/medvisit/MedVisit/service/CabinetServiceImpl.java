package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.CabinetDto;
import com.medvisit.MedVisit.entity.Cabinet;
import com.medvisit.MedVisit.entity.Clinic;
import com.medvisit.MedVisit.repository.CabinetRepository;
import com.medvisit.MedVisit.repository.ClinicRepository;
import com.medvisit.MedVisit.service.CabinetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CabinetServiceImpl implements CabinetService {

    private final CabinetRepository cabinetRepository;
    private final ClinicRepository clinicRepository;

    @Override
    public CabinetDto create(CabinetDto dto) {
        Cabinet cabinet = new Cabinet();
        copyDtoToEntity(dto, cabinet);
        return toDto(cabinetRepository.save(cabinet));
    }

    @Override
    public CabinetDto getById(Long id) {
        return cabinetRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Cabinet not found"));
    }

    @Override
    public List<CabinetDto> getAll() {
        return cabinetRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CabinetDto> getByClinic(Long clinicId) {
        return cabinetRepository.findByClinicClinicId(clinicId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        cabinetRepository.deleteById(id);
    }

    private void copyDtoToEntity(CabinetDto dto, Cabinet cabinet) {
        cabinet.setName(dto.getName());
        cabinet.setFloor(dto.getFloor());
        cabinet.setDescription(dto.getDescription());
        cabinet.setActive(dto.isActive());

        Clinic clinic = clinicRepository.findById(dto.getClinicId())
                .orElseThrow(() -> new RuntimeException("Clinic not found"));
        cabinet.setClinic(clinic);
    }

    private CabinetDto toDto(Cabinet cabinet) {
        return new CabinetDto(
                cabinet.getCabinetId(),
                cabinet.getName(),
                cabinet.getFloor(),
                cabinet.getClinic().getClinicId(),
                cabinet.getDescription(),
                cabinet.isActive()
        );
    }
}
