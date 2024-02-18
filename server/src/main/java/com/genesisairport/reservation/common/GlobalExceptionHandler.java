package com.genesisairport.reservation.common;

import org.springframework.http.HttpHeaders;
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

    private ResponseEntity<Object> handleExceptionInternal(Exception e, ResponseCode responseCode,
                                                           HttpHeaders headers, WebRequest webRequest){

        DataResponseDto<Object> body = DataResponseDto.of(null, responseCode.getMessage(e));

        return super.handleExceptionInternal(
                e,
                body,
                headers,
                responseCode.getHttpStatus(),
                webRequest);
    }
}