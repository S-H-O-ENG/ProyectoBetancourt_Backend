package com.betancourt.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "procedure_sessions", uniqueConstraints = {
    @UniqueConstraint(name = "uq_procedure_session_number", columnNames = {"procedure_id", "session_number"})
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ProcedureSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "procedure_id", nullable = false)
    private Procedure procedure;

    @Column(name = "session_number", nullable = false)
    private Integer sessionNumber;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;
}