package com.medvisit.MedVisit.controller;

import com.medvisit.MedVisit.dto.VisitDto;
import com.medvisit.MedVisit.entity.Employee;
import com.medvisit.MedVisit.service.AppointmentSlotService;
import com.medvisit.MedVisit.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/visits")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;
    private final AppointmentSlotService appointmentSlotService;

    @PostMapping
    public VisitDto create(@RequestBody VisitDto dto) {
        return visitService.createVisit(dto);
    }

    @PostMapping("/book-with-slot")
    public Map<String, Object> createWithSlot(@RequestBody Map<String, Object> request) {
        VisitDto dto = new VisitDto();
        dto.setClientId(Long.parseLong(request.get("clientId").toString()));
        dto.setEmployeeId(Long.parseLong(request.get("employeeId").toString()));
        dto.setServiceId(Long.parseLong(request.get("serviceId").toString()));
        dto.setClinicId(Long.parseLong(request.get("clinicId").toString()));
        dto.setStatus("SCHEDULED");
        dto.setNotes(request.get("notes") != null ? request.get("notes").toString() : null);
        
        Long slotId = Long.parseLong(request.get("slotId").toString());
        
        VisitDto result = visitService.createVisitWithSlot(dto, slotId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("visit", result);
        return response;
    }

    @GetMapping("/service/{serviceId}/available-doctors")
    public List<Employee> getAvailableDoctorsByService(
            @PathVariable Long serviceId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return visitService.findDoctorsByServiceAndDate(serviceId, date);
    }

    @GetMapping("/{id}")
    public VisitDto get(@PathVariable Long id) {
        return visitService.getVisitById(id);
    }

    @GetMapping
    public List<VisitDto> getAll() {
        return visitService.getAllVisits();
    }

    @PutMapping("/{id}")
    public VisitDto update(@PathVariable Long id, @RequestBody VisitDto dto) {
        return visitService.updateVisit(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        visitService.deleteVisit(id);
    }

    @GetMapping("/doctor/{doctorId}/available-slots")
    public List<com.medvisit.MedVisit.dto.AppointmentSlotDto> getAvailableSlotsForDoctor(
            @PathVariable Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        // Get available slots for specific doctor on specific date
        return appointmentSlotService.getAvailableSlotsByEmployeeAndDate(doctorId, date);
    }
}
