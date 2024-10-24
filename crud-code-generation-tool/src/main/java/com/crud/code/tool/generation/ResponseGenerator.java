package com.crud.code.tool.generation;

import cn.hutool.core.util.StrUtil;
import com.crud.code.tool.config.BaseEntityColumns;
import com.crud.code.tool.config.ColumnInfo;
import com.crud.code.tool.config.GenerateCode;

import java.util.*;

public class ResponseGenerator {

    public static void cal(GenerateCode code) {

        String className = code.getObjectName();

        Map<String, List<String>> importEnum = code.getNewEnums();
        Set<String> importClass = new HashSet<>();

        for (ColumnInfo col : code.getColumns()) { // 把需要声明引入的类找出来
            if (!col.getFieldExists()) {
                continue;
            }
            if (StrUtil.isNotBlank(col.getJavaPackage())) {
                importClass.add(col.getJavaPackage());
            }
        }
        List<String> asList = Arrays.asList(importEnum.keySet().toArray(new String[]{}));

        StringBuffer sb = new StringBuffer();

        sb.append("package ").append(code.getJavaPackage()).append(".").append(code.getModelName()).append(".response;\n")
                .append("\n")
                .append("import ").append(code.getJavaPackage()).append(".").append(code.getModelName()).append(".entity.").append(className).append("Entity;\n");

        for (String importString : asList) { // 把需要声明引入的类找出来
            sb.append("import ").append(code.getJavaPackage()).append(".").append(code.getModelName()).append(".enums.").append(importString).append(";\n");
        }
        for (String importString : importClass) {
            sb.append("import ").append(importString).append(";\n");
        }

        sb.append("\n")
                .append("import cn.hutool.json.JSONUtil;\n")
                .append("import lombok.AllArgsConstructor;\n")
                .append("import lombok.Data;\n")
                .append("import lombok.NoArgsConstructor;\n")


                .append("import java.io.Serializable;\n")

                .append("\n")
                .append("/**\n")
                .append(" * ").append(code.getTable().getTableComment()).append("\n")
                .append(" *\n")
                .append(" */\n")
                .append("@Data\n")
                .append("@AllArgsConstructor\n")
                .append("@NoArgsConstructor\n")
                .append("public class ").append(className).append("Response {\n")
                .append("\n")
                .append("    private String id;\n\n")
        ;

        for (ColumnInfo col : code.getColumns()) {
            if (!col.getFieldExists()) {
                continue;
            }
            if ("_id".equalsIgnoreCase(col.getColumnName()) || "id".equalsIgnoreCase(col.getColumnName())) {
                continue;
            }
            // 如果等于基础字段，重名也要continue,不再生成
            if (BaseEntityColumns.isMetaDataColumn(col)) {
                continue;
            }
            if (StrUtil.isNotBlank(col.getColumnComment())) {
                sb.append("	/**\n")
                        .append("	 *  ").append(col.getColumnComment()).append("\n")
                        .append("    */\n");
            }
            sb.append("    private ").append(col.newJavaClassName()).append(" ").append(col.getJavaColumnNameLowwer()).append(";\n\n");
        }

        sb.append("    public static ").append(className).append("Response toResponse(").append(className).append("Entity entity) {\n")
                .append("        if (entity == null) {\n            return null;\n        }\n")
                .append("        String json = JSONUtil.toJsonStr(entity);\n").append("        return JSONUtil.toBean(json, ")
                .append(className).append("Response.class);\n").append("    }\n");
        sb.append("}\n");

//		t.log(sb.toString());

        code.setResponseCode(sb.toString());
        code.setResponseCodeFileName(className + "Response.java");
    }
}
