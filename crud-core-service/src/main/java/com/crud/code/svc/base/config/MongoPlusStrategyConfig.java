package com.crud.code.svc.base.config;

import com.anwen.mongo.cache.global.ConversionCache;
import com.anwen.mongo.mapping.MongoConverter;
import com.anwen.mongo.strategy.conversion.ConversionStrategy;
import com.crud.code.svc.base.enums.EnumBaseType;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.lang.reflect.Method;

@Configuration
public class MongoPlusStrategyConfig {

    @PostConstruct
    public void initConfig() {
        ConversionCache.getConversionStrategy(Object.class);
        ConversionCache.putConversionStrategy(Enum.class, new EnumBaseTypeConversionStrategy());
    }

    protected class EnumBaseTypeConversionStrategy<T> implements ConversionStrategy<Enum> {

        @Override
        @SuppressWarnings({"unchecked","rawtypes"})
        public Enum convertValue(Object fieldValue, Class<?> fieldType, MongoConverter mongoConverter) throws IllegalAccessException {
            // 检查fieldType是否为Enum类型
            if (!EnumBaseType.class.isAssignableFrom(fieldType)) {
                throw new IllegalArgumentException("Field type must be an enum implementing EnumBaseType.");
            }
            // 转换fieldType为Enum类型
            Class<? extends Enum> enumType = (Class<? extends Enum>) fieldType;

            // 使用反射调用get方法，通过枚举的code值获取对应的枚举实例
            try {
                return (Enum) get(enumType, (String) fieldValue);
            } catch (IOException e) {
                throw new IllegalArgumentException("Error while converting value", e);
            }
        }

        public <T extends EnumBaseType> T get(Class<?> enumClass, String text) throws IOException {
            // 使用反射获取枚举类的values()方法
            try {
                Method valuesMethod = enumClass.getMethod("values");

                // 调用 values() 方法获取所有枚举常量
                @SuppressWarnings("unchecked")
                T[] enumValues = (T[]) valuesMethod.invoke(null);

                // 遍历枚举值，找到匹配的值
                for (T enumValue : enumValues) {
                    if (enumValue.isMatched(text)) {
                        return enumValue; // 返回匹配的枚举值
                    }
                }

                // 如果找不到匹配的枚举值，则抛出异常或返回null
                throw new IllegalArgumentException("No enum constant matches code: " + text);
            } catch (Exception e) {
                throw new IOException("无法反序列化枚举: " + e.getMessage(), e);
            }
        }
    }

}
