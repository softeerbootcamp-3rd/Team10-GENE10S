package com.genesisairport.reservation.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserRequestTest {

    @Test
    void createLogin_shouldCreateInstanceWithCorrectValues() {
        String grantType = "password";
        String code = "123456";
        String redirectUri = "https://example.com/callback";
        String clientId = "client123";
        String clientSecret = "secret123";

        UserRequest.Login login = UserRequest.Login.builder()
                .grantType(grantType)
                .code(code)
                .redirectUri(redirectUri)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();

        assertNotNull(login);
        assertEquals(grantType, login.getGrantType());
        assertEquals(code, login.getCode());
        assertEquals(redirectUri, login.getRedirectUri());
        assertEquals(clientId, login.getClientId());
        assertEquals(clientSecret, login.getClientSecret());
    }

    @Test
    void createUserInfo_shouldCreateInstanceWithCorrectValues() {
        String name = "John Doe";
        String birthdate = "1990-01-01";
        String phoneNumber = "123-456-7890";

        UserRequest.UserInfo userInfo = UserRequest.UserInfo.builder()
                .name(name)
                .birthdate(birthdate)
                .phoneNumber(phoneNumber)
                .build();

        assertNotNull(userInfo);
        assertEquals(name, userInfo.getName());
        assertEquals(birthdate, userInfo.getBirthdate());
        assertEquals(phoneNumber, userInfo.getPhoneNumber());
    }
}
