package com.genesisairport.reservation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "session")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "sid", length = 100)
    private String sessionId;

    @Column(name = "access_token", length = 2000)
    private String accessToken;

    @Column(name = "token_type", length = 20)
    private String tokenType;

    @Column(name = "refresh_token", length = 2000)
    private String refreshToken;

    @Column(name = "expires_in", nullable = false)
    private LocalDateTime expiresIn;

    @Column(name = "create_datetime", nullable = false)
    private LocalDateTime createDateTime;

    @Column(name = "update_datetime", nullable = false)
    private LocalDateTime updateDateTime;

    public String getSessionId() {
        return sessionId;
    }
}
