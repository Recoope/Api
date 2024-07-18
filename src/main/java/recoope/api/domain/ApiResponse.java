package recoope.api.domain;


import java.util.*;

public class ApiResponse<T> {
    public String message;
    public T data;
    private Date executed;

    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
        this.executed = dataAtual();
    }

    public ApiResponse(String message) {
        this.message = message;
        this.data = null;
        this.executed = dataAtual();
    }

    public ApiResponse(List<> data) {
        this.message = list.size() + " resultados encontrados";
        this.data = list;

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

    public Date getExecuted() {
        return executed;
    }

    public void setExecuted(Date executed) {
        this.executed = executed;
    }

    private Date dataAtual() {
        Calendar date = new GregorianCalendar();
        date.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

        return date.getTime();
    }
}

