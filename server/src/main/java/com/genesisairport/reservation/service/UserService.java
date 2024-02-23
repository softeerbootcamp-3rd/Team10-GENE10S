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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final CarService carService;
    private final CustomerRepository customerRepository;

    public UserResponse.UserInfo getUserInfo(Long userId) {
        if (userId == null)
            throw new GeneralException(ResponseCode.BAD_REQUEST, "유저 아이디를 입력해주세요.");

        Optional<Customer> customer = customerRepository.findById(userId);
        if (customer.isEmpty())
            throw new GeneralException(ResponseCode.NOT_FOUND, "유저 정보를 찾을 수 없습니다.");

        List<Car> carList = customer.get().getCars();
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
                .userId(customer.get().getId())
                .name(customer.get().getName())
                .email(customer.get().getEmail())
                .birthdate(CommonDateFormat.date(customer.get().getBirthdate()))
                .phoneNumber(customer.get().getPhoneNumber())
                .carList(carInfo)
                .build();
    }

    public void patchUserInfo(Long userId, UserRequest.UserInfo userInfo) {
        if (userId == null)
            throw new GeneralException(ResponseCode.BAD_REQUEST, "유저 아이디를 입력해주세요.");

        Optional<Customer> customer = customerRepository.findById(userId);
        if (customer.isEmpty())
            throw new GeneralException(ResponseCode.NOT_FOUND, "유저 정보를 찾을 수 없습니다.");
        if (!Strings.isEmpty(userInfo.getName()))
            customer.get().setName(userInfo.getName());
        if (!Strings.isEmpty(userInfo.getBirthdate()))
            customer.get().setBirthdate(LocalDate.parse(userInfo.getBirthdate()));
        if (!Strings.isEmpty(userInfo.getPhoneNumber()))
            customer.get().setPhoneNumber(userInfo.getPhoneNumber());

        customerRepository.save(customer.get());
    }

    public UserResponse.Profile getUserProfile(Long userId) {
        if (userId == null)
            throw new GeneralException(ResponseCode.BAD_REQUEST, "유저 아이디를 입력해주세요.");

        Optional<Customer> customer = customerRepository.findById(userId);
        if (customer.isEmpty())
            throw new GeneralException(ResponseCode.NOT_FOUND, "유저 정보를 찾을 수 없습니다.");

        List<Car> carList = customer.get().getCars();
        String carSellName = null;
        String imageUrl = carService.getDefaultImage();
        if (!carList.isEmpty()) {
            carSellName = carList.get(0).getSellName();
            imageUrl = carService.getCarImage(carSellName);
        }

        return UserResponse.Profile.builder()
                .name(customer.get().getName())
                .email(customer.get().getEmail())
                .carSellName(carSellName)
                .imageUrl(imageUrl)
                .build();
    }
}
