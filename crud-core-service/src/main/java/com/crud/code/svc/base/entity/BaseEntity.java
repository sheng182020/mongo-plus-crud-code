package com.crud.code.svc.base.entity;

import com.anwen.mongo.annotation.collection.CollectionField;
import com.anwen.mongo.enums.FieldFill;
import lombok.Data;

import java.util.Date;

@Data
public class BaseEntity {

    @CollectionField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedAt;

    @CollectionField(fill = FieldFill.INSERT)
    private Date createdAt;
}
