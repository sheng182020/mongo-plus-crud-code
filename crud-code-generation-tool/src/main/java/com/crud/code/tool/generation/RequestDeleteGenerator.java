package com.crud.code.tool.generation;

import com.crud.code.tool.config.GenerateCode;
import com.crud.code.tool.utils.Tools;

public class RequestDeleteGenerator {

	public static void cal(GenerateCode code) {

		String className = code.getObjectName();

		StringBuffer sb = new StringBuffer();

		sb.append("package ").append(code.getJavaPackage()).append(".").append(code.getModelName()).append(".request;\n")
				.append("\n");

		sb.append("\n")

				.append("import cn.hutool.json.JSONUtil;\n")
				.append("import ").append(code.getJavaPackage()).append(".").append(code.getModelName()).append(".entity.").append(className).append("Entity;\n")

				.append("import lombok.Data;\n")
				.append("import jakarta.validation.constraints.*;\n")
				.append("import lombok.AllArgsConstructor;\n")
				.append("import lombok.NoArgsConstructor;\n")
				.append("import java.util.List;\n")

				.append("\n")
				.append("/**\n")
				.append(" * ").append(code.getTable().getTableComment()).append(" 批量删除参数 \n")
				.append(" *\n")
				.append(" */\n")
				.append("@Data\n")
				.append("@AllArgsConstructor\n")
				.append("@NoArgsConstructor\n")
				.append("public class ").append(className).append("DeleteRequest {\n\n")
				.append("    @NotNull\n").append("    private List<String> ids;\n").append("}\n");

		Tools.log(sb.toString());
		code.setRequestDeleteCode(sb.toString());
		code.setRequestDeleteCodeFileName(className + "DeleteRequest.java");
	}

}
