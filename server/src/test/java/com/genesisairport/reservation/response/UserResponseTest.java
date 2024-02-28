package com.genesisairport.reservation.response;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserResponseTest {

    @Test
    void createLogin_shouldCreateInstanceWithCorrectValue() {
        String sid = "abc123";

        UserResponse.Login login = UserResponse.Login.builder()
                .sid(sid)
                .build();

        assertNotNull(login);
        assertEquals(sid, login.getSid());
    }

    @Test
    void createUserInfo_shouldCreateInstanceWithCorrectValues() {
        // Given
        Long userId = 123L;
        String name = "Alice";
        String email = "alice@example.com";
        String birthdate = "1990-01-01";
        String phoneNumber = "123-456-7890";
        UserResponse.UserInfo.CarInfo carInfo1 = UserResponse.UserInfo.CarInfo.builder()
                .carId(1L)
                .vin("VIN123")
                .sellName("Genesis G80")
                .plateNumber("ABC123")
                .imageUrl("https://example.com/car1.jpg")
                .build();
        UserResponse.UserInfo.CarInfo carInfo2 = UserResponse.UserInfo.CarInfo.builder()
                .carId(2L)
                .vin("VIN456")
                .sellName("Genesis G90")
                .plateNumber("DEF456")
                .imageUrl("https://example.com/car2.jpg")
                .build();
        java.util.List<UserResponse.UserInfo.CarInfo> carList = Arrays.asList(carInfo1, carInfo2);

        UserResponse.UserInfo userInfo = UserResponse.UserInfo.builder()
                .userId(userId)
                .name(name)
                .email(email)
                .birthdate(birthdate)
                .phoneNumber(phoneNumber)
                .carList(carList)
                .build();

        assertNotNull(userInfo);
        assertEquals(userId, userInfo.getUserId());
        assertEquals(name, userInfo.getName());
        assertEquals(email, userInfo.getEmail());
        assertEquals(birthdate, userInfo.getBirthdate());
        assertEquals(phoneNumber, userInfo.getPhoneNumber());
        assertEquals(carList, userInfo.getCarList());
    }

    @Test
    void createProfile_shouldCreateInstanceWithCorrectValues() {
        String name = "John Doe";
        String email = "test@example.com";
        String carSellName = "Genesis G80";
        String imageUrl = "https://example.com/profile.jpg";

        UserResponse.Profile profile = UserResponse.Profile.builder()
                .name(name)
                .email(email)
                .carSellName(carSellName)
                .imageUrl(imageUrl)
                .build();

        assertNotNull(profile);
        assertEquals(name, profile.getName());
        assertEquals(email, profile.getEmail());
        assertEquals(carSellName, profile.getCarSellName());
        assertEquals(imageUrl, profile.getImageUrl());
    }
}
