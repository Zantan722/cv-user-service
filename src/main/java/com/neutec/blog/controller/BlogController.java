package com.neutec.blog.controller;

import com.neutec.blog.constant.UrlConstant;
import com.neutec.blog.model.Page;
import com.neutec.blog.model.blog.BlogDTO;
import com.neutec.blog.model.blog.CommonBlogCriteria;
import com.neutec.blog.response.BlogRestController;
import com.neutec.blog.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Blog API", description = "一般用戶Blog操作")
@RequestMapping(UrlConstant.COMMON_PATH)
@BlogRestController
public class BlogController {


    @Autowired
    private BlogService blogService;

    @GetMapping("blog")
    @Operation(summary = "查詢", description = "查詢Blog")
    public Page<BlogDTO> searchDTO(CommonBlogCriteria criteria) {
        return blogService.findBlogDTO(criteria);
    }


    @GetMapping("blog/{id}")
    @Operation(summary = "查詢", description = "查詢Blog")
    public BlogDTO searchDTO(@PathVariable Long id) {
        return blogService.findById(id, false);
    }


}
