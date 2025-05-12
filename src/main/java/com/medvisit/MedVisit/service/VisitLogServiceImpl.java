package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.VisitLogDto;
import com.medvisit.MedVisit.entity.User;
import com.medvisit.MedVisit.entity.Visit;
import com.medvisit.MedVisit.entity.VisitLog;
import com.medvisit.MedVisit.repository.UserRepository;
import com.medvisit.MedVisit.repository.VisitLogRepository;
import com.medvisit.MedVisit.repository.VisitRepository;
import com.medvisit.MedVisit.service.VisitLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitLogServiceImpl implements VisitLogService {

    private final VisitLogRepository visitLogRepository;
    private final VisitRepository visitRepository;
    private final UserRepository userRepository;

    @Override
    public VisitLogDto create(VisitLogDto dto) {
        VisitLog log = new VisitLog();
        log.setAction(dto.getAction());
        log.setTimestamp(dto.getTimestamp());

        Visit visit = visitRepository.findById(dto.getVisitId())
                .orElseThrow(() -> new RuntimeException("Visit not found"));
        log.setVisit(visit);

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        log.setUser(user);

        return toDto(visitLogRepository.save(log));
    }

    @Override
    public VisitLogDto getById(Long id) {
        return visitLogRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("VisitLog not found"));
    }

    @Override
    public List<VisitLogDto> getAll() {
        return visitLogRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        visitLogRepository.deleteById(id);
    }

    private VisitLogDto toDto(VisitLog log) {
        return new VisitLogDto(
                log.getLogId(),
                log.getVisit().getVisitId(),
                log.getUser().getUserId(),
                log.getAction(),
                log.getTimestamp()
        );
    }
}
