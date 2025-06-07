package com.vanphong.foodnfitbe.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reminders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Reminders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private Users user;
    @Column(name = "reminder_type")
    private String reminderType;
    @Column(name = "message")
    private String message;
    @Column(name = "scheduled_time")
    private LocalDateTime scheduledTime;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "frequency")
    private String frequency;
}
