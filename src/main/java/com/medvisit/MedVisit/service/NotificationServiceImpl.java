package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.NotificationDto;
import com.medvisit.MedVisit.entity.Notification;
import com.medvisit.MedVisit.entity.User;
import com.medvisit.MedVisit.repository.NotificationRepository;
import com.medvisit.MedVisit.repository.UserRepository;
import com.medvisit.MedVisit.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

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

    private void copyDtoToEntity(NotificationDto dto, Notification notification) {
        notification.setMessage(dto.getMessage());
        notification.setRead(dto.isRead());
        notification.setType(dto.getType());
        notification.setCreatedAt(dto.getCreatedAt());

        User recipient = userRepository.findById(dto.getRecipientId())
                .orElseThrow(() -> new RuntimeException("Recipient user not found"));
        notification.setRecipient(recipient);
    }

    private NotificationDto toDto(Notification notification) {
        return new NotificationDto(
                notification.getNotificationId(),
                notification.getMessage(),
                notification.isRead(),
                notification.getType(),
                notification.getCreatedAt(),
                notification.getRecipient().getUserId()
        );
    }
}
