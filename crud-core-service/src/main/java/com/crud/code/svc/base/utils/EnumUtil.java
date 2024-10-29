package com.crud.code.svc.base.utils;

import cn.hutool.core.util.StrUtil;
import com.crud.code.svc.base.enums.EnumBaseType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumUtil {
    /**
     * 将编码解析为指定的枚举类型
     *
     * @param code      枚举的编码
     * @param enumClass 枚举的类型
     * @param <T>       枚举类型
     * @return
     */
    public static <T extends EnumBaseType> T parseEnum(String code, Class<T> enumClass) {
        if (StrUtil.isBlank(code) || null == enumClass) {
            return null;
        }

        // 通过反射取出Enum所有常量的属性值
        for (T each : enumClass.getEnumConstants()) {
            // 利用code进行循环比较，获取对应的枚举
            if (StrUtil.equalsIgnoreCase(code, each.getCode())) {
                return each;
            }
        }
        return null;
    }

    /**
     * 获取枚举所有的编码
     *
     * @param enumClass 枚举的类型
     * @param <T>       枚举类型
     * @return
     */
    public static <T extends EnumBaseType> List<String> getAllCodeList(Class<T> enumClass) {
        List<String> results = new ArrayList<>();
        if (enumClass != null) {
            results = Arrays.stream(enumClass.getEnumConstants())
                    .map(T::getCode)
                    .collect(Collectors.toList());
        }
        return results;
    }

}



