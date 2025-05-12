package com.medvisit.MedVisit.repository;

import com.medvisit.MedVisit.entity.DocumentTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentTemplateRepository extends JpaRepository<DocumentTemplate, Long> {
    List<DocumentTemplate> findByClinicClinicId(Long clinicId);
    List<DocumentTemplate> findByIsActiveTrue();
}
