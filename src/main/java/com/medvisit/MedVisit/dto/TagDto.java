package com.medvisit.MedVisit.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {
    private Long tagId;
    private String name;
    private String description;
    private LocalDateTime createdAt;
}
