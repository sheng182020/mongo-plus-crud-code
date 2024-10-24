package com.crud.code.tool.generation;

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
                ? "import com.anwen.mongo.model.PageResult;\nimport " + code.getJavaPackage()+"." + code.getModelName() + ".response." + code.getObjectName() + "ItemResponse;\n" : "";
    }

    public static String getImportListCode(GenerateCode code) {
        return Boolean.TRUE.equals(code.getOptionConfig().getIsHasList()) ? "import java.util.List;\n" : "";
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


    public static void main(String[] args) {
        String reslut = getRemark();
        System.out.println(reslut);
    }
}
