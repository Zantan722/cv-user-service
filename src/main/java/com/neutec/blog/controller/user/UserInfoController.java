package com.neutec.blog.controller.user;

import com.neutec.blog.constant.UrlConstant;
import com.neutec.blog.model.user.UserInfoDTO;
import com.neutec.blog.response.BlogRestController;
import com.neutec.blog.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.neutec.blog.constant.HttpConstant.ATTRIBUTE_USER_ID;

@BlogRestController
@Tag(name = "用戶資訊 API", description = "用戶操作API")
@RequestMapping(UrlConstant.USER_PATH)
public class UserInfoController{

    @Autowired
    private UserService userService;


    @GetMapping("profile")
    public UserInfoDTO getUserDetails(@RequestAttribute(name = ATTRIBUTE_USER_ID) Long userId) {
       return userService.getProfile(userId);
    }
}
