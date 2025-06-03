package com.neutec.blog.model.blog;

import com.neutec.blog.enums.BlogStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class BlogCreateRequest {

    @NotNull(message = "Blog標題不能為空")
    @Schema(description = "Blog標題")
    private String title;

    @NotNull(message = "Blog內容不能為空")
    @Schema(description = "Blog內容")
    private String content;

    @Schema(description = "Blog標籤")
    private List<String> tags;

    @NotNull(message = "Blog狀態不能為空")
    @Schema(description = "Blog狀態", example = "DRAFT")
    private BlogStatus status;

}
