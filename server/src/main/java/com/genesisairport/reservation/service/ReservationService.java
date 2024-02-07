package com.genesisairport.reservation.service;

import com.genesisairport.reservation.Response.ReservationResponse;
import com.genesisairport.reservation.common.DataResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    public List<ReservationResponse.CarInfo> getCarList() {
        List<ReservationResponse.CarInfo> carInfoList = new ArrayList<>();


        ReservationResponse.CarInfo carInfo = ReservationResponse.CarInfo.builder()
                .sellName("Sonata")
                .plateNumber("222라 2222")
                .build();
        ReservationResponse.CarInfo carInfo1 = ReservationResponse.CarInfo.builder()
                .sellName("Sonata")
                .plateNumber("111마 1111")
                .build();

        carInfoList.add(carInfo);
        carInfoList.add(carInfo1);

        return carInfoList;
    }

}
