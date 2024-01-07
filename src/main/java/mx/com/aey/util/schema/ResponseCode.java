package mx.com.aey.util.schema;

import lombok.Getter;

@Getter
public enum ResponseCode {
    CREATED("Resource was successfully created"),
    DELETED("Resource was successfully deleted"),
    ;

    private final String description;
    ResponseCode(String description) {
        this.description = description;
    }
}