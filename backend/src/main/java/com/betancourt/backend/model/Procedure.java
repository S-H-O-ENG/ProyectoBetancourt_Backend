package com.betancourt.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "procedures")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Procedure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 150)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @OneToMany(mappedBy = "procedure", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sessionNumber ASC")
    @Builder.Default
    private List<ProcedureSession> procedureSessions = new ArrayList<>();
}
