package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.PaymentDto;
import com.medvisit.MedVisit.entity.Client;
import com.medvisit.MedVisit.entity.Payment;
import com.medvisit.MedVisit.entity.Visit;
import com.medvisit.MedVisit.repository.ClientRepository;
import com.medvisit.MedVisit.repository.PaymentRepository;
import com.medvisit.MedVisit.repository.VisitRepository;
import com.medvisit.MedVisit.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ClientRepository clientRepository;
    private final VisitRepository visitRepository;

    @Override
    public PaymentDto create(PaymentDto dto) {
        Payment payment = new Payment();
        copyDtoToEntity(dto, payment);
        return toDto(paymentRepository.save(payment));
    }

    @Override
    public PaymentDto getById(Long id) {
        return paymentRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    @Override
    public List<PaymentDto> getAll() {
        return paymentRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDto> getByClient(Long clientId) {
        return paymentRepository.findByClientClientId(clientId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDto> getByVisit(Long visitId) {
        return paymentRepository.findByVisitVisitId(visitId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        paymentRepository.deleteById(id);
    }

    private void copyDtoToEntity(PaymentDto dto, Payment payment) {
        payment.setAmount(dto.getAmount());
        payment.setMethod(dto.getMethod());
        payment.setStatus(dto.getStatus());
        payment.setPaymentDate(dto.getPaymentDate());
        payment.setNote(dto.getNote());

        Visit visit = visitRepository.findById(dto.getVisitId())
                .orElseThrow(() -> new RuntimeException("Visit not found"));
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        payment.setVisit(visit);
        payment.setClient(client);
    }

    private PaymentDto toDto(Payment payment) {
        return new PaymentDto(
                payment.getPaymentId(),
                payment.getAmount(),
                payment.getMethod(),
                payment.getStatus(),
                payment.getPaymentDate(),
                payment.getVisit().getVisitId(),
                payment.getClient().getClientId(),
                payment.getNote()
        );
    }
}
