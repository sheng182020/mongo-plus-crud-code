package com.crud.code.svc.tkftProjectTask.service;

import com.anwen.mongo.service.IService;
import com.anwen.mongo.model.PageResult;
import com.crud.code.svc.tkftProjectTask.response.TkftProjectTaskItemResponse;
import com.crud.code.svc.tkftProjectTask.request.*;
import com.crud.code.svc.tkftProjectTask.response.TkftProjectTaskResponse;
import com.crud.code.svc.tkftProjectTask.entity.TkftProjectTaskEntity;
import java.util.List;

/**
 * 
 *
 */
public interface TkftProjectTaskService extends IService<TkftProjectTaskEntity> {


	/**
	 * 新增
	 *
	 */
	TkftProjectTaskResponse create(TkftProjectTaskCreateRequest request);

	/**
	 * 更新
	 *
	 */
	TkftProjectTaskResponse update(TkftProjectTaskUpdateRequest request);

	/**
	 * 分页查询
	 *
	 */
	PageResult<TkftProjectTaskItemResponse> search(TkftProjectTaskSearchPageRequest request);

	/**
	 * 全部查询
	 *
	 */
	List<TkftProjectTaskResponse> search(TkftProjectTaskSearchRequest request);

	/**
	 * 查询一条
	 *
	 */
	TkftProjectTaskResponse get(String id);

	/**
	 * 删除一条
	 *
	 */
	void delete(String id);

	/**
	 * 批量删除
	 *
	 */
	void delete(TkftProjectTaskDeleteRequest request);

}
