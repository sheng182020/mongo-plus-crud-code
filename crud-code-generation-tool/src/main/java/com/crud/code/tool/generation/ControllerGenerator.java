package com.crud.code.tool.generation;

import com.crud.code.tool.config.GenerateCode;
import com.crud.code.tool.utils.Tools;

public class ControllerGenerator {

	public static void cal(GenerateCode code) {
		
		StringBuffer sb = new StringBuffer();
		
		String className = code.getObjectName();
		String javaNameLower=code.getTable().getJavaNameLowwer();
		
		sb.append("package ").append(code.getJavaPackage()).append(".").append(code.getModelName()).append(".controller;\n")
			.append("\n")
			.append(CommonCodeGenerator.getImportPageCode(code))
			.append("import ").append(code.getJavaPackage()).append(".base.response.BaseResponse;\n")
			.append("import ").append(code.getJavaPackage()).append(".").append(code.getModelName()).append(".request.*;\n")
			.append("import ").append(code.getJavaPackage()).append(".").append(code.getModelName()).append(".response.").append(className).append("Response").append(";\n")
			.append("import ").append(code.getJavaPackage()).append(".").append(code.getModelName()).append(".service.").append(className).append("Service").append(";\n")
			
			.append("\n")

			.append("import org.springframework.beans.factory.annotation.Autowired;\n")
			.append("import org.springframework.validation.annotation.Validated;\n")
			.append("import org.springframework.web.bind.annotation.*;\n")
			.append(CommonCodeGenerator.getImportListCode(code))
			.append("\n")
			.append("/**\n")
			.append(" * ").append(code.getTable().getTableComment()).append("\n")
			.append(" *\n")
			.append(" */\n")
			.append("@RestController\n")
			.append("@RequestMapping(\"/api/").append(code.getTable().getControllerURL()).append("\")\n")
			.append("public class ").append(className).append("Controller {\n")
			.append("\n")
			.append("	private final ").append(className).append("Service ").append(javaNameLower).append("Service;\n")
			.append("\n")
			.append("	@Autowired\n")
			.append("	public ").append(className).append("Controller(")
				.append(className).append("Service ").append(javaNameLower).append("Service")
				.append(") {\n")
			.append("		this.").append(javaNameLower).append("Service")
				.append(" = ").append(javaNameLower).append("Service;\n")
			.append("	}\n");
			
		_calCreate(sb ,className,javaNameLower);
		_calUpdate(sb ,className ,javaNameLower);
		_calSearchPage(sb ,className,javaNameLower);
		_calSearchList(sb ,className,javaNameLower);
		_calGet(sb ,className,javaNameLower );
		_calDelete(sb ,className,javaNameLower);
		_calBatchDelete(sb ,className,javaNameLower);
			
		sb.append("\n")
			.append("}\n")
			.append("");
	
		Tools.log(sb.toString());
		
		code.setControllerCode(sb.toString());
		code.setControllerCodeFileName(className+"Controller.java");
	}

	private static void _calBatchDelete(StringBuffer sb, String className,String javaNameLower) {
		
		String op = "批量删除";
		
		sb.append("\n");
		sb.append("	/**\n")
			.append("	 * ").append(op).append("\n")
			.append("	 *\n")
			.append(CommonCodeGenerator.getRemark())
			.append("	 */\n")
			.append("	@DeleteMapping(\"/delete\")\n")
			.append("	private BaseResponse delete(@RequestBody @Validated ").append(className).append("DeleteRequest request) {\n")
			.append("\n")
			.append("		this.").append(javaNameLower).append("Service.delete(request);\n")
			.append("		return BaseResponse.OK();\n")
			.append("	}\n");
	}

	private static void _calDelete(StringBuffer sb, String className,String javaNameLower) {
		
		String op = "删除一条";

		sb.append("\n");
		sb.append("	/**\n")
				.append("	 * ").append(op).append("\n")
				.append("	 *\n")
				.append(CommonCodeGenerator.getRemark())
				.append("	 */\n")
				.append("	@DeleteMapping(\"\")\n")
				.append("	private BaseResponse delete(@RequestParam(name = \"id\") String id) {\n")
				.append("		this.").append(javaNameLower).append("Service.delete(id);\n")
				.append("		return BaseResponse.OK();\n")
				.append("	}\n");
	}

