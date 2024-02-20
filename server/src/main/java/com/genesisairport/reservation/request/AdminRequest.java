package com.genesisairport.reservation.request;

import com.genesisairport.reservation.common.ProgressStage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AdminRequest {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StageInfo {
        private Long reservationId;
        private ProgressStage progress;
        private String detail;
    }
}
