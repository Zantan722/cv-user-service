package com.neutec.blog.controller.user;

import com.neutec.blog.db.entity.Blog;
import com.neutec.blog.model.Page;
import com.neutec.blog.model.blog.BlogCreateRequest;
import com.neutec.blog.model.blog.BlogDTO;
import com.neutec.blog.model.blog.BlogUpdateRequest;
import com.neutec.blog.model.blog.UserBlogCriteria;
import com.neutec.blog.response.BlogRestController;
import com.neutec.blog.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.neutec.blog.constant.HttpConstant.ATTRIBUTE_USER_ID;

@Tag(name = "User Blog API", description = "用戶Blog操作")
@BlogRestController
public class UserBlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("blog")
    @Operation(summary = "查詢用戶個人Blog", description = "查詢用戶個人Blog")
    public Page<BlogDTO> searchDTO(
        @RequestAttribute(name = ATTRIBUTE_USER_ID) Long userId,
        UserBlogCriteria criteria) {
        criteria.setAuthorId(userId);
        return blogService.findBlogDTO(criteria);
    }

    @PostMapping("blog")
    @Operation(summary = "建立blog", description = "建立blog")
    public BlogDTO createBlog(
        @RequestAttribute(name = ATTRIBUTE_USER_ID) Long userId,
        @Valid @RequestBody BlogCreateRequest createRequest) {
        Blog blog = blogService.createBlog(createRequest, userId);
        return blogService.findById(blog.getId(), false);
    }

    @PatchMapping("blog")
    @Operation(summary = "異動blog", description = "編輯blog內容")
    public BlogDTO updateBlog(
        @RequestAttribute(name = ATTRIBUTE_USER_ID) Long userId,
        @Valid @RequestBody BlogUpdateRequest updateRequest) {
        Blog blog = blogService.updateBlog(userId, updateRequest);
        return blogService.findById(blog.getId(), false);
    }

    @DeleteMapping("blog/{id}")
    @Operation(summary = "刪除blog", description = "刪除blog")
    public void updateBlog(
        @RequestAttribute(name = ATTRIBUTE_USER_ID) Long userId,
        @PathVariable Long id) {
        blogService.deleteBlog(userId, id);
    }


}
