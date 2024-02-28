package com.genesisairport.reservation.repository;

import com.genesisairport.reservation.TestJPAQueryFactoryConfiguration;
import com.genesisairport.reservation.entity.AvailableTime;
import com.genesisairport.reservation.entity.RepairShop;
import com.genesisairport.reservation.entity.Reservation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(TestJPAQueryFactoryConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class RepairShopRepositoryTest {

    @Autowired
    private RepairShopRepository repairShopRepository;

    @Test
    void saveRepairShop_WhenValidInput_ShouldSaveSuccessfully() {
        String shopName = "Sample Repair Shop";
        Integer capacityPerTime = 10;
        String address = "123 Main St, City, Country";
        LocalDateTime createDatetime = LocalDateTime.now();
        LocalDateTime updateDatetime = LocalDateTime.now();
        List<Reservation> reservations = new ArrayList<>();
        List<AvailableTime> availableTimes = new ArrayList<>();

        RepairShop repairShop = RepairShop.builder()
                .shopName(shopName)
                .capacityPerTime(capacityPerTime)
                .address(address)
                .createDatetime(createDatetime)
                .updateDatetime(updateDatetime)
                .reservation(reservations)
                .availableTime(availableTimes)
                .build();

        RepairShop savedRepairShop = repairShopRepository.save(repairShop);

        assertNotNull(savedRepairShop.getId());
    }

    @Test
    void findRepairShopByShopName_WhenExists_ShouldReturnRepairShop() {
        String shopName = "Sample Repair Shop";
        Integer capacityPerTime = 10;
        String address = "123 Main St, City, Country";
        LocalDateTime createDatetime = LocalDateTime.now();
        LocalDateTime updateDatetime = LocalDateTime.now();
        List<Reservation> reservations = new ArrayList<>();
        List<AvailableTime> availableTimes = new ArrayList<>();

        RepairShop repairShop = RepairShop.builder()
                .shopName(shopName)
                .capacityPerTime(capacityPerTime)
                .address(address)
                .createDatetime(createDatetime)
                .updateDatetime(updateDatetime)
                .reservation(reservations)
                .availableTime(availableTimes)
                .build();

        RepairShop savedRepairShop = repairShopRepository.save(repairShop);

        Optional<RepairShop> foundRepairShopOptional = repairShopRepository.findByShopName(savedRepairShop.getShopName());

        assertTrue(foundRepairShopOptional.isPresent());
    }

    @Test
    void deleteRepairShopById_WhenExists_ShouldDeleteSuccessfully() {
        String shopName = "Sample Repair Shop";
        Integer capacityPerTime = 10;
        String address = "123 Main St, City, Country";
        LocalDateTime createDatetime = LocalDateTime.now();
        LocalDateTime updateDatetime = LocalDateTime.now();
        List<Reservation> reservations = new ArrayList<>();
        List<AvailableTime> availableTimes = new ArrayList<>();

        RepairShop repairShop = RepairShop.builder()
                .shopName(shopName)
                .capacityPerTime(capacityPerTime)
                .address(address)
                .createDatetime(createDatetime)
                .updateDatetime(updateDatetime)
                .reservation(reservations)
                .availableTime(availableTimes)
                .build();

        RepairShop savedRepairShop = repairShopRepository.save(repairShop);

        repairShopRepository.deleteById(savedRepairShop.getId());

        Optional<RepairShop> deletedRepairShopOptional = repairShopRepository.findById(savedRepairShop.getId());
        assertTrue(deletedRepairShopOptional.isEmpty());
    }
}
