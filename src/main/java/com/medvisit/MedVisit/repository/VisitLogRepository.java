package com.medvisit.MedVisit.repository;

import com.medvisit.MedVisit.entity.VisitLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitLogRepository extends JpaRepository<VisitLog, Long> {
}
