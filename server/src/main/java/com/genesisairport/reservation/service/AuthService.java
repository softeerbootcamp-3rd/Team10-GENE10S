package com.genesisairport.reservation.service;

import com.genesisairport.reservation.Request.LoginRequest;
import com.genesisairport.reservation.entity.Session;
import com.genesisairport.reservation.respository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final SessionRepository sessionRepository;

    public String tokenRequest(LoginRequest.Login requestBody) {
        final String tokenEndpoint = "https://accounts.genesis.com/api/account/ccsp/user/oauth2/token";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        // ID, Secret 값을 Base64로 인코딩
        String clientCredentials = requestBody.getClientId() + ":" + requestBody.getClientSecret();
        String encodedCredentials = Base64.encodeBase64String(clientCredentials.getBytes(StandardCharsets.UTF_8));
        log.info("Encoded Credentials: " + encodedCredentials);
        headers.add("Authorization", "Basic " + encodedCredentials); // Authorization 헤더에 인코딩된 ID, Secret 값 추가
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED); // Content-Type 설정

        String parameters = getParameters(requestBody);
        HttpEntity<String> request = new HttpEntity<>(parameters, headers); // 요청 헤더와 바디 설정

        ResponseEntity<String> response = restTemplate.postForEntity(tokenEndpoint, request, String.class); // POST 요청

        return saveSession(response.getBody());
    }

    private String getParameters(LoginRequest.Login requestBody) {
        String queryParameters = UriComponentsBuilder.newInstance()
                .queryParam("grant_type", "authorization_code")
                .queryParam("code", requestBody.getCode())
                .queryParam("redirect_uri", requestBody.getRedirectUri())
                .queryParam("client_id", requestBody.getClientId())
                .queryParam("client_secret", requestBody.getClientSecret())
                .build().encode().toUriString();

        return queryParameters.substring(1);
    }

    private String saveSession(String response) {
        String[] split = response.split(",");

        String token_type = split[0].split(":")[1];
        String expires_in = split[1].split(":")[1];
        String refresh_token = split[2].split(":")[1];
        String success = split[4].split(":")[1];
        String access_token = split[5].split(":")[1];

        Session session = Session.builder()
                .sessionId(UUID.randomUUID().toString())
                .accessToken(access_token)
                .tokenType(token_type)
                .refreshToken(refresh_token)
                .expiresIn(LocalDateTime.now().plusSeconds(Long.parseLong(expires_in)))
                .build();

        sessionRepository.save(session);
        return session.getSessionId();
    }
}
