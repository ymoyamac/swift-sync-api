package mx.com.aey.util.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    NOT_FOUND(404, "Resource not found"),;

    private final String description;
    private final Integer statusCode;
    ErrorCode(Integer statusCode, String description) {
        this.description = description;
        this.statusCode = statusCode;
    }
}
