package com.crud.code.svc.base.response;

import com.crud.code.svc.base.enums.ResponseStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BaseResponse<T> {

    private String code;

    private T data;

    private String message;

    public static <T> BaseResponse<T> OK(T data){
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(ResponseStatusEnum.SUCCESS.getCode());
        response.setData(data);
        return response;
    }

    public static <T> BaseResponse<T> OK(){
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(ResponseStatusEnum.SUCCESS.getCode());
        return response;
    }
}
