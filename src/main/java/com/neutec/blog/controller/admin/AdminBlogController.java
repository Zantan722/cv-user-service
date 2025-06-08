package com.neutec.blog.controller.admin;

import com.neutec.blog.constant.UrlConstant;
import com.neutec.blog.db.entity.Blog;
import com.neutec.blog.model.Page;
import com.neutec.blog.model.blog.BlogCriteria;
import com.neutec.blog.model.blog.BlogDTO;
import com.neutec.blog.model.blog.BlogUpdateRequest;
import com.neutec.blog.response.BlogRestController;
import com.neutec.blog.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.neutec.blog.constant.HttpConstant.ATTRIBUTE_USER_ID;

@Tag(name = "ADMIN Blog API", description = "管理者Blog操作")
@RequestMapping(UrlConstant.ADMIN_PATH)
@BlogRestController
public class AdminBlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("blog")
    @Operation(summary = "查詢", description = "查詢Blog")
    public Page<BlogDTO> searchDTO(BlogCriteria criteria) {
        return blogService.findBlogDTO(criteria);
    }


    @PatchMapping("blog")
    @Operation(summary = "異動blog", description = "編輯blog內容")
    public BlogDTO updateBlog(
        @RequestAttribute(name = ATTRIBUTE_USER_ID) Long userId,
        @Valid @RequestBody BlogUpdateRequest updateRequest) {
        Blog blog = blogService.updateBlogByAdmin(userId, updateRequest);
        return blogService.findById(blog.getId(), false);
    }

    @DeleteMapping("blog/{id}")
    @Operation(summary = "刪除blog", description = "刪除blog")
    public void deleteBlog(
        @RequestAttribute(name = ATTRIBUTE_USER_ID) Long userId,
        @PathVariable Long id) {
        blogService.deleteBlogByAdmin(userId, id);
    }


}
