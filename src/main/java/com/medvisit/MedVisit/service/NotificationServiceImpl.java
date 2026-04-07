package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.NotificationDto;
import com.medvisit.MedVisit.entity.Notification;
import com.medvisit.MedVisit.entity.User;
import com.medvisit.MedVisit.entity.Visit;
import com.medvisit.MedVisit.repository.NotificationRepository;
import com.medvisit.MedVisit.repository.UserRepository;
import com.medvisit.MedVisit.repository.VisitRepository;
import com.medvisit.MedVisit.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final VisitRepository visitRepository;

    @Override
    public NotificationDto create(NotificationDto dto) {
        Notification notification = new Notification();
        copyDtoToEntity(dto, notification);
        return toDto(notificationRepository.save(notification));
    }

    @Override
    public NotificationDto getById(Long id) {
        return notificationRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
    }

    @Override
    public List<NotificationDto> getAll() {
        return notificationRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationDto> getByRecipient(Long recipientId) {
        return notificationRepository.findByRecipientUserId(recipientId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationDto markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        return toDto(notificationRepository.save(notification));
    }

    @Override
    public void delete(Long id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public NotificationDto sendToPatient(NotificationDto dto) {
        Notification notification = new Notification();
        notification.setMessage(dto.getMessage());
        notification.setType(dto.getType() != null ? dto.getType() : "MESSAGE");
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        
        // Set recipient (patient)
        User recipient = userRepository.findById(dto.getRecipientId())
                .orElseThrow(() -> new RuntimeException("Recipient user not found"));
        notification.setRecipient(recipient);
        
        // Set sender (doctor or system)
        if (dto.getSenderId() != null) {
            User sender = userRepository.findById(dto.getSenderId())
                    .orElseThrow(() -> new RuntimeException("Sender user not found"));
            notification.setSender(sender);
        }
        
        // Optionally link to a visit
        if (dto.getVisitId() != null) {
            Visit visit = visitRepository.findById(dto.getVisitId())
                    .orElseThrow(() -> new RuntimeException("Visit not found"));
            notification.setVisit(visit);
        }
        
        return toDto(notificationRepository.save(notification));
    }

    private void copyDtoToEntity(NotificationDto dto, Notification notification) {
        notification.setMessage(dto.getMessage());
        notification.setRead(dto.isRead());
        notification.setType(dto.getType());
        notification.setCreatedAt(dto.getCreatedAt());

        User recipient = userRepository.findById(dto.getRecipientId())
                .orElseThrow(() -> new RuntimeException("Recipient user not found"));
        notification.setRecipient(recipient);
        
        if (dto.getSenderId() != null) {
            User sender = userRepository.findById(dto.getSenderId())
                    .orElseThrow(() -> new RuntimeException("Sender user not found"));
            notification.setSender(sender);
        }
        
        if (dto.getVisitId() != null) {
            Visit visit = visitRepository.findById(dto.getVisitId())
                    .orElseThrow(() -> new RuntimeException("Visit not found"));
            notification.setVisit(visit);
        }
    }

    private NotificationDto toDto(Notification notification) {
        return new NotificationDto(
                notification.getNotificationId(),
                notification.getMessage(),
                notification.isRead(),
                notification.getType(),
                notification.getCreatedAt(),
                notification.getRecipient().getUserId(),
                notification.getSender() != null ? notification.getSender().getUserId() : null,
                notification.getVisit() != null ? notification.getVisit().getVisitId() : null
        );
    }
}
