package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.ServiceDto;
import com.medvisit.MedVisit.entity.Clinic;
import com.medvisit.MedVisit.entity.Service;
import com.medvisit.MedVisit.repository.ClinicRepository;
import com.medvisit.MedVisit.repository.ServiceRepository;
import com.medvisit.MedVisit.service.ServiceService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final ClinicRepository clinicRepository;

    @Override
    public ServiceDto create(ServiceDto dto) {
        Service service = new Service();
        copyDtoToEntity(dto, service);
        return toDto(serviceRepository.save(service));
    }

    @Override
    public ServiceDto getById(Long id) {
        return serviceRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Service not found"));
    }

    @Override
    public List<ServiceDto> getAll() {
        return serviceRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceDto update(Long id, ServiceDto dto) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        copyDtoToEntity(dto, service);
        return toDto(serviceRepository.save(service));
    }

    @Override
    public void delete(Long id) {
        serviceRepository.deleteById(id);
    }

    private void copyDtoToEntity(ServiceDto dto, Service service) {
        service.setName(dto.getName());
        service.setDescription(dto.getDescription());
        service.setPrice(dto.getPrice());
        service.setDuration(dto.getDuration());
        Clinic clinic = clinicRepository.findById(dto.getClinicId())
                .orElseThrow(() -> new RuntimeException("Clinic not found"));
        service.setClinic(clinic);
    }

    private ServiceDto toDto(Service service) {
        return new ServiceDto(
                service.getServiceId(),
                service.getName(),
                service.getDescription(),
                service.getPrice(),
                service.getDuration(),
                service.getClinic().getClinicId()
        );
    }
}
