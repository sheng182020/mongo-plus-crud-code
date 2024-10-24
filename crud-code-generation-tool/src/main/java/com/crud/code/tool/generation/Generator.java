package com.crud.code.tool.generation;

import com.crud.code.tool.config.GenerateCode;

public class Generator {

	public static void cal(GenerateCode code ) {

		// step1 优先生成entity,确定是否需要生成enum
		EntityGenerator.cal(code);

		EnumGenerator.cal(code);
		
		ControllerGenerator.cal(code);

		ServiceGenerator.cal(code);

		ServiceImplGenerator.cal(code);

		ResponseGenerator.cal(code);

		ResponseItemGenerator.cal(code);

		RequestCreateGenerator.cal(code);

		RequestUpdateGenerator.cal(code);

		RequestSearchGenerator.cal(code);

		RequestSearchPageGenerator.cal(code);

		RequestDeleteGenerator.cal(code);


	}
}
