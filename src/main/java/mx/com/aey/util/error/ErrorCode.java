package mx.com.aey.util.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    ERROR(500, "Oops... Something went wrong, check server logs"),
    ERROR_TO_CREATE(500, "Oops... Something went wrong, resource could not be created"),
    BAD_REQUEST(400, "Invalid request format. Please check the request body"),
    UNIQUENESS_RULE(400, "Insertion error. This element already exists"),
    NOT_NULL_VALUE(400, "Value cannot be null. Please provide a valid value for the required field"),
    NOT_BLANK_VALUE(400, "Value cannot be black. Please provide a valid value for the required field"),
    UNAUTHORIZED(401, "Not valid credentials, check email or password"),
    NOT_FOUND(404, "Resource not found"),
    RESOURCE_NOT_AVAILABLE(404, "Resource not available"),
    INTERNATIONAL_SERVER_ERROR(500, "Internal Server Error. An unexpected error occurred while processing the request. Please try again later or contact support"),
    ;
    private final String description;
    private final Integer statusCode;
    ErrorCode(Integer statusCode, String description) {
        this.description = description;
        this.statusCode = statusCode;
    }
}
