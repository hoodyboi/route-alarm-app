package com.example.route_alarm_app.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "login_id", unique = true, nullable = false, length = 16)
    private String loginId;

    @Column(name = "email", unique = true, length = 256)
    private String email;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @Column(name = "role", length = 10)
    private String role;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "oauth_type", length = 20)
    private String oauthType;

    @Column(name = "oauth_id", length = 64)
    private String oauthId;

    @Column(name = "uuid", length = 36, unique = true)
    private String uuid;

    @Builder
    public User(String loginId, String email, String password, String role, String oauthType, String oauthId, String uuid){
        this.loginId = loginId;
        this.email = email;
        this.password = password;
        this.role = role;
        this.oauthId = oauthId;
        this.oauthType = oauthType;
        this.uuid = uuid;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isDeleted = false;
    }

    public void update(String email, String password, String role){
        this.email = email;
        this.password = password;
        this.role = role;
        this.updatedAt = LocalDateTime.now();
    }

    public void delete(){
        this.isDeleted = true;
        this.updatedAt = LocalDateTime.now();
    }
}