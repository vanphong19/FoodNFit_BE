package com.vanphong.foodnfitbe.domain.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "feedback")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "message", columnDefinition = "text")
    private String message;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "purpose", columnDefinition = "text")
    private String purpose;

    @Column(name = "inquiry", columnDefinition = "text")
    private String inquiry;
}