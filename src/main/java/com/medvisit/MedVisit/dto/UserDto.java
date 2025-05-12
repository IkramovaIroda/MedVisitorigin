package com.medvisit.MedVisit.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String username;
    private String password;
    private String role;
    private Boolean active;
}
