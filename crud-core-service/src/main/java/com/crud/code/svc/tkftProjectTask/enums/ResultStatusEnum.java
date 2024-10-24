package com.crud.code.svc.tkftProjectTask.enums;


import lombok.Getter;

/**
 *
 *
 */
@Getter
public enum ResultStatusEnum {

    SUCCESS("SUCCESS"),
    FAIL("FAIL"),


	;

	private final String code;

	ResultStatusEnum(String code) {
		this.code = code;
	}
}
