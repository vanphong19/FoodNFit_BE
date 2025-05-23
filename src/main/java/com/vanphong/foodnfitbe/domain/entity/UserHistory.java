package com.vanphong.foodnfitbe.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "users_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserHistory {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "email")
    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "gender")
    private boolean gender;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "changed_at")
    private LocalTime changedAt;

    private String changeType;

    @Column(name = "changed_by")
    private UUID changedBy;
}