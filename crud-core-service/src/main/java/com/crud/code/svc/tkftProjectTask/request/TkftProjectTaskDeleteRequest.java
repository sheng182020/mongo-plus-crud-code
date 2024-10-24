package com.crud.code.svc.tkftProjectTask.request;


import cn.hutool.json.JSONUtil;
import com.crud.code.svc.tkftProjectTask.entity.TkftProjectTaskEntity;
import lombok.Data;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 *  批量删除参数 
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TkftProjectTaskDeleteRequest {

    @NotNull
    private List<String> ids;
}
