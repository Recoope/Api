package recoope.api.domain;

import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class RespostaApi<T> {
    private int statusCode = 200;
    public String message;
    public T data;
    private final LocalDate executedAt = dataAtual();

    public RespostaApi(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public RespostaApi(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public RespostaApi(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = null;
    }

    public RespostaApi(List data) {
        this.message = data.size() + " resultado(s) encontrados";
        this.data = (T) data;
    }

    public ResponseEntity<RespostaApi> get() {
        return ResponseEntity.status(statusCode).body(RespostaApi.this);
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

