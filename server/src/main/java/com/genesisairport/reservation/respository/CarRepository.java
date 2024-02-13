package com.genesisairport.reservation.respository;

import com.genesisairport.reservation.entity.Car;
import com.genesisairport.reservation.response.ReservationResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT c.sellName AS sellName, c.plateNumber AS plateNumber, i.imageUrl AS imageUrl " +
            "FROM Car c " +
            "JOIN CarImage i ON c.sellName = i.sellName " +
            "WHERE c.customer.id = :customerId " +
            "ORDER BY c.id ASC")
    List<ReservationResponse.CarInfo> findCarsByCustomer(@Param("customerId") Long customerId);

}
