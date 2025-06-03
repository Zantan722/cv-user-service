package com.neutec.blog.db.entity;

import lombok.Getter;
import lombok.experimental.FieldNameConstants;

import java.util.Date;

@Getter
@FieldNameConstants
public abstract class GenerateEntity {
    private Date createDate;
    private Date updateDate;
}
