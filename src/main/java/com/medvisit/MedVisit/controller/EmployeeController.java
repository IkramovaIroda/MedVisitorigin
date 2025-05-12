package com.medvisit.MedVisit.controller;

import com.medvisit.MedVisit.dto.EmployeeDto;
import com.medvisit.MedVisit.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public EmployeeDto create(@RequestBody EmployeeDto dto) {
        return employeeService.create(dto);
    }

    @GetMapping("/{id}")
    public EmployeeDto get(@PathVariable Long id) {
        return employeeService.getById(id);
    }

    @GetMapping
    public List<EmployeeDto> getAll() {
        return employeeService.getAll();
    }

    @PutMapping("/{id}")
    public EmployeeDto update(@PathVariable Long id, @RequestBody EmployeeDto dto) {
        return employeeService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        employeeService.delete(id);
    }
}
