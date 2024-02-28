package com.genesisairport.reservation.repository;

import com.genesisairport.reservation.TestJPAQueryFactoryConfiguration;
import com.genesisairport.reservation.entity.CarImage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest
@Import(TestJPAQueryFactoryConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class CarImageRepositoryTest {

    @Autowired
    private CarImageRepository carImageRepository;

    @Test
    void findBySellName_WhenCarImageExists_ReturnCarImage() {
        String sellName = "TestSellName";
        String imageUrl = "https://example.com/image.jpg";
        CarImage carImage = new CarImage();
        carImage.setSellName(sellName);
        carImage.setImageUrl(imageUrl);
        carImageRepository.save(carImage);

        Optional<CarImage> foundCarImageOptional = carImageRepository.findBySellName(sellName);

        Assertions.assertTrue(foundCarImageOptional.isPresent());
        CarImage foundCarImage = foundCarImageOptional.get();
        Assertions.assertEquals(sellName, foundCarImage.getSellName());
    }

    @Test
    void findBySellName_WhenCarImageDoesNotExist_ReturnEmptyOptional() {
        String nonExistentSellName = "nonExistentSellName";

        Optional<CarImage> foundCarImageOptional = carImageRepository.findBySellName(nonExistentSellName);

        Assertions.assertTrue(foundCarImageOptional.isEmpty());
    }
}
