package com.crud.code.svc.base.enums;

import com.crud.code.svc.base.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum ResponseStatusEnum implements ErrorCode {

    SUCCESS("200","Success"),

    INTERNAL_SERVER_ERROR("500", "Internal Server Error"),
    NOT_FOUND("404", "Not Found"),
    UNAUTHORIZED("401", "Unauthorized"),
    INCORRECT_API_KEY("401", "Api Key Incorrect"),

    VALIDATE_FAILED("400", "Bad Request / Validate Failed"),
    FORBIDDEN("403", "Forbidden"),
    UNPROCESSABLE_ENTITY("422", "Unprocessable Entity"),

    ;
    private final String code;

    private final String message;


    ResponseStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
