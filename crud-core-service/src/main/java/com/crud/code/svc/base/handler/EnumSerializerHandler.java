package com.crud.code.svc.base.handler;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.crud.code.svc.base.enums.EnumBaseType;

import java.io.IOException;

public class EnumSerializerHandler<T extends EnumBaseType> extends JsonSerializer<T> {

    @Override
    public void serialize(T t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(t.getCode());
    }
}
