package recoope.api.domain;


import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class ApiResponse<T> {
    private int statusCode = 200;
    public String message;
    public T data;
    private final LocalDate executedAt = dataAtual();

    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public ApiResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = null;
    }

    public ApiResponse(List data) {
        this.message = data.size() + " resultado(s) encontrados";
        this.data = (T) data;
    }

    public ResponseEntity<ApiResponse> get() {
        return ResponseEntity.status(statusCode).body(ApiResponse.this);
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public LocalDate getExecutedAt() {
        return executedAt;
    }


    private static LocalDate dataAtual() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("GMT-03:00"));
        return zonedDateTime.toLocalDate();
    }
}

