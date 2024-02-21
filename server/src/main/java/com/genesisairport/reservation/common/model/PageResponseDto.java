package com.genesisairport.reservation.common.model;

import com.genesisairport.reservation.common.enums.ResponseCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class PageResponseDto<T> extends ResponseDto {
    private final PageInfo pageInfo;
    private final T data;

    private PageResponseDto(T data, PageInfo pageInfo) {
        super(true, ResponseCode.OK.getCode(), ResponseCode.OK.getMessage());
        this.pageInfo = pageInfo;
        this.data = data;
    }

    private PageResponseDto(T data, PageInfo pageInfo, String message) {
        super(true, ResponseCode.OK.getCode(), message);
        this.pageInfo = pageInfo;
        this.data = data;
    }

    public static <T> PageResponseDto<T> of(T data, PageInfo pageInfo) {
        return new PageResponseDto<>(data, pageInfo);
    }

    public static <T> PageResponseDto<T> of(T data, PageInfo pageInfo, String message) {
        return new PageResponseDto<>(data, pageInfo, message);
    }

    public static <T> PageResponseDto<T> empty() {
        return new PageResponseDto<>(null, null);
    }
}
