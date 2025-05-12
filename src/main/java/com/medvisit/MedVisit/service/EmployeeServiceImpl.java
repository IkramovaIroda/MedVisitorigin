package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.EmployeeDto;
import com.medvisit.MedVisit.entity.Clinic;
import com.medvisit.MedVisit.entity.Employee;
import com.medvisit.MedVisit.repository.ClinicRepository;
import com.medvisit.MedVisit.repository.EmployeeRepository;
import com.medvisit.MedVisit.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ClinicRepository clinicRepository;

    @Override
    public EmployeeDto create(EmployeeDto dto) {
        Employee employee = new Employee();
        copyDtoToEntity(dto, employee);
        return toDto(employeeRepository.save(employee));
    }

    @Override
    public EmployeeDto getById(Long id) {
        return employeeRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @Override
    public List<EmployeeDto> getAll() {
        return employeeRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto update(Long id, EmployeeDto dto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        copyDtoToEntity(dto, employee);
        return toDto(employeeRepository.save(employee));
    }

    @Override
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    private void copyDtoToEntity(EmployeeDto dto, Employee employee) {
        employee.setFullName(dto.getFullName());
        employee.setPosition(dto.getPosition());
        employee.setSpecialization(dto.getSpecialization());
        employee.setPhone(dto.getPhone());
        employee.setEmail(dto.getEmail());
        Clinic clinic = clinicRepository.findById(dto.getClinicId())
                .orElseThrow(() -> new RuntimeException("Clinic not found"));
        employee.setClinic(clinic);
    }

    private EmployeeDto toDto(Employee employee) {
        return new EmployeeDto(
                employee.getEmployeeId(),
                employee.getFullName(),
                employee.getPosition(),
                employee.getSpecialization(),
                employee.getPhone(),
                employee.getEmail(),
                employee.getClinic().getClinicId()
        );
    }
}
