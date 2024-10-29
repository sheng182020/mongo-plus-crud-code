package com.crud.code.tool.generation;

import com.crud.code.tool.config.ColumnInfo;
import com.crud.code.tool.config.GenerateCode;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CommonCodeGenerator {

    public static Map<String, String> remark = new HashMap<>();

    public static void initRemark() {
        Map<String, String> map = new HashMap<>();
        map.put("author", "Jansen");
        map.put("company", "GT");
        map.put("Time", new Date().toString());
        remark = map;
    }

    public static String getRemark() {
        StringBuffer sb = new StringBuffer("");
        for (Map.Entry<String, String> entry : remark.entrySet()) {
            sb.append("	 * @").append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    public static String getImportPageCode(GenerateCode code) {
        return Boolean.TRUE.equals(code.getOptionConfig().getIsHasPage())
                ? "import com.anwen.mongo.model.PageResult;\nimport " + code.getJavaPackage() + "." + code.getModelName() + ".response." + code.getObjectName() + "ItemResponse;\n" : "";
    }

    public static String getImportListCode(GenerateCode code) {
        return Boolean.TRUE.equals(code.getOptionConfig().getIsHasList()) ? "import java.util.List;\n" : "";
    }

    public static String getImportEnumFormatSerializerHandler(GenerateCode code) {
        return Boolean.TRUE.equals(code.getOptionConfig().getIsHasEnum()) ? "import com.fasterxml.jackson.databind.annotation.JsonSerialize;\nimport " + code.getJavaPackage() + ".base.handler.EnumSerializerHandler;\n" : "";
    }

    public static String getImportEnumFormatDeserializerHandler(GenerateCode code) {
        return Boolean.TRUE.equals(code.getOptionConfig().getIsHasEnum()) ? "import com.fasterxml.jackson.databind.annotation.JsonDeserialize;\nimport " + code.getJavaPackage() + ".base.handler.EnumDeserializerHandler;\n" : "";
    }

    public static String addEnumFormatDeserializerHandler(ColumnInfo col) {
        return Boolean.TRUE.equals(col.getIsEnum()) ? "    @JsonDeserialize(using = EnumFormatDeserializerHandler.class)\n" : "";
    }

    public static String addEnumFormatSerializerHandler(ColumnInfo col) {
        return Boolean.TRUE.equals(col.getIsEnum()) ? "    @JsonSerialize(using = EnumFormatSerializerHandler.class)\n" : "";
    }

    public static String getMaxValue(Object max, String fieldType) {
        if (max != null) {
            if ("Long".equalsIgnoreCase(fieldType)
                    || "Integer".equalsIgnoreCase(fieldType)
                    || "BigInteger".equalsIgnoreCase(fieldType)
            ) {
                return "    @Max(value = " + max + "L)\n";
            } else if ("Float".equalsIgnoreCase(fieldType)
                    || "Double".equalsIgnoreCase(fieldType)
                    || "BigDecimal".equalsIgnoreCase(fieldType)
            ) {
                return "    @DecimalMax(value = \"" + max + "\")\n";
            }
        }
        return "";
    }

    public static String getMinValue(Object min, String fieldType) {
        if (min != null) {
            if ("Long".equalsIgnoreCase(fieldType)
                    || "Integer".equalsIgnoreCase(fieldType)
                    || "BigInteger".equalsIgnoreCase(fieldType)
            ) {
                return "    @Min(value = " + min + "L)\n";
            } else if ("Float".equalsIgnoreCase(fieldType)
                    || "Double".equalsIgnoreCase(fieldType)
                    || "BigDecimal".equalsIgnoreCase(fieldType)
            ) {
                return "    @DecimalMin(value = \"" + min + "\")\n";
            }
        }
        return "";
    }

    public static String getDefaultValue(ColumnInfo col) {
        if (col.getDefaultValue() != null) {
            String pre = " = ";
            if ("Double".equalsIgnoreCase(col.getJavaClassName())) {
                return pre + col.getDefaultValue() + "D";
            } else if ("Float".equalsIgnoreCase(col.getJavaClassName())) {
                return pre + col.getDefaultValue() + "F";
            } else if ("BigDecimal".equalsIgnoreCase(col.getJavaClassName())) {
                return pre + "new BigDecimal(" + col.getDefaultValue() + ")";
            } else if ("Boolean".equalsIgnoreCase(col.getJavaClassName())) {
                return pre + col.getDefaultValue();
            } else if ("Long".equalsIgnoreCase(col.getJavaClassName())) {
                return pre + col.getDefaultValue() + "L";
            } else if ("Integer".equalsIgnoreCase(col.getJavaClassName())) {
                return pre + col.getDefaultValue();
            } else if ("Date".equalsIgnoreCase(col.getJavaClassName())) {
                return pre + "new Date(" + col.getDefaultValue() + ")";
            } else if ("String".equalsIgnoreCase(col.getJavaClassName())) {
                return pre + "\"" + col.getDefaultValue() + "\"";
            } else if (col.getIsEnum()) {
                return pre + col.newJavaClassName() + ".valueOf(\"" + col.getDefaultValue() + "\")";
            } else if ("Object".equalsIgnoreCase(col.getJavaClassName())) {
                System.out.println(col.getColumnName() + " : \n The object type data is not empty, but it refuses to be generated.");
            }
        }
        return "";
    }

    // public static String convertEnumClassName(String fieldName) {
    //     if (StrUtil.isBlank(fieldName)) {
    //         throw new RuntimeException("fieldName is not null");
    //     }
    //     if (fieldName.endsWith("enum")
    //             || fieldName.endsWith("enums")
    //             || fieldName.endsWith("Enums")
    //             || fieldName.endsWith("Enum")) {
    //         return Tools.toHump(fieldName);
    //     } else {
    //         return Tools.toHump(fieldName) + "Enum";
    //     }
    // }
}
