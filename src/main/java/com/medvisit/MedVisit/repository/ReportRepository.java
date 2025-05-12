package com.medvisit.MedVisit.repository;

import com.medvisit.MedVisit.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByClinicClinicId(Long clinicId);
    List<Report> findByIsArchived(boolean archived);
}
