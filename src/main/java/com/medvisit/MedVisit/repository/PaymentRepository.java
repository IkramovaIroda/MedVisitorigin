package com.medvisit.MedVisit.repository;

import com.medvisit.MedVisit.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByClientClientId(Long clientId);
    List<Payment> findByVisitVisitId(Long visitId);
}
