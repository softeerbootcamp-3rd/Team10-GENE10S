package com.genesisairport.reservation.config;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class S3ConfigTest {

    @Test
    void amazonS3Client_shouldReturnNonNullInstance() {
        S3Config s3Config = new S3Config();

        AmazonS3 amazonS3 = s3Config.amazonS3Client();

        assertNotNull(amazonS3);
    }
}
