package com.genesisairport.reservation.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesisairport.reservation.request.UserRequest;
import com.genesisairport.reservation.entity.Customer;
import com.genesisairport.reservation.respository.CustomerRepository;
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

    private final CustomerRepository customerRepository;

    private final ObjectMapper objectMapper;

    // 토큰 요청
    public String tokenRequest(UserRequest.Login requestBody) {
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

        return extractStringValue(response.getBody(), "access_token");
    }

    // 요청 DTO를 이용하여 쿼리 파라미터 생성
    private String getParameters(UserRequest.Login requestBody) {
        String queryParameters = UriComponentsBuilder.newInstance()
                .queryParam("grant_type", "authorization_code")
                .queryParam("code", requestBody.getCode())
                .queryParam("redirect_uri", requestBody.getRedirectUri())
                .queryParam("client_id", requestBody.getClientId())
                .queryParam("client_secret", requestBody.getClientSecret())
                .build().encode().toUriString();

        return queryParameters.substring(1);
    }

    // 사용자 프로필을 현대자동차 인증 서버에 요청
    public long userProfileRequest(String accessToken) {
        final String tokenEndpoint = "https://prd-kr-ccapi.genesis.com:8081/api/v1/user/profile";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "Bearer " + accessToken); // Authorization 헤더에 Access Token 추가
        HttpEntity<String> request = new HttpEntity<>(headers); // HttpEntity에 헤더만 포함시킴

        ResponseEntity<String> response = restTemplate.exchange(
                tokenEndpoint, HttpMethod.GET, request, String.class); // GET 요청을 위해 exchange 메소드 사용
        log.info(response.getBody());

        String email = extractStringValue(response.getBody(), "email");

        Customer customer = customerRepository.findByEmail(email).orElse(null);

        if (customer == null) {
            customer = createCustomer(response.getBody());
            return customerRepository.save(customer).getId();
        }

        return customer.getId();
    }

    public Customer createCustomer(String response) {
        String email = extractStringValue(response, "email");
        String name = extractStringValue(response, "name");
        String phoneNumberStr = extractStringValue(response, "mobileNum");
        String birthdateStr = extractStringValue(response, "birthdate");

        String phoneNumber = formatPhoneNumber(phoneNumberStr);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd"); // 날짜 형식 지정
        LocalDate birthdate = LocalDate.parse(birthdateStr, formatter);

        Optional<Customer> existCustomer = customerRepository.findByEmail(email);
        if (existCustomer.isPresent())
            return existCustomer.get();

        return Customer.builder()
                .email(email)
                .name(name)
                .phoneNumber(phoneNumber)
                .birthdate(birthdate)
                .createDateTime(LocalDateTime.now())
                .updateDateTime(LocalDateTime.now())
                .build();
    }

    private String extractStringValue(String response, String attributeName) {
        try {
            JsonNode rootNode = objectMapper.readTree(response);
            return rootNode.path(attributeName).asText();
        } catch (IOException e) {
            throw new RuntimeException("토큰 발급에 실패했습니다.");
        }
    }

    private String formatPhoneNumber(String phoneNumber) {
        String cleanPhoneNumber = phoneNumber.replaceAll("[^0-9]", "");

        if (cleanPhoneNumber.length() == 11) {
            return cleanPhoneNumber.replaceFirst("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
        } else if (cleanPhoneNumber.length() == 10) {
            return cleanPhoneNumber.replaceFirst("(\\d{2})(\\d{3,4})(\\d{4})", "$1-$2-$3");
        } else {
            return phoneNumber;
        }
    }

}

