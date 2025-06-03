package com.neutec.blog.model.blog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.neutec.blog.config.ObjectMapperConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.List;

@Data
@Slf4j
public class BlogDTO {
    @Schema(description = "BlogID")
    private Long id;
    @Schema(description = "Blog標題")
    private String title;
    @Schema(description = "Blog內容")
    private String content;
    @Schema(description = "Blog標籤")
    private List<String> tags;
    @Schema(description = "Blog狀態")
    private String status;
    @Schema(description = "建立日期")
    private Date createDate;
    @Schema(description = "更新日期")
    private Date updateDate;
    @Schema(description = "作者")
    private String author;


    public BlogDTO(Long id, String title, String content, String tags, String status, Date createDate, Date updateDate, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        try {
            this.tags = new ObjectMapperConfig().objectMapper().readValue(tags, new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            // ignore;
            log.error("Error parsing tags JSON: {}", e.getMessage());
        }
        this.status = status;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.author = author;
    }
}
