package com.medvisit.MedVisit.repository;

import com.medvisit.MedVisit.entity.Cabinet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CabinetRepository extends JpaRepository<Cabinet, Long> {
    List<Cabinet> findByClinicClinicId(Long clinicId);
}
