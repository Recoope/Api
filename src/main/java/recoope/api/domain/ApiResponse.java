package recoope.api.domain;


import java.util.*;

public class ApiResponse<T> {
    public String message;
    public T data;
    private Date executedAt = dataAtual();

    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
        this.executedAt = dataAtual();
    }

    public ApiResponse(String message) {
        this.message = message;
        this.data = null;
    }

    public ApiResponse(List data) {
        this.message = data.size() + " resultado(s) encontrados";
        this.data = (T) data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Date getExecutedAt() {
        return executedAt;
    }


    private Date dataAtual() {
        Calendar date = new GregorianCalendar();
        date.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

        return date.getTime();
    }
}

