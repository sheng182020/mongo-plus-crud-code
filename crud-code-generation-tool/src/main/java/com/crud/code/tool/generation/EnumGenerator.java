package com.crud.code.tool.generation;

import cn.hutool.core.collection.CollectionUtil;
import com.crud.code.tool.config.GenerateCode;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EnumGenerator {

    public static void cal(GenerateCode code) {


        Iterator<Map.Entry<String, List<String>>> it = code.getNewEnums().entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, List<String>> col = it.next();

            StringBuffer sb = new StringBuffer();

            sb.append("package ").append(code.getJavaPackage()).append(".").append(code.getModelName()).append(".enums;\n")
                    .append("\n")
                            .append("import ").append(code.getJavaPackage()).append(".base.enums.EnumBaseType;\n")
            ;

            sb.append("\n")
                    .append("import lombok.Getter;\n")
                    .append("\n/**\n")
                    .append(" *")
                    .append(CommonCodeGenerator.getRemark())
                    .append("\n *\n")
                    .append(" */\n")
                    .append("@Getter\n")
                    .append("public enum ").append(col.getKey()).append(" implements EnumBaseType {\n")
                    .append("\n")
            ;

            List<String> colValue = col.getValue();
            if(CollectionUtil.isNotEmpty(colValue)){
                colValue.forEach(v->{
                    sb.append("    ").append(v).append("(\"").append(v).append("\"),\n");
                });
            }
            sb.append("\n")
                    .append("\n")
                    .append("	;\n")
                    .append("\n")
                    .append("	private final String code;\n")

                    .append("\n")
                    .append("	").append(col.getKey()).append("(String code) {\n")
                    .append("		this.code = code;\n")
                    .append("	}\n")
            ;

            sb.append("}\n");

            code.getEnumsCode().put(col.getKey() + ".java", sb.toString());


        }
    }
}
