package com.crud.code.svc.base.exception;

import com.crud.code.svc.base.enums.ResponseStatusEnum;
import com.crud.code.svc.base.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global Exception Handler, used to capture and handle exceptions within the application.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handles custom BaseApiException exceptions.
     *
     * @param e BaseApiException object
     * @return BaseResponse containing the error message
     */
    @ExceptionHandler(value = BaseApiException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse handle(BaseApiException e) {
        log.error("error", e);
        if (e.getErrorCode() != null) {
            return BaseResponse.builder().code(e.getErrorCode().getCode()).message(e.getMessage()).build();
        }
        return BaseResponse.builder().code(ResponseStatusEnum.INTERNAL_SERVER_ERROR.getCode()).message(e.getMessage()).build();
    }

    /**
     * Handles MethodArgumentNotValidException for method argument validation.
     *
     * @param e MethodArgumentNotValidException object
     * @return BaseResponse containing the error message
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse handleValidException(MethodArgumentNotValidException e) {
        log.error("error", e);
        return errorResponse(e);
    }

    /**
     * Handles BindException for data binding exceptions.
     *
     * @param e BindException object
     * @return BaseResponse containing the error message
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse handleValidException(BindException e) {
        log.error("error", e);
        return errorResponse(e);
    }

    /**
     * Extracts error information from BindException and creates a validation error BaseResponse.
     *
     * @param e BindException object
     * @return BaseResponse containing validation error information
     */
    private <T> BaseResponse<T> errorResponse(BindException e) {
        log.error("error", e);
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        return new BaseResponse<T>(ResponseStatusEnum.VALIDATE_FAILED.getCode(), null, message);
    }

    /**
     * Handles default BaseApiException exceptions.
     *
     * @param e object
     * @return ErrorResponse containing the error message
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse handle(Exception e) {
        log.error("error", e);
        return BaseResponse.builder().code(ResponseStatusEnum.INTERNAL_SERVER_ERROR.getCode()).message(e.getMessage()).build();
    }
}
