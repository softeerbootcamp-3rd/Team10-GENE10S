package com.genesisairport.reservation.controller.admin;

import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.model.ResponseDto;
import com.genesisairport.reservation.service.S3Service;
import com.genesisairport.reservation.service.admin.AReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v2/admin/reservation")
public class AReservationController {

    private final AReservationService aReservationService;
    private final S3Service s3Service;

    @PostMapping("/{id}/image")
    public ResponseEntity<ResponseDto> uploadImage(@PathVariable("id") Long id,
                                                   @RequestParam("status") Integer status,
                                                   @RequestPart("image") MultipartFile image) throws IOException {
        String imageUrl = s3Service.saveFile(image);
        aReservationService.addMaintenanceImage(id, status, imageUrl);
        return new ResponseEntity<>(ResponseDto.of(true, ResponseCode.OK), HttpStatus.OK);
    }
}
