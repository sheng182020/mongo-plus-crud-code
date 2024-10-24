package com.crud.code.tool.config;

import lombok.Getter;

/**
 * 定义系统基础字段
 */
@Getter
public enum BaseEntityColumns {
	MODIFIEDTIME("updatedAt","Date","@CollectionField(fill = FieldFill.UPDATE)"),
	CREATEDTIME("createdAt","Date","@CollectionField(fill = FieldFill.INSERT_UPDATE)"),

	;

	private String name;

	private String type;

	private String remark;

	BaseEntityColumns(String name,String type,String remark){
		this.name=name;
		this.type=type;
		this.remark=remark;
	}
	
	public static boolean isMetaDataColumn(ColumnInfo col) {
		String nameLowwer = col.getJavaColumnNameLowwer();
		return MODIFIEDTIME.getName().equalsIgnoreCase(nameLowwer)
				|| CREATEDTIME.getName().equalsIgnoreCase(nameLowwer)
				;
	}
}
