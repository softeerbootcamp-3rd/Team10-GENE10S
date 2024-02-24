package com.genesisairport.reservation.common.util;

import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.exception.GeneralException;
import com.genesisairport.reservation.common.model.ResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(annotations = {RestController.class})
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<Object> handleGeneralException(GeneralException generalException, WebRequest webRequest){
        return handleExceptionInternal(
                generalException,
                generalException.getResponseCode(),
                HttpHeaders.EMPTY,
                webRequest);
    }

    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception e, WebRequest request) {
        return handleExceptionInternal(
                e,
                ResponseCode.INTERNAL_SERVER_ERROR,
                HttpHeaders.EMPTY,
                request
        );
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception e, ResponseCode responseCode,
                                                           HttpHeaders headers, WebRequest webRequest){
        e.printStackTrace();
        ResponseDto body = ResponseDto.of(false, responseCode, e.getMessage());

        return super.handleExceptionInternal(
                e,
                body,
                headers,
                HttpStatus.OK,
                webRequest);
    }
}
