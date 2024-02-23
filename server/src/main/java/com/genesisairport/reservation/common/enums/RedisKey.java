package com.genesisairport.reservation.common.enums;

public enum RedisKey {

    RESERVATION("reservation"),
    LOGIN_USER("login-user"),
    ;

    private final String key;

    RedisKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
