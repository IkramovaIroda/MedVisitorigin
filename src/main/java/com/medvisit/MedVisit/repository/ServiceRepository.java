package com.medvisit.MedVisit.repository;

import com.medvisit.MedVisit.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}
