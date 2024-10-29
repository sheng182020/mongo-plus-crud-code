package com.crud.code.tool.generation;

import cn.hutool.core.util.StrUtil;
import com.crud.code.tool.config.BaseEntityColumns;
import com.crud.code.tool.config.ColumnInfo;
import com.crud.code.tool.config.GenerateCode;

import java.util.*;

public class EntityGenerator {

    public static void cal(GenerateCode code) {

        String className = code.getObjectName();

        Map<String, List<String>> importEnum = new HashMap<>();
        Set<String> importClass = new HashSet<>();

        for (ColumnInfo col : code.getColumns()) { // 把需要声明引入的类找出来
            if (!col.getFieldExists()) {
                continue;
            }
            if (col.getIsEnum()) {
                importEnum.put(col.newJavaClassName(), col.getEnumList());
            }
            if (StrUtil.isNotBlank(col.getJavaPackage())) {
                importClass.add(col.getJavaPackage());
            }
        }
        code.setNewEnums(importEnum);
        List<String> asList = Arrays.asList(importEnum.keySet().toArray(new String[]{}));

        StringBuffer sb = new StringBuffer();

        sb.append("package ").append(code.getJavaPackage()).append(".").append(code.getModelName()).append(".entity;\n")
                .append("\n");

        for (String importString : asList) { // 把需要声明引入的类找出来
            sb.append("import ").append(code.getJavaPackage()).append(".").append(code.getModelName()).append(".enums.").append(importString).append(";\n");
        }
        for (String importString : importClass) {
            sb.append("import ").append(importString).append(";\n");
        }

        sb.append("\n")

                .append("import com.anwen.mongo.annotation.ID;\n")
                // .append("import com.anwen.mongo.enums.FieldFill;\n")
                .append("import com.anwen.mongo.annotation.collection.CollectionField;\n")
                .append("import com.anwen.mongo.annotation.collection.CollectionName;\n")
                .append("import ").append(code.getJavaPackage()).append(".base.entity.BaseEntity;\n")
                .append(objectIdTypeHandleCode(code.getJavaPackage(), code.getOptionConfig().getIsHasObjectId()))
                // .append(enumTypeHandleCode(code.getJavaPackage(), code.getOptionConfig().getIsHasEnum()))
                .append("import lombok.Data;\n")
                .append("import lombok.AllArgsConstructor;\n")
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
                .append("@CollectionName(\"").append(code.getTable().getTableName()).append("\")\n")
                .append("public class ").append(className).append("Entity extends BaseEntity implements Serializable {\n")
                .append("	\n")
                .append("	/**\n")
                .append("	 * 主键ID\n")
                .append("	 */\n")
                .append("	@ID\n")
                .append("	@CollectionField(\"_id\")\n")
                .append("	private String id;\n\n")
        ;
//				.append("	\n");

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
            if (col.getColumnName().equalsIgnoreCase("project")) {
                System.out.println("test");
            }
            addCollectionField(sb, col.getDbFieldName(), col.isKey());
            sb.append("    private ").append(col.newJavaClassName()).append(" ").append(col.getJavaColumnNameLowwer()).append(";\n\n");
        }
        // addBaseCol(sb); //添加基础字段
        sb.append("}\n");

//		t.log(sb.toString());
        code.setImportClass(importClass);
        code.setEntityCode(sb.toString());
        code.setEntityCodeFileName(className + "Entity.java");
    }


    private static String objectIdTypeHandleCode(String javaClassPath, Boolean isObjectId) {
        return Boolean.TRUE.equals(isObjectId) ? "import " + javaClassPath + ".base.handler.ObjectIdTypeHandler;\n" : "";
    }

    private static String enumTypeHandleCode(String javaClassPath, Boolean hasEnum) {
        return Boolean.TRUE.equals(hasEnum) ? "import " + javaClassPath + ".base.handler.EnumTypeHandler;\n" : "";
    }

    private static void addCollectionField(StringBuffer sb, String dbFieldName, Boolean isKey) {

        if (StrUtil.isBlank(dbFieldName) && !isKey) {
            return;
        }
        if (StrUtil.isNotBlank(dbFieldName)) {
            sb.append("    @CollectionField(value = \"").append(dbFieldName).append("\"");
            if (isKey) {
                sb.append(", typeHandler = ObjectIdTypeHandler.class");
            }
            sb.append(")\n");
        } else {
            sb.append("    @CollectionField(typeHandler = ObjectIdTypeHandler.class)\n");
        }
    }

    private static void addCollectionField(StringBuffer sb, String dbFieldName, Boolean isKey, Boolean isEnum) {

        // Validate that isEnum and isKey are mutually exclusive
        if (isEnum != null && isKey != null && isEnum && isKey) {
            throw new IllegalArgumentException("isEnum and isKey cannot both be true.");
        }

        if (StrUtil.isBlank(dbFieldName) && !isKey && !isEnum) {
            return;
        }

        if (StrUtil.isNotBlank(dbFieldName)) {
            sb.append("    @CollectionField(value = \"").append(dbFieldName).append("\"");
            if (isEnum != null && isEnum) {
                sb.append(", typeHandler = EnumTypeHandler.class");
            } else if (isKey != null && isKey) {
                sb.append(", typeHandler = ObjectIdTypeHandler.class");
            }
            sb.append(")\n");
        } else {
            if (isKey != null && isKey) {
                sb.append("    @CollectionField(typeHandler = ObjectIdTypeHandler.class)\n");
            } else if (isEnum != null && isEnum) {
                sb.append("    @CollectionField(typeHandler = EnumTypeHandler.class)\n");
            }
        }
    }

    private static void addBaseCol(StringBuffer sb) {
        for (BaseEntityColumns value : BaseEntityColumns.values()) {
            if (StrUtil.isNotBlank(value.getRemark())) {
                sb.append("    ").append(value.getRemark()).append("\n");
            }
            sb.append("    private ").append(value.getType()).append(" ").append(value.getName()).append(";\n\n");
        }
    }

}
