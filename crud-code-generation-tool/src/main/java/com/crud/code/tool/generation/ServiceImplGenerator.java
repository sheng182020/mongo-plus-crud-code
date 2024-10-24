package com.crud.code.tool.generation;

import com.crud.code.tool.config.ColumnInfo;
import com.crud.code.tool.config.GenerateCode;
import com.crud.code.tool.utils.Tools;

public class ServiceImplGenerator {

    public static void cal(GenerateCode code) {

        StringBuffer sb = new StringBuffer();

        String className = code.getObjectName();

        sb.append("package ").append(code.getJavaPackage()).append(".").append(code.getModelName()).append(".service.impl;\n")
                .append("\n")

                .append("import org.springframework.stereotype.Service;\n")
                .append("import com.anwen.mongo.toolkit.StringUtils;\n")
                .append("import com.anwen.mongo.service.impl.ServiceImpl;\n")
                .append(CommonCodeGenerator.getImportPageCode(code))
                .append(CommonCodeGenerator.getImportListCode(code))
                .append("\n")

                .append("import ").append(code.getJavaPackage()).append(".").append(code.getModelName()).append(".entity.").append(className).append("Entity").append(";\n")
                .append("import ").append(code.getJavaPackage()).append(".").append(code.getModelName()).append(".request.*;\n")

                .append("import ").append(code.getJavaPackage()).append(".").append(code.getModelName()).append(".response.").append(className).append("Response;\n")
                // .append("import ").append(code.getJavaPackage()).append(".").append(code.getModelName()).append(".response.").append(className).append("ItemResponse;\n")
                .append("import ").append(code.getJavaPackage()).append(".").append(code.getModelName()).append(".service.").append(className).append("Service;\n")
                .append("\n")

                .append("\n")
                .append("/**\n")
                .append(" * ").append(code.getTable().getTableComment()).append("\n")
                .append(" *\n")
                .append(CommonCodeGenerator.getRemark())
                .append(" */\n")
                .append("@Service\n")
                .append("public class ")
                .append(className).append("ServiceImpl extends ServiceImpl<").append(className).append("Entity> implements ")
                .append(className).append("Service {\n")
                .append("\n");

        _calCreate(sb, className);
        _calUpdate(sb, className, code);
        _calSearchPage(sb, className, code);
        _calSearchList(sb, className, code);
        _calGet(sb, className);
        _calDelete(sb, className);
        _calBatchDelete(sb, className, code);

        sb.append("\n")
                .append("}\n")
                .append("");

        Tools.log(sb.toString());

        code.setServiceImplCode(sb.toString());
        code.setServiceImplCodeFileName(className + "ServiceImpl.java");
    }

    private static void _calBatchDelete(StringBuffer sb, String className, GenerateCode code) {

        String op = "批量删除";

        sb.append("\n");
        sb.append("	/**\n")
                .append("	 * ").append(op).append("\n")
                .append("	 *\n")
                .append(CommonCodeGenerator.getRemark())
                .append("	 */\n")
                .append("	@Override\n")
                .append("	public void delete(").append(className).append("DeleteRequest request) {\n")
                .append("        this.removeBatchByIds(request.getIds());\n")
                .append("	}\n")
        ;
    }

    private static void _calDelete(StringBuffer sb, String className) {

        String op = "删除一条";

        sb.append("\n");
        sb.append("	/**\n")
                .append("	 * ").append(op).append("\n")
                .append("	 *\n")
                .append(CommonCodeGenerator.getRemark())
                .append("	 */\n")
                .append("	@Override\n")
                .append("	public void delete(String id) {\n")
                .append("\n")
                .append("        this.removeById(id);\n")
                .append("	}\n")
        ;
    }

    private static void _calGet(StringBuffer sb, String className) {

        String op = "查询一条";

        sb.append("\n");
        sb.append("	/**\n")
                .append("	 * ").append(op).append("\n")
                .append("	 *\n")
                .append(CommonCodeGenerator.getRemark())
                .append("    */\n")
                .append("    @Override\n")
                .append("    public ").append(className).append("Response get(String id) {\n")
                .append("        ").append(className).append("Entity entity = this.getById(id);\n")
                .append("        return ").append(className).append("Response.toResponse(entity);\n")
                .append("    }\n")
        ;
    }

