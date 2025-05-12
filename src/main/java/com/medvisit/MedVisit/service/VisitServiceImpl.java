package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.VisitDto;
import com.medvisit.MedVisit.entity.*;
import com.medvisit.MedVisit.repository.*;
import com.medvisit.MedVisit.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final ServiceRepository serviceRepository;
    private final ScheduleRepository scheduleRepository;
    private final ClinicRepository clinicRepository;

    @Override
    public VisitDto createVisit(VisitDto dto) {
        Visit visit = new Visit();
        copyDtoToEntity(dto, visit);
        return toDto(visitRepository.save(visit));
    }

    @Override
    public VisitDto getVisitById(Long id) {
        return visitRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Visit not found"));
    }

    @Override
    public List<VisitDto> getAllVisits() {
        return visitRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public VisitDto updateVisit(Long id, VisitDto dto) {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visit not found"));
        copyDtoToEntity(dto, visit);
        return toDto(visitRepository.save(visit));
    }

    @Override
    public void deleteVisit(Long id) {
        visitRepository.deleteById(id);
    }

    private void copyDtoToEntity(VisitDto dto, Visit visit) {
        visit.setVisitDateTime(dto.getVisitDateTime());
        visit.setStatus(dto.getStatus());
        visit.setNotes(dto.getNotes());

        visit.setClient(clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found")));
        visit.setEmployee(employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found")));
        visit.setService(serviceRepository.findById(dto.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found")));
        visit.setSchedule(scheduleRepository.findById(dto.getScheduleId())
                .orElseThrow(() -> new RuntimeException("Schedule not found")));
        visit.setClinic(clinicRepository.findById(dto.getClinicId())
                .orElseThrow(() -> new RuntimeException("Clinic not found")));
    }

    private VisitDto toDto(Visit visit) {
        VisitDto dto = new VisitDto();
        dto.setVisitId(visit.getVisitId());
        dto.setVisitDateTime(visit.getVisitDateTime());
        dto.setStatus(visit.getStatus());
        dto.setNotes(visit.getNotes());
        dto.setClientId(visit.getClient().getClientId());
        dto.setEmployeeId(visit.getEmployee().getEmployeeId());
        dto.setServiceId(visit.getService().getServiceId());
        dto.setScheduleId(visit.getSchedule().getScheduleId());
        dto.setClinicId(visit.getClinic().getClinicId());
        return dto;
    }
}
