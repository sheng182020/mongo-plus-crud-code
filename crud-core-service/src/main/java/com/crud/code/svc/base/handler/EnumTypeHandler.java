package com.crud.code.svc.base.handler;

import com.anwen.mongo.handlers.TypeHandler;
import com.anwen.mongo.toolkit.StringUtils;
import com.crud.code.svc.base.enums.EnumBaseType;

public class EnumTypeHandler<T extends EnumBaseType> implements TypeHandler<T> {

    @Override
    public Object setParameter(String fieldName, T enumBaseType) {
        if (enumBaseType != null) {
            return enumBaseType.getCode();
        }
        return null;
    }

    @Override
    public T getResult(Object value) {
        // if (value instanceof String && StringUtils.isNotBlank(String.valueOf(value))) {
        //     String code = (String) value;
        // }
        return null;
    }
}
