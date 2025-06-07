package com.neutec.blog.model.blog;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neutec.blog.enums.BlogOrderBy;
import com.neutec.blog.enums.BlogStatus;
import com.neutec.blog.enums.Sort;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 通用的Blog查詢條件
 */

@Data
public class UserBlogCriteria implements IBlogCriteria {

    @Schema(description = "Blog Id")
    private Long id;

    @Schema(description = "標題")
    private String title;

    @JsonIgnore
    @Schema(description = "作者ID")
    private Long authorId;

    @Schema(description = "狀態")
    private BlogStatus status;

    @Schema(description = "排序", example = "BLOG_ID")
    private BlogOrderBy orderBy = BlogOrderBy.BLOG_ID;

    @Schema(description = "正序/反序", example = "ASC")
    private Sort sort = Sort.ASC;

    @Schema(description = "略過筆數", example = "0")
    private Integer skip = 0;

    @Schema(description = "呈現筆數", example = "10")
    private Integer limit = 10;

    @Schema(description = "開始日期")
    private Date dateFrom;

    @Schema(description = "結束日期")
    private Date dateTo;


    @Override
    @JsonIgnore
    public Boolean getContainDeleted() {
        return false;
    }

    @Override
    public String getAuthorName() {
        return null;
    }

    @Override
    public void setContainDeleted(Boolean deleted) {

    }

    @Override
    public void setAuthorName(String authorName) {

    }
}
