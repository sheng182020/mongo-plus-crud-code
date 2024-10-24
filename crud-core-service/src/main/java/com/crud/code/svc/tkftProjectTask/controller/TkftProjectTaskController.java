package com.crud.code.svc.tkftProjectTask.controller;

import com.anwen.mongo.model.PageResult;
import com.crud.code.svc.tkftProjectTask.response.TkftProjectTaskItemResponse;
import com.crud.code.svc.base.response.BaseResponse;
import com.crud.code.svc.tkftProjectTask.request.*;
import com.crud.code.svc.tkftProjectTask.response.TkftProjectTaskResponse;
import com.crud.code.svc.tkftProjectTask.service.TkftProjectTaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 
 *
 */
@RestController
@RequestMapping("/api/tkftprojecttasks")
public class TkftProjectTaskController {

	private final TkftProjectTaskService tkftProjectTasksService;

	@Autowired
	public TkftProjectTaskController(TkftProjectTaskService tkftProjectTasksService) {
		this.tkftProjectTasksService = tkftProjectTasksService;
	}

	/**
	 * 新增
	 *
	 */
	@PostMapping("/create")
	private BaseResponse<TkftProjectTaskResponse> create(@RequestBody @Validated TkftProjectTaskCreateRequest request) {

        TkftProjectTaskResponse response = this.tkftProjectTasksService.create(request);
		return BaseResponse.OK(response);
	}

	/**
	 * 更新
	 *
	 */
	@PutMapping("/update")
	private BaseResponse<TkftProjectTaskResponse> update(@RequestBody @Validated TkftProjectTaskUpdateRequest request) {

        TkftProjectTaskResponse response = this.tkftProjectTasksService.update(request);
		return BaseResponse.OK(response);
	}

	/**
	 * 分页查询
	 *
	 */
	@GetMapping("/page")
	private BaseResponse<PageResult<TkftProjectTaskItemResponse>> search(@RequestBody @Validated TkftProjectTaskSearchPageRequest request) {
		PageResult<TkftProjectTaskItemResponse> response = this.tkftProjectTasksService.search(request);
		return BaseResponse.OK(response);
	}

	/**
	 * 全部查询
	 *
	 */
	@GetMapping("/list")
	private BaseResponse<List<TkftProjectTaskResponse>> search(@RequestBody @Validated TkftProjectTaskSearchRequest request) {
		List<TkftProjectTaskResponse> response = this.tkftProjectTasksService.search(request);
		return BaseResponse.OK(response);
	}

	/**
	 * 查询一条
	 *
	 */
	@GetMapping("")
	private BaseResponse<TkftProjectTaskResponse> get(@RequestParam(name = "id") String id) {
		TkftProjectTaskResponse response = this.tkftProjectTasksService.get(id);
		return BaseResponse.OK(response);
	}

	/**
	 * 删除一条
	 *
	 */
	@DeleteMapping("")
	private BaseResponse delete(@RequestParam(name = "id") String id) {
		this.tkftProjectTasksService.delete(id);
		return BaseResponse.OK();
	}

	/**
	 * 批量删除
	 *
	 */
	@DeleteMapping("/delete")
	private BaseResponse delete(@RequestBody @Validated TkftProjectTaskDeleteRequest request) {

		this.tkftProjectTasksService.delete(request);
		return BaseResponse.OK();
	}

}
