package com.genesisairport.reservation.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ProgressStage {
    RESERVATION_APPROVED("예약완료"),
    TO_GARAGE("차량인수"),
    MAINTENANCE("정비중"),
    PARKING("보관중"),
    TO_CUSTOMER("차량인계");

    private final String name;

    @JsonCreator
    public static ProgressStage of(String progress) {
        return Arrays.stream(ProgressStage.values())
                .filter(i -> i.name.equals(progress))
                .findAny()
                .orElse(null);
    }
}