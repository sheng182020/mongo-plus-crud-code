package com.crud.code.tool.config;

import com.crud.code.tool.utils.Tools;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TableInfo {
	
	String tableName;
	
	String tableComment;

	String tableType;
	
	public String getJavaName() {
		return Tools.toHump(this.tableName);
	}
	
	public String getJavaNameLowwer() {
		return Tools.lowwerFirst(getJavaName());
	}
	public String getControllerURL() {
		
		return this.getJavaName().toLowerCase();
	}
}
