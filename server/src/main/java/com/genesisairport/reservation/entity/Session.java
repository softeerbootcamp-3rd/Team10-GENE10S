package com.genesisairport.reservation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
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

    @Getter
    @Column(name = "sid", length = 100)
    private String sessionId;

    @Getter
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
}
