package com.neutec.blog.db.entity;

import com.neutec.blog.enums.BlogStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Data
@FieldNameConstants
@Entity
@Table(name = "blog")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String title;

    private String content;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> tags;

    @Enumerated(EnumType.STRING)
    private BlogStatus status; // e.g., "draft", "published"

    private boolean deleted;
}
