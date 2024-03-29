package com.genesisairport.reservation.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.genesisairport.reservation.common.exception.GeneralException;
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
    COMPLETED("완료"),
    CANCELED("취소");

    private final String name;

    @JsonCreator
    public static ProgressStage of(String progress) {
        return Arrays.stream(ProgressStage.values())
                .filter(i -> i.name.equals(progress))
                .findAny()
                .orElse(null);
    }

    public static ProgressStage fromName(String name) {
        for (ProgressStage stage : ProgressStage.values()) {
            if (stage.name.equals(name)) {
                return stage;
            }
        }
        throw new GeneralException(ResponseCode.INTERNAL_SERVER_ERROR, "유효하지 않은 stage name 입니다. : " + name);
    }
}
