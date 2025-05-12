package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.ReportDto;
import com.medvisit.MedVisit.entity.Clinic;
import com.medvisit.MedVisit.entity.Report;
import com.medvisit.MedVisit.entity.User;
import com.medvisit.MedVisit.repository.ClinicRepository;
import com.medvisit.MedVisit.repository.ReportRepository;
import com.medvisit.MedVisit.repository.UserRepository;
import com.medvisit.MedVisit.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ClinicRepository clinicRepository;
    private final UserRepository userRepository;

    @Override
    public ReportDto create(ReportDto dto) {
        Report report = new Report();
        report.setClinic(clinicRepository.findById(dto.getClinicId())
                .orElseThrow(() -> new RuntimeException("Clinic not found")));
        report.setGeneratedBy(userRepository.findById(dto.getGeneratedByUserId())
                .orElseThrow(() -> new RuntimeException("User not found")));
        report.setTitle(dto.getTitle());
        report.setType(dto.getType());
        report.setGeneratedAt(LocalDateTime.now());
        report.setContent(dto.getContent());
        report.setArchived(dto.isArchived());
        return toDto(reportRepository.save(report));
    }

    @Override
    public ReportDto getById(Long id) {
        return reportRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Report not found"));
    }

    @Override
    public List<ReportDto> getAll() {
        return reportRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReportDto> getByClinic(Long clinicId) {
        return reportRepository.findByClinicClinicId(clinicId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReportDto> getArchived(boolean archived) {
        return reportRepository.findByIsArchived(archived).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        reportRepository.deleteById(id);
    }

    private ReportDto toDto(Report report) {
        return new ReportDto(
                report.getReportId(),
                report.getClinic().getClinicId(),
                report.getTitle(),
                report.getType(),
                report.getGeneratedAt(),
                report.getGeneratedBy().getUserId(),
                report.getContent(),
                report.isArchived()
        );
    }
}
