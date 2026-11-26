package com.betancourt.backend.dto.user;

import com.betancourt.backend.domain.enums.UserRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserCreateDTO {
    @NotBlank @Size(min = 2, max = 100) private String firstName;
    @NotBlank @Size(min = 2, max = 100) private String lastName;
    @NotBlank @Email private String email;
    @Size(max = 30) private String phone;
    @NotBlank @Size(min = 12, max = 128) private String password;
    @NotNull private UserRole role;

    @Valid private ClientProfileDTO clientProfile;
    @Valid private EmployeeProfileDTO employeeProfile;
}