package com.neutec.blog.model.blog;

import com.neutec.blog.enums.BlogOrderBy;
import com.neutec.blog.enums.Sort;
import com.neutec.blog.enums.BlogStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class BlogCriteria implements IBlogCriteria {

    @Schema(description = "Blog Id")
    private Long id;

    @Schema(description = "標題")
    private String title;

    @Schema(description = "作者ID")
    private Long authorId;

    @Schema(description = "狀態")
    private BlogStatus status;

    @Schema(description = "排序")
    private BlogOrderBy orderBy;

    @Schema(description = "正序/反序")
    private Sort sort = Sort.ASC;

    @Schema(description = "是否包含已刪除")
    private Boolean containDeleted = false;

    @Schema(description = "略過筆數")
    private Integer skip;

    @Schema(description = "呈現筆數")
    private Integer limit;

    @Schema(description = "開始日期")
    private Date dateFrom;

    @Schema(description = "結束日期")
    private Date dateTo;

    @Schema(description = "作者名稱")
    private String authorName;
}
