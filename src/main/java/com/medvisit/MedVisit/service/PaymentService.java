package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.PaymentDto;

import java.util.List;

public interface PaymentService {
    PaymentDto create(PaymentDto dto);
    PaymentDto getById(Long id);
    List<PaymentDto> getAll();
    List<PaymentDto> getByClient(Long clientId);
    List<PaymentDto> getByVisit(Long visitId);
    void delete(Long id);
}
