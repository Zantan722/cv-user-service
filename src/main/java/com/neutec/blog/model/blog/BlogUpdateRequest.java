package com.neutec.blog.model.blog;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BlogUpdateRequest extends BlogCreateRequest {

    @NotNull(message = "Blog Id 不能為空")
    @Schema(description = "Blog Id")
    private Long id;

}
