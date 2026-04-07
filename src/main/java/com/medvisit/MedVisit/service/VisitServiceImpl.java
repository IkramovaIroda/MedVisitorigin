package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.VisitDto;
import com.medvisit.MedVisit.entity.*;
import com.medvisit.MedVisit.repository.*;
import com.medvisit.MedVisit.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    private final AppointmentSlotRepository appointmentSlotRepository;

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

    @Override
    @Transactional
    public VisitDto createVisitWithSlot(VisitDto dto, Long slotId) {
        // Book the appointment slot first
        AppointmentSlot slot = appointmentSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Appointment slot not found with ID: " + slotId));
        
        if (slot.getIsBooked()) {
            throw new RuntimeException("This time slot is already booked");
        }
        
        // Create the visit
        Visit visit = new Visit();
        
        // Set basic fields from DTO
        visit.setVisitDateTime(null); // Will be set from slot
        visit.setStatus(dto.getStatus());
        visit.setNotes(dto.getNotes());
        
        // Set relationships from DTO
        visit.setClient(clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + dto.getClientId())));
        visit.setEmployee(employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + dto.getEmployeeId())));
        visit.setService(serviceRepository.findById(dto.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found with ID: " + dto.getServiceId())));
        visit.setClinic(clinicRepository.findById(dto.getClinicId())
                .orElseThrow(() -> new RuntimeException("Clinic not found with ID: " + dto.getClinicId())));
        
        // Set visit date/time and schedule from the slot
        Schedule schedule = slot.getSchedule();
        visit.setSchedule(schedule);
        visit.setVisitDateTime(java.time.LocalDateTime.of(schedule.getDate(), slot.getStartTime()));
        
        Visit savedVisit = visitRepository.save(visit);
        
        // Mark slot as booked and link to visit
        slot.setIsBooked(true);
        slot.setVisit(savedVisit);
        appointmentSlotRepository.save(slot);
        
        return toDto(savedVisit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findDoctorsByServiceAndDate(Long serviceId, LocalDate date) {
        com.medvisit.MedVisit.entity.Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        
        // Find doctors by service name (which should match specialization)
        String serviceName = service.getName();
        return employeeRepository.findEmployeesBySpecialtyAndDate(serviceName, date);
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
