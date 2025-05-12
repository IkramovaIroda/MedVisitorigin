package com.medvisit.MedVisit.controller;

import com.medvisit.MedVisit.dto.PaymentDto;
import com.medvisit.MedVisit.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public PaymentDto create(@RequestBody PaymentDto dto) {
        return paymentService.create(dto);
    }

    @GetMapping("/{id}")
    public PaymentDto getById(@PathVariable Long id) {
        return paymentService.getById(id);
    }

    @GetMapping
    public List<PaymentDto> getAll() {
        return paymentService.getAll();
    }

    @GetMapping("/by-client/{clientId}")
    public List<PaymentDto> getByClient(@PathVariable Long clientId) {
        return paymentService.getByClient(clientId);
    }

    @GetMapping("/by-visit/{visitId}")
    public List<PaymentDto> getByVisit(@PathVariable Long visitId) {
        return paymentService.getByVisit(visitId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        paymentService.delete(id);
    }
}
