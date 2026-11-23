package com.betancourt.backend.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "employee_profiles", indexes = {
    @Index(name = "ix_employee_profiles_available", columnList = "is_available")
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class EmployeeProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private Usuario user;

    @Column(name = "job_title", nullable = false, length = 100)
    private String jobTitle;

    @Column(length = 150)
    private String specialty;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(name = "is_available", nullable = false)
    @Builder.Default
    private Boolean isAvailable = true;

    @Column(columnDefinition = "TEXT")
    private String notes;
}