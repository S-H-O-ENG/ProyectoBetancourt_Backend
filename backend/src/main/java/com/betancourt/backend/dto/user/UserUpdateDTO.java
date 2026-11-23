package com.betancourt.backend.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateDTO {
    @Size(min = 2, max = 100) private String firstName;
    @Size(min = 2, max = 100) private String lastName;
    @Email private String email;
    @Size(max = 30) private String phone;
}