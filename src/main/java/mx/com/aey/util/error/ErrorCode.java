package mx.com.aey.util.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    NOT_FOUND("Resource not found", 404),;

    private final String description;
    private final Integer code;
    ErrorCode(String description, Integer code) {
        this.description = description;
        this.code = code;
    }
}
