package com.neutec.blog.db.entity;

import com.neutec.blog.enums.BlogStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@FieldNameConstants
@Entity
@Table(name = "blog")
public class Blog extends GenerateEntity {

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
