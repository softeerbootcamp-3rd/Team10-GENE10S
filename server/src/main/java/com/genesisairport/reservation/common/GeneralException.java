package com.genesisairport.reservation.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GeneralException extends RuntimeException{
    private final ResponseCode responseCode;
    private final String message;
}