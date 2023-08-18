package com.example.skyengtest.excepsion;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ApiError {

    private final String status;
    private final String message;
    private final String reason;
    private final LocalDateTime time;


    public ApiError(String status, String message, String reason, LocalDateTime time) {
        this.status = status;
        this.message = message;
        this.reason = reason;
        this.time = time;
    }
}
