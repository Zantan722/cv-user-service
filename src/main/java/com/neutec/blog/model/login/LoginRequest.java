package com.neutec.blog.model.login;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginRequest {


    @NotBlank(message = "電子郵件不能為空")
    @Schema(description = "電子郵件", example = "john@example.com")
    @Pattern(
        regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
        message = "電子郵件格式不正確"
    )
    private String email;

    @NotBlank(message = "密碼不能為空")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)[A-Za-z\\\\d@$!%*?&]{10,}$",
        message = "密碼必須包含大小寫字母和數字，長度至少10位"
    )
    @Schema(description = "密碼", example = "Password123")
    private String password;
}
