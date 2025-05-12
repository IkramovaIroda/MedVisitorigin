package com.medvisit.MedVisit.repository;

import com.medvisit.MedVisit.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {
}
