package com.betancourt.backend.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;

@Data
public class EmployeeProfileDTO {
    @NotBlank @Size(min = 2, max = 100) private String jobTitle;
    @Size(max = 150) private String specialty;
    private LocalDate hireDate;
    private Boolean isAvailable = true;
    private String notes;
}