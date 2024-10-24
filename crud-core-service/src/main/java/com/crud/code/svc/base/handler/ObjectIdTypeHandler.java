package com.crud.code.svc.base.handler;

import cn.hutool.core.util.StrUtil;
import com.anwen.mongo.handlers.TypeHandler;
import org.bson.types.ObjectId;

public class ObjectIdTypeHandler implements TypeHandler<String> {

    @Override
    public Object setParameter(String fieldName, String value) {
        if (StrUtil.isNotBlank(value)){
            return new ObjectId(value);
        }
        return null;
    }

    @Override
    public String getResult(Object obj) {
        if (obj!=null){
            return obj.toString();
        }
        return null;
    }
}
