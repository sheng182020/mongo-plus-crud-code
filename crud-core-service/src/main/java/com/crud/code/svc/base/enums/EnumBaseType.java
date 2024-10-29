package com.crud.code.svc.base.enums;

public interface EnumBaseType {

    /**
     * 获取枚举的编码
     *
     * @return 枚举的编码值
     */
    String getCode();

    /**
     * 是否匹配的枚举编码
     *
     * @param code 枚举编码
     * @return 是否匹配
     */
    default boolean isMatched(String code) {
        return getCode().equalsIgnoreCase(code);
    }


}
