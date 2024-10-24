package com.crud.code.svc.tkftProjectTask.service.impl;

import org.springframework.stereotype.Service;
import com.anwen.mongo.toolkit.StringUtils;
import com.anwen.mongo.service.impl.ServiceImpl;
import com.anwen.mongo.model.PageResult;
import com.crud.code.svc.tkftProjectTask.response.TkftProjectTaskItemResponse;
import java.util.List;

import com.crud.code.svc.tkftProjectTask.entity.TkftProjectTaskEntity;
import com.crud.code.svc.tkftProjectTask.request.*;
import com.crud.code.svc.tkftProjectTask.response.TkftProjectTaskResponse;
import com.crud.code.svc.tkftProjectTask.service.TkftProjectTaskService;


/**
 * 
 *
 */
@Service
public class TkftProjectTaskServiceImpl extends ServiceImpl<TkftProjectTaskEntity> implements TkftProjectTaskService {


	/**
	 * 新增
	 *
	 */
	@Override
	public TkftProjectTaskResponse create(TkftProjectTaskCreateRequest request) {
        TkftProjectTaskEntity entity = TkftProjectTaskCreateRequest.toEntity(request);
        this.save(entity);
        return TkftProjectTaskResponse.toResponse(entity);
    }

	/**
	 * 更新
	 *
	 */
	@Override
	public TkftProjectTaskResponse update(TkftProjectTaskUpdateRequest request) {
        TkftProjectTaskEntity entity = TkftProjectTaskUpdateRequest.toEntity(request);
        Boolean updated = this.updateById(entity);
        if (updated) {
            return TkftProjectTaskResponse.toResponse(this.getById(request.getId()));
        } else { // 对更新失败做处理
            return null;
        }
    }

	/**
	 * 分页查询
	 *
	 */
    @Override
    public PageResult<TkftProjectTaskItemResponse> search(TkftProjectTaskSearchPageRequest request) {

        PageResult<TkftProjectTaskItemResponse> response = this.lambdaQuery()
                .eq(StringUtils.isNotBlank(request.getName()), TkftProjectTaskEntity::getName, request.getName())
                .eq(StringUtils.isNotBlank(request.getProject()), TkftProjectTaskEntity::getProject, request.getProject())
                .eq(StringUtils.isNotBlank(request.getStartDate()), TkftProjectTaskEntity::getStartDate, request.getStartDate())
                .eq(StringUtils.isNotBlank(request.getEndDate()), TkftProjectTaskEntity::getEndDate, request.getEndDate())
                .eq(request.getDuration()!=null, TkftProjectTaskEntity::getDuration, request.getDuration())
                .eq(request.getTaskNum()!=null, TkftProjectTaskEntity::getTaskNum, request.getTaskNum())
                .eq(request.getMd()!=null, TkftProjectTaskEntity::getMd, request.getMd())
                .eq(request.getResultStatus()!=null, TkftProjectTaskEntity::getResultStatus, request.getResultStatus())
                .page(request.getPageNum(), request.getPageSize(), TkftProjectTaskItemResponse.class);
        return response;
    }

	/**
	 * 全部查询
	 *
	 */
    @Override
    public List<TkftProjectTaskResponse> search(TkftProjectTaskSearchRequest request) {

        List<TkftProjectTaskEntity> response = this.lambdaQuery()
                .eq(StringUtils.isNotBlank(request.getName()), TkftProjectTaskEntity::getName, request.getName())
                .eq(StringUtils.isNotBlank(request.getProject()), TkftProjectTaskEntity::getProject, request.getProject())
                .eq(StringUtils.isNotBlank(request.getStartDate()), TkftProjectTaskEntity::getStartDate, request.getStartDate())
                .eq(StringUtils.isNotBlank(request.getEndDate()), TkftProjectTaskEntity::getEndDate, request.getEndDate())
                .eq(request.getDuration()!=null, TkftProjectTaskEntity::getDuration, request.getDuration())
                .eq(request.getTaskNum()!=null, TkftProjectTaskEntity::getTaskNum, request.getTaskNum())
                .eq(request.getMd()!=null, TkftProjectTaskEntity::getMd, request.getMd())
                .eq(request.getResultStatus()!=null, TkftProjectTaskEntity::getResultStatus, request.getResultStatus())
                .list();
        return response.stream().map(TkftProjectTaskResponse::toResponse).toList();
    }

	/**
	 * 查询一条
	 *
    */
    @Override
    public TkftProjectTaskResponse get(String id) {
        TkftProjectTaskEntity entity = this.getById(id);
        return TkftProjectTaskResponse.toResponse(entity);
    }

	/**
	 * 删除一条
	 *
	 */
	@Override
	public void delete(String id) {

        this.removeById(id);
	}

	/**
	 * 批量删除
	 *
	 */
	@Override
	public void delete(TkftProjectTaskDeleteRequest request) {
        this.removeBatchByIds(request.getIds());
	}

}
