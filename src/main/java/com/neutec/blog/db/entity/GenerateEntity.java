package com.neutec.blog.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;

import java.util.Date;

@Getter
@FieldNameConstants
@MappedSuperclass
public abstract class GenerateEntity {
    @Column(nullable = false, updatable = false,insertable = false)
    private Date createDate;
    @Column(nullable = false, updatable = false,insertable = false)
    private Date updateDate;
}
