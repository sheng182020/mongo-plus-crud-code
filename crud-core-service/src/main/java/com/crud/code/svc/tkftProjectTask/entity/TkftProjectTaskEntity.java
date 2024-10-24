package com.crud.code.svc.tkftProjectTask.entity;

import com.crud.code.svc.tkftProjectTask.enums.ResultStatusEnum;

import com.anwen.mongo.annotation.ID;
import com.anwen.mongo.annotation.collection.CollectionField;
import com.anwen.mongo.annotation.collection.CollectionName;
import com.crud.code.svc.base.entity.BaseEntity;
import com.crud.code.svc.base.handler.ObjectIdTypeHandler;
import lombok.Data;
import java.io.Serializable;

/**
 * 
 *
 */
@Data
@CollectionName("tkft_project_tasks")
public class TkftProjectTaskEntity extends BaseEntity implements Serializable {
	
	/**
	 * 主键ID
	 */
	@ID
	@CollectionField("_id")
	private String id;

    private String name;

    @CollectionField(typeHandler = ObjectIdTypeHandler.class)
    private String project;

	@CollectionField("start_date")
    private String startDate;

	@CollectionField("end_date")
    private String endDate;

    private Float duration;

	@CollectionField("assign_party")
    private Object assignParty;

    private Object assignee;

	@CollectionField("task_num")
    private Float taskNum;

    private Float md;

	@CollectionField("sub_tasks")
    private Object subTasks;

    private ResultStatusEnum resultStatus;

}
