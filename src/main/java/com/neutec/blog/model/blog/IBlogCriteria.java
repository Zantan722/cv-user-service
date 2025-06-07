package com.neutec.blog.model.blog;

import java.util.Date;

public interface IBlogCriteria {
    Long getId();
    String getTitle();

    Long getAuthorId();

    com.neutec.blog.enums.BlogStatus getStatus();

    com.neutec.blog.enums.BlogOrderBy getOrderBy();

    com.neutec.blog.enums.Sort getSort();

    Integer getSkip();

    Integer getLimit();

    Boolean getContainDeleted();

    Date getDateFrom();

    Date getDateTo();

    String getAuthorName();

    void setId(Long id);

    void setTitle(String title);

    void setAuthorId(Long authorId);

    void setStatus(com.neutec.blog.enums.BlogStatus status);

    void setOrderBy(com.neutec.blog.enums.BlogOrderBy orderBy);

    void setSort(com.neutec.blog.enums.Sort sort);

    void setSkip(Integer skip);

    void setLimit(Integer limit);

    void setContainDeleted(Boolean deleted);

    void setDateFrom(Date dateFrom);

    void setDateTo(Date dateTo);

    void setAuthorName(String authorName);
}
