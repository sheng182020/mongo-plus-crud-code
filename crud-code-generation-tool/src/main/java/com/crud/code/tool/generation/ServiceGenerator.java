package com.crud.code.tool.generation;

import com.crud.code.tool.config.GenerateCode;
import com.crud.code.tool.utils.Tools;

public class ServiceGenerator {

    public static void cal(GenerateCode code) {

        StringBuffer sb = new StringBuffer();

        String className = code.getObjectName();

        sb.append("package ").append(code.getJavaPackage()).append(".").append(code.getModelName()).append(".service;\n")
                .append("\n")
                .append("import com.anwen.mongo.service.IService;\n")
                .append(CommonCodeGenerator.getImportPageCode(code))
                .append("import ").append(code.getJavaPackage()).append(".").append(code.getModelName()).append(".request.*;\n")
                .append("import ").append(code.getJavaPackage()).append(".").append(code.getModelName()).append(".response.").append(className).append("Response").append(";\n")
                // .append("import ").append(code.getJavaPackage()).append(".").append(code.getModelName()).append(".response.").append(className).append("ItemResponse").append(";\n")
                .append("import ").append(code.getJavaPackage()).append(".").append(code.getModelName()).append(".entity.").append(className).append("Entity").append(";\n")
                .append(CommonCodeGenerator.getImportListCode(code))
                .append("\n")
                .append("/**\n")
                .append(" * ").append(code.getTable().getTableComment()).append("\n")
                .append(" *\n")
                .append(CommonCodeGenerator.getRemark())
                .append(" */\n")
                .append("public interface ").append(className).append("Service extends IService<").append(className).append("Entity> {\n")
                .append("\n");

        _calCreate(sb, className);
        _calUpdate(sb, className);
        _calSearchPage(sb, className);
        _calSearchList(sb, className);
        _calGet(sb, className);
        _calDelete(sb, className);
        _calBatchDelete(sb, className);

        sb.append("\n")
                .append("}\n")
                .append("");

        Tools.log(sb.toString());

        code.setServiceCode(sb.toString());
        code.setServiceCodeFileName(className + "Service.java");
    }

    private static void _calBatchDelete(StringBuffer sb, String className) {

        String op = "批量删除";

        sb.append("\n");
        sb.append("	/**\n")
                .append("	 * ").append(op).append("\n")
                .append("	 *\n")
                .append(CommonCodeGenerator.getRemark())
                .append("	 */\n")
                .append("	void delete(").append(className).append("DeleteRequest request);\n");
    }

    private static void _calDelete(StringBuffer sb, String className) {

        String op = "删除一条";

        sb.append("\n");
        sb.append("	/**\n")
                .append("	 * ").append(op).append("\n")
                .append("	 *\n")
                .append(CommonCodeGenerator.getRemark())
                .append("	 */\n")
                .append("	void delete(String id);\n");
    }

    private static void _calGet(StringBuffer sb, String className) {

        String op = "查询一条";

        sb.append("\n");
        sb.append("	/**\n")
                .append("	 * ").append(op).append("\n")
                .append("	 *\n")
                .append(CommonCodeGenerator.getRemark())
                .append("	 */\n")
                .append("	").append(className).append("Response get(String id);\n");
    }

    private static void _calSearchList(StringBuffer sb, String className) {

        String op = "全部查询";

        sb.append("\n");
        sb.append("	/**\n")
                .append("	 * ").append(op).append("\n")
                .append("	 *\n")
                .append(CommonCodeGenerator.getRemark())
                .append("	 */\n")
                .append("	List<").append(className).append("Response> search(").append(className).append("SearchRequest request);\n");
    }

    private static void _calSearchPage(StringBuffer sb, String className) {

        String op = "分页查询";

        sb.append("\n");
        sb.append("	/**\n")
                .append("	 * ").append(op).append("\n")
                .append("	 *\n")
                .append(CommonCodeGenerator.getRemark())
                .append("	 */\n")
                .append("	PageResult<").append(className).append("ItemResponse> search(").append(className).append("SearchPageRequest request);\n");
    }

    private static void _calUpdate(StringBuffer sb, String className) {

        String op = "更新";

        sb.append("\n");
        sb.append("	/**\n")
                .append("	 * ").append(op).append("\n")
                .append("	 *\n")
                .append(CommonCodeGenerator.getRemark())
                .append("	 */\n")
                .append("	").append(className).append("Response update(").append(className).append("UpdateRequest request);\n");
    }

    private static void _calCreate(StringBuffer sb, String className) {

        String op = "新增";

        sb.append("\n");
        sb.append("	/**\n")
                .append("	 * ").append(op).append("\n")
                .append("	 *\n")
                .append(CommonCodeGenerator.getRemark())
                .append("	 */\n")
                .append("	").append(className).append("Response create(").append(className).append("CreateRequest request);\n");
    }

}
