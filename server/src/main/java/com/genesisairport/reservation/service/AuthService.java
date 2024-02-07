package com.genesisairport.reservation.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesisairport.reservation.Request.LoginRequest;
import com.genesisairport.reservation.entity.Customer;
import com.genesisairport.reservation.entity.Session;
import com.genesisairport.reservation.respository.CustomerRepository;
import com.genesisairport.reservation.respository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final SessionRepository sessionRepository;

    private final CustomerRepository customerRepository;

    private final ObjectMapper objectMapper;

    // 토큰 요청
    public Session tokenRequest(LoginRequest.Login requestBody) {
        final String tokenEndpoint = "https://accounts.genesis.com/api/account/ccsp/user/oauth2/token";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        // ID, Secret 값을 Base64로 인코딩
        String clientCredentials = requestBody.getClientId() + ":" + requestBody.getClientSecret();
        String encodedCredentials = Base64.encodeBase64String(clientCredentials.getBytes(StandardCharsets.UTF_8));

        headers.add("Authorization", "Basic " + encodedCredentials); // Authorization 헤더에 인코딩된 ID, Secret 값 추가
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED); // Content-Type 설정

        String parameters = getParameters(requestBody);
        HttpEntity<String> request = new HttpEntity<>(parameters, headers); // 요청 헤더와 바디 설정

        ResponseEntity<String> response = restTemplate.postForEntity(tokenEndpoint, request, String.class); // POST 요청

        return buildSession(response.getBody());
    }

    // 요청 DTO를 이용하여 쿼리 파라미터 생성
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

    // 세션 저장
    public Session buildSession(String response) {
        try {
            JsonNode rootNode = objectMapper.readTree(response); // 문자열을 JsonNode로 변환

            String token_type = rootNode.path("token_type").asText(); // path 메소드를 이용하여 키에 해당하는 값을 가져옴 (없으면 빈 문자열 반환, NullPointer 방지)
            long expires_in = rootNode.path("expires_in").asLong();
            String refresh_token = rootNode.path("refresh_token").asText();
            boolean success = rootNode.path("success").asBoolean();
            String access_token = rootNode.path("access_token").asText();

            return Session.builder()
                    .sessionId(UUID.randomUUID().toString())
                    .accessToken(access_token)
                    .tokenType(token_type)
                    .refreshToken(refresh_token)
                    .expiresIn(LocalDateTime.now().plusSeconds(expires_in))
                    .createDateTime(LocalDateTime.now())
                    .updateDateTime(LocalDateTime.now())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("세션 저장에 실패했습니다.");
        }
    }

    // 사용자 프로필을 현대자동차 인증 서버에 요청
    public void userProfileRequest(Session session) {
        final String tokenEndpoint = "https://prd-kr-ccapi.genesis.com:8081/api/v1/user/profile";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        String accessToken = session.getAccessToken();

        headers.add("Authorization", "Bearer " + accessToken); // Authorization 헤더에 Access Token 추가
        HttpEntity<String> request = new HttpEntity<>(headers); // HttpEntity에 헤더만 포함시킴

        ResponseEntity<String> response = restTemplate.exchange(
                tokenEndpoint, HttpMethod.GET, request, String.class); // GET 요청을 위해 exchange 메소드 사용
        log.info(response.getBody());
        Customer customer = saveCustomer(response.getBody());
        session.setCustomerId(customer.getId());
        sessionRepository.save(session);
    }

    public Customer saveCustomer(String response) {
        try {
            JsonNode rootNode = objectMapper.readTree(response);

            String email = rootNode.path("email").asText();
            String name = rootNode.path("name").asText();
            String phone_number = rootNode.path("mobileNum").asText();
            String birthdateStr = rootNode.path("birthdate").asText();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd"); // 날짜 형식 지정
            LocalDate birthdate = LocalDate.parse(birthdateStr, formatter);

            Optional<Customer> existCustomer = customerRepository.findByEmail(email);
            if (existCustomer.isPresent())
                return existCustomer.get();

            Customer customer = Customer.builder()
                    .email(email)
                    .name(name)
                    .phoneNumber(phone_number)
                    .birthdate(birthdate)
                    .createDateTime(LocalDateTime.now())
                    .updateDateTime(LocalDateTime.now())
                    .build();
            return customerRepository.save(customer);
        } catch (Exception e) {
            throw new RuntimeException("고객 정보 저장에 실패했습니다.");
        }
    }
}

