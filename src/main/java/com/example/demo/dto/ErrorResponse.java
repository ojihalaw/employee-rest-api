package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private boolean success;

    private String message;

    private int status;

    private LocalDateTime timestamp;

    public static ErrorResponse of(String message, int status){
        return new ErrorResponse(
                false,
                message,
                status,
                LocalDateTime.now()
        );
    }
}
