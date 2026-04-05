package com.finance.backend.dtos;

import com.finance.backend.entities.Role;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {

    private String name;

    @Email(message = "Invalid email format")
    private String email;

    private String password;

    private Role role;

    private String status;
}
