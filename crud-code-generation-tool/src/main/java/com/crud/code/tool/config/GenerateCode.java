package com.crud.code.tool.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.*;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class GenerateCode {

	TableInfo table;

	OptionConfig optionConfig;

	String objectName;
	
	List<ColumnInfo> columns = new ArrayList<>();
	Map<String,List<String>> newEnums = new HashMap<>();//当前类的所有importEnum, key:名称，value:字段值
	Set<String> importClass = new HashSet<>();

	String javaPackage;

	String modelName;

	String controllerCode;
	
	String serviceCode;
	
	String serviceImplCode;
	
	String responseCode;

	String responseItemCode;

	String entityCode;
	
	String requestDeleteCode;
	
	String requestCreateCode;
	
	String requestUpdateCode;
	
	String requestSearchCode;

	String requestSearchPageCode;

	Map<String,String> enumsCode = new HashMap<>();
	
	// FileName
	String controllerCodeFileName;
	
	String serviceCodeFileName;
	
	String serviceImplCodeFileName;
	
	String responseCodeFileName;

	String responseItemCodeFileName;

	String entityCodeFileName;

	String requestCreateCodeFileName;
	
	String requestUpdateCodeFileName;

	String requestDeleteCodeFileName;

	String requestSearchCodeFileName;

	String requestSearchPageCodeFileName;

	public ColumnInfo getPKeyColumn() {
		for(ColumnInfo col : this.columns) {
			if(col.isKey()) {
				return col;
			}
		}
		throw new RuntimeException("这个表没有设置主键:"+this.getTable().getTableName());
	}
}