    private static void queryConditionCode(StringBuffer sb, String className, GenerateCode code) {

        for (ColumnInfo col : code.getColumns()) {
            if ("Object".equalsIgnoreCase(col.getJavaClassName())){
                continue;
            }
            if ("String".equalsIgnoreCase(col.getJavaClassName())){
                sb.append("                .eq(StringUtils.isNotBlank(request.").append(col.getter()).append("()), ")
                        .append(className).append("Entity::").append(col.getter()).append(", request.").append(col.getter()).append("())\n")
                ;
            }else if("Date".equalsIgnoreCase(col.getJavaClassName())){
                sb.append("                .lte(request.").append(col.getter()).append("Start()!=null, ")
                        .append(className).append("Entity::").append(col.getter()).append("Start, request.").append(col.getter()).append("Start())\n")
                .append("                .gte(request.").append(col.getter()).append("End()!=null, ")
                        .append(className).append("Entity::").append(col.getter()).append("End, request.").append(col.getter()).append("End())\n")
                ;
            }else{
                sb.append("                .eq(request.").append(col.getter()).append("()!=null, ")
                        .append(className).append("Entity::").append(col.getter()).append(", request.").append(col.getter()).append("())\n");
            }
        }
    }

    private static void _calSearchList(StringBuffer sb, String className, GenerateCode code) {

        String op = "全部查询";

        sb.append("\n");
        sb.append("	/**\n")
                .append("	 * ").append(op).append("\n")
                .append("	 *\n")
                .append(CommonCodeGenerator.getRemark())
                .append("	 */\n")
                .append("    @Override\n")
                .append("    public List<").append(className).append("Response> search(").append(className).append("SearchRequest request) {\n")
                .append("\n")
                .append("        List<").append(className).append("Entity> response = this.lambdaQuery()\n");

        queryConditionCode(sb, className, code);

        sb.append("                .list();\n").append("        return response.stream().map(").append(className).append("Response::toResponse).toList();\n")
                .append("    }\n")
        ;
    }

    private static void _calSearchPage(StringBuffer sb, String className, GenerateCode code) {

        String op = "分页查询";

        sb.append("\n");
        sb.append("	/**\n")
                .append("	 * ").append(op).append("\n")
                .append("	 *\n")
                .append(CommonCodeGenerator.getRemark())
                .append("	 */\n")
                .append("    @Override\n")
                .append("    public PageResult<").append(className).append("ItemResponse> search(").append(className).append("SearchPageRequest request) {\n")
                .append("\n")
                .append("        PageResult<").append(className).append("ItemResponse> response = this.lambdaQuery()\n");

        queryConditionCode(sb, className, code);

        sb.append("                .page(request.getPageNum(), request.getPageSize(), ").append(className).append("ItemResponse.class);\n")
                .append("        return response;\n")
                .append("    }\n")
        ;
    }

    private static void _calUpdate(StringBuffer sb, String className, GenerateCode code) {

        String op = "更新";

        sb.append("\n");
        sb.append("	/**\n")
                .append("	 * ").append(op).append("\n")
                .append("	 *\n")
                .append(CommonCodeGenerator.getRemark())
                .append("	 */\n")
                .append("	@Override\n")
                .append("	public ").append(className).append("Response update(").append(className).append("UpdateRequest request) {\n")
                .append("        ").append(className).append("Entity entity = ").append(className).append("UpdateRequest.toEntity(request);\n");
        sb.append("        Boolean updated = this.updateById(entity);\n").append("        if (updated) {\n")
                .append("            return ").append(className).append("Response.toResponse(this.getById(request.getId()));\n")
                .append("        } else { // 对更新失败做处理\n").append("            return null;\n").append("        }\n");
        sb.append("    }\n");
    }

    private static void _calCreate(StringBuffer sb, String className) {

        String op = "新增";

        sb.append("\n");
        sb.append("	/**\n")
                .append("	 * ").append(op).append("\n")
                .append("	 *\n")
                .append(CommonCodeGenerator.getRemark())
                .append("	 */\n")
                .append("	@Override\n")
                .append("	public ").append(className).append("Response create(").append(className).append("CreateRequest request) {\n")
        .append("        ").append(className).append("Entity entity = ").append(className).append("CreateRequest.toEntity(request);\n");
        sb.append("        this.save(entity);\n");
        sb.append("        return ").append(className).append("Response.toResponse(entity);\n");
        sb.append("    }\n");
    }

}
