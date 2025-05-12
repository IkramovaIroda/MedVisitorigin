package com.medvisit.MedVisit.repository;

import com.medvisit.MedVisit.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
}
