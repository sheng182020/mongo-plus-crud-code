package com.crud.code.svc.base.exception;

import com.crud.code.svc.base.enums.ResponseStatusEnum;
import lombok.Getter;

@Getter
public class BaseApiException extends RuntimeException {

    protected ErrorCode errorCode;


    public BaseApiException() {
        super(ResponseStatusEnum.INTERNAL_SERVER_ERROR.getMessage());
    }

    public BaseApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BaseApiException(String message) {
        super(message);
    }

    public BaseApiException(Throwable cause) {
        super(cause);
    }

    public BaseApiException(String message, Throwable cause) {
        super(message, cause);
    }

}
