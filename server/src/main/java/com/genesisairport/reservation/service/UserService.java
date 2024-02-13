package com.genesisairport.reservation.service;

import com.genesisairport.reservation.entity.Car;
import com.genesisairport.reservation.entity.Customer;
import com.genesisairport.reservation.response.UserResponse;
import com.genesisairport.reservation.respository.CustomerRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final SessionService sessionService;
    private final CarService carService;

    public UserResponse.Profile getUserProfile(HttpServletRequest request) {
        Optional<Customer> customerOptional = sessionService.getLoggedInCustomer(request);

        // TODO: 로그인 유저 없을때
        if (customerOptional.isEmpty())
            return UserResponse.Profile.builder().build();

        Customer customer = customerOptional.get();
        List<Car> carList = customer.getCar();
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