	private static void _calGet(StringBuffer sb, String className,String javaNameLower) {
		
		String op = "查询一条";
		
		sb.append("\n");
		sb.append("	/**\n")
			.append("	 * ").append(op).append("\n")
			.append("	 *\n")
				.append(CommonCodeGenerator.getRemark())
			.append("	 */\n")
			.append("	@GetMapping(\"\")\n")
			.append("	private BaseResponse<").append(className).append("Response> get(@RequestParam(name = \"id\") String id) {\n")
				.append("		").append(className).append("Response response = this.").append(javaNameLower).append("Service.get(id);\n")
				.append("		return BaseResponse.OK(response);\n")
				.append("	}\n");
	}

	private static void _calSearchPage(StringBuffer sb, String className,String javaNameLower) {
		
		String op = "分页查询";
		
		sb.append("\n");
		sb.append("	/**\n")
			.append("	 * ").append(op).append("\n")
			.append("	 *\n")
			.append(CommonCodeGenerator.getRemark())
			.append("	 */\n")
			.append("	@GetMapping(\"/page\")\n")
			.append("	private BaseResponse<PageResult<").append(className).append("ItemResponse>> search(@RequestBody @Validated ").append(className).append("SearchPageRequest request) {\n")
			.append("		PageResult<").append(className).append("ItemResponse> response = this.")
				.append(javaNameLower).append("Service.search(request);\n")
				.append("		return BaseResponse.OK(response);\n")
				.append("	}\n");
		
	}

	private static void _calSearchList(StringBuffer sb, String className,String javaNameLower) {

		String op = "全部查询";

		sb.append("\n");
		sb.append("	/**\n")
				.append("	 * ").append(op).append("\n")
				.append("	 *\n")
				.append(CommonCodeGenerator.getRemark())
				.append("	 */\n")
				.append("	@GetMapping(\"/list\")\n")
				.append("	private BaseResponse<List<").append(className).append("Response>> search(@RequestBody @Validated ").append(className).append("SearchRequest request) {\n")
				.append("		List<").append(className).append("Response> response = this.")
				.append(javaNameLower).append("Service.search(request);\n")
				.append("		return BaseResponse.OK(response);\n")
				.append("	}\n");

	}

	private static void _calUpdate(StringBuffer sb, String className,String javaNameLower) {
		
		String op = "更新";

		sb.append("\n");
		sb.append("	/**\n")
				.append("	 * ").append(op).append("\n")
				.append("	 *\n")
				.append(CommonCodeGenerator.getRemark())
				.append("	 */\n")
				.append("	@PutMapping(\"/update\")\n")
				.append("	private BaseResponse<").append(className).append("Response> update(@RequestBody @Validated ").append(className).append("UpdateRequest request) {\n")
				.append("\n        ")
				.append(className).append("Response response = this.").append(javaNameLower).append("Service.update(request);\n")
				.append("		return BaseResponse.OK(response);\n")
				.append("	}\n");
		
	}

	private static void _calCreate(StringBuffer sb, String className,String javaNameLower) {
		
		String op = "新增";
		
		sb.append("\n");
		sb.append("	/**\n")
			.append("	 * ").append(op).append("\n")
			.append("	 *\n")
				.append(CommonCodeGenerator.getRemark())
			.append("	 */\n")
			.append("	@PostMapping(\"/create\")\n")
			.append("	private BaseResponse<").append(className).append("Response> create(@RequestBody @Validated ").append(className).append("CreateRequest request) {\n")
			.append("\n        ")
			.append(className).append("Response response = this.").append(javaNameLower).append("Service.create(request);\n")
			.append("		return BaseResponse.OK(response);\n")
			.append("	}\n");
	}

}
