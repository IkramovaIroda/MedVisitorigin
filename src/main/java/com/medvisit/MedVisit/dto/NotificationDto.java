package com.medvisit.MedVisit.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private Long notificationId;
    private String message;
    private boolean read;
    private String type;
    private LocalDateTime createdAt;
    private Long recipientId;
}
