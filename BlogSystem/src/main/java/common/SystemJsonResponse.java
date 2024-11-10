package common;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Optional;



public class SystemJsonResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonInclude
    private int code;

    @JsonInclude
    private String message;

    @JsonInclude
    private T data;

    private SystemJsonResponse(int code, String msg, T data) {
        this.code = code;
        Optional.ofNullable(msg).ifPresent(m -> this.message = m);
        Optional.ofNullable(data).ifPresent(d -> this.data = d);
    }

    private SystemJsonResponse(int code, String msg) {
        this(code, msg, null);
    }


    public static SystemJsonResponse<?> SYSTEM_SUCCESS() {
        return new SystemJsonResponse<>(200, "成功");
    }

    public static <T> SystemJsonResponse<T> SYSTEM_SUCCESS(T data) {
        return new SystemJsonResponse<T>(200, "成功", data);
    }

    public static SystemJsonResponse<?> SYSTEM_FAIL(String message) {
        return new SystemJsonResponse<>(-200, message);
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
