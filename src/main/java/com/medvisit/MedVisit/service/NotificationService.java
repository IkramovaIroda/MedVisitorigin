package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.NotificationDto;

import java.util.List;

public interface NotificationService {
    NotificationDto create(NotificationDto dto);
    NotificationDto getById(Long id);
    List<NotificationDto> getAll();
    List<NotificationDto> getByRecipient(Long recipientId);
    NotificationDto markAsRead(Long id);
    void delete(Long id);
}
