package com.genesisairport.reservation.repository;

import com.genesisairport.reservation.TestJPAQueryFactoryConfiguration;
import com.genesisairport.reservation.entity.MaintenanceImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(TestJPAQueryFactoryConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MaintenanceImageRepositoryTest {

    @Autowired
    private MaintenanceImageRepository maintenanceImageRepository;

    @Test
    void saveMaintenanceImage_WhenValidInput_ShouldSaveSuccessfully() {
        Integer status = 0;
        String imageUrl = "http://test.example.com/image";
        String objectKey = "testKey";
        MaintenanceImage maintenanceImage = new MaintenanceImage();
        maintenanceImage.setStatus(status);
        maintenanceImage.setImageUrl(imageUrl);
        maintenanceImage.setObjectKey(objectKey);
        MaintenanceImage savedMaintenanceImage = maintenanceImageRepository.save(maintenanceImage);

        assertNotNull(savedMaintenanceImage.getId());
    }

    @Test
    void findMaintenanceImageById_WhenExists_ShouldReturnMaintenanceImage() {
        Integer status = 0;
        String imageUrl = "http://test.example.com/image";
        String objectKey = "testKey";
        MaintenanceImage maintenanceImage = new MaintenanceImage();
        maintenanceImage.setStatus(status);
        maintenanceImage.setImageUrl(imageUrl);
        maintenanceImage.setObjectKey(objectKey);
        MaintenanceImage savedMaintenanceImage = maintenanceImageRepository.save(maintenanceImage);

        Optional<MaintenanceImage> foundMaintenanceImageOptional = maintenanceImageRepository.findById(savedMaintenanceImage.getId());

        assertTrue(foundMaintenanceImageOptional.isPresent());
    }

    @Test
    void deleteMaintenanceImageById_WhenExists_ShouldDeleteSuccessfully() {
        Integer status = 0;
        String imageUrl = "http://test.example.com/image";
        String objectKey = "testKey";
        MaintenanceImage maintenanceImage = new MaintenanceImage();
        maintenanceImage.setStatus(status);
        maintenanceImage.setImageUrl(imageUrl);
        maintenanceImage.setObjectKey(objectKey);
        MaintenanceImage savedMaintenanceImage = maintenanceImageRepository.save(maintenanceImage);

        maintenanceImageRepository.deleteById(savedMaintenanceImage.getId());

        Optional<MaintenanceImage> deletedMaintenanceImageOptional = maintenanceImageRepository.findById(savedMaintenanceImage.getId());
        assertTrue(deletedMaintenanceImageOptional.isEmpty());
    }
}
