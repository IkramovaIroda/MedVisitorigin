package com.medvisit.MedVisit.repository;

import com.medvisit.MedVisit.entity.MedicalQuestionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MedicalQuestionnaireRepository extends JpaRepository<MedicalQuestionnaire, Long> {
    Optional<MedicalQuestionnaire> findByClientClientId(Long clientId);
}
