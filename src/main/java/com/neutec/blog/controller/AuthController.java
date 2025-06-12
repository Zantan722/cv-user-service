package com.neutec.blog.controller;

import com.neutec.blog.constant.UrlConstant;
import com.neutec.blog.model.login.LoginRequest;
import com.neutec.blog.model.user.RegisterRequest;
import com.neutec.blog.response.BlogRestController;
import com.neutec.blog.response.Result;
import com.neutec.blog.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Auth API", description = "Authentication related operations")
@RequestMapping(UrlConstant.COMMON_PATH)
@BlogRestController
public class AuthController {


    @Autowired
    private UserService userService;

    @PostMapping("login")
    @Operation(summary = "登入", description = "登入後取得jwt token")
    public Result login(@Valid @RequestBody LoginRequest loginRequest) {
        return Result.success(userService.login(loginRequest.getEmail(), loginRequest.getPassword()));
    }

    @PostMapping("register")
    @Operation(summary = "註冊", description = "註冊新用戶")
    public void register(@Valid @RequestBody RegisterRequest registerRequest) {
        userService.registerUser(registerRequest.getName(), registerRequest.getEmail(), registerRequest.getPassword());
    }

}
