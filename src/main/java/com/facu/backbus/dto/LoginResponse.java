package com.facu.backbus.dto;

import com.facu.backbus.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private Long id;
    private String fullName;
    private String login;
    private UserType userType;
}
