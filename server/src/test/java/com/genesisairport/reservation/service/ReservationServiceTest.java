package com.genesisairport.reservation.service;

import com.genesisairport.reservation.common.enums.RedisKey;
import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.exception.GeneralException;
import com.genesisairport.reservation.common.util.CommonDateFormat;
import com.genesisairport.reservation.common.util.ConcurrencyManager;
import com.genesisairport.reservation.entity.AvailableTime;
import com.genesisairport.reservation.entity.RepairShop;
import com.genesisairport.reservation.entity.Reservation;
import com.genesisairport.reservation.repository.AvailableTimeRepository;
import com.genesisairport.reservation.repository.ReservationRepository;
import com.genesisairport.reservation.request.ReservationRequest;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ReservationServiceTest {

    @Autowired
    ReservationService reservationService;

    @Autowired
    ReservationRepository reservationRepository;

    @Mock
    private AvailableTimeRepository availableTimeRepository;

    static int successCount = 0;

    @Test
    @DisplayName("한번에 여러명이 예약할 경우 5명까지만 예약된다.")
    public void concurrency() throws InterruptedException {
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        for(int i=1; i<=threadCount; i++){
            final int index = i;
            ReservationRequest.ReservationPost requestBody = new ReservationRequest.ReservationPost(
                    "h"+i,
                    "블루핸즈 인천공항점",
                    "2024-03-31 12:00:00",
                    "2024-04-02 12:00:00",
                    "010-1234-1234",
                    "Genesis G80",
                    "123가 1234",
                    new Object(),
                    "요청"
            );
            executorService.submit(()->{
                try{
                    reservationService.reserve(1L, requestBody);
                    System.out.println(index + ": success");
                } catch (Exception e) {
                    System.out.println(index + ": fail");
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        List<Reservation> reservations = reservationRepository.findReservationsBy(1L, CommonDateFormat.localDateTime("2024-03-31 12:00:00"));

        assertEquals(5, reservations.size());
    }
}