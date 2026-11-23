package com.betancourt.backend.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserStatusUpdateDTO {
    @NotNull private Boolean isActive;
}