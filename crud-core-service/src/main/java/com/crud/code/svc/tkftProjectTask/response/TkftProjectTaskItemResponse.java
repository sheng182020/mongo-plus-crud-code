package com.crud.code.svc.tkftProjectTask.response;

import com.crud.code.svc.tkftProjectTask.entity.TkftProjectTaskEntity;
import com.crud.code.svc.tkftProjectTask.enums.ResultStatusEnum;

import com.anwen.mongo.annotation.collection.CollectionField;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * 
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TkftProjectTaskItemResponse {

    @CollectionField("_id")
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

    public static TkftProjectTaskItemResponse toResponse(TkftProjectTaskEntity entity) {
        if (entity == null) {
            return null;
        }
        String json = JSONUtil.toJsonStr(entity);
        return JSONUtil.toBean(json, TkftProjectTaskItemResponse.class);
    }
}
