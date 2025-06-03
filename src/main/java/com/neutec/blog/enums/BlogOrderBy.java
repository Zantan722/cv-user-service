package com.neutec.blog.enums;

import com.neutec.blog.db.entity.Blog;
import com.neutec.blog.db.entity.GenerateEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BlogOrderBy {

    /**
     * 建立時間
     */
    CREATED_DATE(GenerateEntity.Fields.createDate),

    /**
     * 點擊數
     */
    UPDATE_DATE(GenerateEntity.Fields.updateDate),

    /**
     * BLOG編號
     */
    BLOG_ID(Blog.Fields.id),
    ;

    private final String orderByFieldName;
}
