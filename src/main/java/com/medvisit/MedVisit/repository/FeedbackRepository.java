package com.medvisit.MedVisit.repository;

import com.medvisit.MedVisit.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByClinicClinicId(Long clinicId);
    List<Feedback> findByClientClientId(Long clientId);
}
