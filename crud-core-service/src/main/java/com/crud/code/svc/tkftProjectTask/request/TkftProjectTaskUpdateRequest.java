package com.crud.code.svc.tkftProjectTask.request;

import com.crud.code.svc.tkftProjectTask.enums.ResultStatusEnum;

import cn.hutool.json.JSONUtil;
import com.crud.code.svc.tkftProjectTask.entity.TkftProjectTaskEntity;
import lombok.Data;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 *  修改参数 
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TkftProjectTaskUpdateRequest {

	private String id;

    private String name;

    private String project;

    private String startDate;

    private String endDate;

    private Float duration;

    private Object assignParty;

    private Object assignee;

    private Float taskNum;

    private Float md;

    private Object subTasks;

    private ResultStatusEnum resultStatus;

    public static TkftProjectTaskEntity toEntity(TkftProjectTaskUpdateRequest request) {
        String json = JSONUtil.toJsonStr(request);
        return JSONUtil.toBean(json, TkftProjectTaskEntity.class);
    }
}
