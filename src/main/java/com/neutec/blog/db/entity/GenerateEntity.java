package com.neutec.blog.db.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;

import java.util.Date;

@Getter
@FieldNameConstants
@MappedSuperclass
public class GenerateEntity {
    private Date createDate;
    private Date updateDate;
}
