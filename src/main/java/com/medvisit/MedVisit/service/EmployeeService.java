package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto create(EmployeeDto dto);
    EmployeeDto getById(Long id);
    List<EmployeeDto> getAll();
    EmployeeDto update(Long id, EmployeeDto dto);
    void delete(Long id);
}
