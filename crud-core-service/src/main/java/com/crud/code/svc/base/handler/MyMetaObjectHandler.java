package com.crud.code.svc.base.handler;

import com.anwen.mongo.handlers.MetaObjectHandler;
import com.anwen.mongo.model.AutoFillMetaObject;
import com.crud.code.svc.base.entity.BaseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(AutoFillMetaObject insertAutoFillMetaObject) {
        insertAutoFillMetaObject.fillValue(BaseEntity::getCreatedAt, new Date());
    }

    @Override
    public void updateFill(AutoFillMetaObject updateAutoFillMetaObject) {
        updateAutoFillMetaObject.fillValue(BaseEntity::getUpdatedAt, new Date());
    }
}
