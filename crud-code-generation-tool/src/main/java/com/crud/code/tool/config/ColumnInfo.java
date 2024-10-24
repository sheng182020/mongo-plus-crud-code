package com.crud.code.tool.config;

import com.crud.code.tool.utils.Tools;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ColumnInfo {

    String columnName; // 字段名
    String columnType; // 转换前字段类型
    String columnComment; // 字段备注
    // Long maxLength; //设置最大长度限制
    // Long minLength; //设置最小长度限制
    Object max; // 设置最大值限制
    Object min; // 设置最小值限制
    String dbFieldName; // 如果有值就更新

    Boolean required; // 是否必填
    String javaClassName; // 转换后字段类型
    String javaPackage; // 导入的包路径
    Boolean keyFlag; // 是否主键
    Boolean isEnum; // 是否枚举
    Boolean fieldExists; // 是否需要生成该字段
    // Boolean ObjectExists; //是否model/Object,可以生成该字段的类Class
    Object defaultValue; // 看下是否需要默认值
    List<String> enumList; // 枚举的值

    public boolean isKey() {
        return Boolean.TRUE.equals(this.keyFlag);
    }

    public boolean isNullAble() {
        return Boolean.TRUE.equals(this.required);
    }

    public String getJavaColumnName() {
        return Tools.upperFirst(this.getColumnName());
    }

    public String getJavaColumnNameLowwer() {
        return Tools.lowwerFirst(getJavaColumnName());
    }

    public String newJavaClassName() {
        if (Boolean.TRUE.equals(this.isEnum)) {
            String fieldName = getJavaColumnName();
            if (fieldName.endsWith("enum")
                    || fieldName.endsWith("enums")
                    || fieldName.endsWith("Enums")
                    || fieldName.endsWith("Enum")) {
                return fieldName;
            } else {
                return fieldName + getJavaClassName();
            }
        } else {
            return getJavaClassName();
        }
    }

    public String getter() {
        return "get" + getJavaColumnName();
    }

    public String setter() {
        return "set" + getJavaColumnName();
    }

}
