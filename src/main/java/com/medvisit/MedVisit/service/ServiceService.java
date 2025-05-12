package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.ServiceDto;
import java.util.List;

public interface ServiceService {
    ServiceDto create(ServiceDto dto);
    ServiceDto getById(Long id);
    List<ServiceDto> getAll();
    ServiceDto update(Long id, ServiceDto dto);
    void delete(Long id);
}
