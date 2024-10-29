package com.crud.code.svc.base.handler;

import java.io.IOException;
import java.lang.reflect.Method;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.crud.code.svc.base.enums.EnumBaseType;

public class EnumDeserializerHandler<T extends EnumBaseType> extends JsonDeserializer<T> implements ContextualDeserializer {


    private Class<?> currentClass;

    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String text = jsonParser.getText();
        // 获取正在反序列化的类
        if (currentClass != null) {
            return get(text);
        }
        return null;
    }

    public T get(String text) throws IOException{
        // 通过反射获取枚举的 values() 方法
        try {
            // 获取 values() 方法
            Method valuesMethod = currentClass.getMethod("values");

            // 调用 values() 方法获取所有枚举常量
            @SuppressWarnings("unchecked")
            T[] enumValues = (T[]) valuesMethod.invoke(null);

            // 遍历枚举值，找到匹配的值
            for (T enumValue : enumValues) {
                if (enumValue.getCode().equalsIgnoreCase(text)) {
                    return enumValue; // 返回匹配的枚举值
                }
            }
            return null;
            // throw new IllegalArgumentException("No enum constant for value: " + text);
        } catch (Exception e) {
            throw new IOException("无法反序列化枚举: " + e.getMessage(), e);
        }
    }

    // 用于获取当前上下文的类型信息
    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        if (property != null) {
            // 获取当前字段所属的类
            JavaType javaType = property.getType();
            this.currentClass = javaType.getRawClass();
        }
        return this;
    }

}
