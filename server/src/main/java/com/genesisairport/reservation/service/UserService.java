package com.genesisairport.reservation.service;

import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.exception.GeneralException;
import com.genesisairport.reservation.common.util.CommonDateFormat;
import com.genesisairport.reservation.entity.Car;
import com.genesisairport.reservation.entity.Customer;
import com.genesisairport.reservation.repository.CustomerRepository;
import com.genesisairport.reservation.request.UserRequest;
import com.genesisairport.reservation.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final CarService carService;
    private final CustomerRepository customerRepository;

    public UserResponse.UserInfo getUserInfo(Long userId) {
        if (userId == null)
            throw new GeneralException(ResponseCode.BAD_REQUEST, "유저 아이디를 입력해주세요.");

        Customer customer = customerRepository.findCustomerById(userId);
        List<Car> carList = customer.getCars();
        List<UserResponse.UserInfo.CarInfo> carInfo = carList.stream()
                .map(car -> UserResponse.UserInfo.CarInfo.builder()
                        .carId(car.getId())
                        .vin(car.getVin())
                        .sellName(car.getSellName())
                        .plateNumber(car.getPlateNumber())
                        .imageUrl(carService.getCarImage(car.getSellName()))
                        .build())
                .toList();

        return UserResponse.UserInfo.builder()
                .userId(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .birthdate(CommonDateFormat.date(customer.getBirthdate()))
                .phoneNumber(customer.getPhoneNumber())
                .carList(carInfo)
                .build();
    }

    public void patchUserInfo(Long userId, UserRequest.UserInfo userInfo) {
        if (userId == null)
            throw new GeneralException(ResponseCode.BAD_REQUEST, "유저 아이디를 입력해주세요.");

        Customer customer = customerRepository.findCustomerById(userId);
        if (!Strings.isEmpty(userInfo.getName()))
            customer.setName(userInfo.getName());
        if (!Strings.isEmpty(userInfo.getBirthdate()))
            customer.setBirthdate(LocalDate.parse(userInfo.getBirthdate()));
        if (!Strings.isEmpty(userInfo.getPhoneNumber()))
            customer.setPhoneNumber(userInfo.getPhoneNumber());

        customerRepository.save(customer);
    }

    public UserResponse.Profile getUserProfile(Long userId) {
        if (userId == null)
            throw new GeneralException(ResponseCode.BAD_REQUEST, "유저 아이디를 입력해주세요.");

        Customer customer = customerRepository.findCustomerById(userId);
        List<Car> carList = customer.getCars();
        String carSellName = null;
        String imageUrl = carService.getDefaultImage();
        if (!carList.isEmpty()) {
            carSellName = carList.get(0).getSellName();
            imageUrl = carService.getCarImage(carSellName);
        }

        return UserResponse.Profile.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .carSellName(carSellName)
                .imageUrl(imageUrl)
                .build();
    }
}
