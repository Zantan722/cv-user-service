package com.neutec.blog.service;

import com.neutec.blog.db.dao.BlogRepository;
import com.neutec.blog.db.entity.Blog;
import com.neutec.blog.db.entity.User;
import com.neutec.blog.excption.BlogException;
import com.neutec.blog.model.Page;
import com.neutec.blog.model.blog.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.neutec.blog.enums.UserRole.ADMIN;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserService userService;

    public Page<BlogDTO> findBlogDTO(IBlogCriteria criteria) {
        long total = blogRepository.count(criteria);

        if (total == 0) {
            // 如果沒有數據，直接返回一個空的Page對象
            return Page.of(0, null, 0, criteria.getLimit());
        }
        List<BlogDTO> list = blogRepository.findDTO(criteria);
        // 返回一個模擬的Page對象
        return Page.of(total, list, criteria.getSkip(), criteria.getLimit());
    }

    public Blog findById(Long id) {
        return blogRepository.findById(id).orElseThrow(() -> new BlogException("Blog not found"));
    }

    public BlogDTO findById(Long id, boolean fetchDeleted) {
        BlogCriteria criteria = new BlogCriteria();
        criteria.setId(id);
        if(!fetchDeleted){
            criteria.setContainDeleted(false);
        }
        List<BlogDTO> list = blogRepository.findDTO(criteria);
        if (list.isEmpty()) {
            throw new BlogException("Blog not found");
        }
        return list.get(0);
    }


    public Blog createBlog(BlogCreateRequest createRequest, Long userId) {
        Blog blog = new Blog();
        blog.setTitle(createRequest.getTitle());
        blog.setContent(createRequest.getContent());
        blog.setTags(createRequest.getTags());
        blog.setStatus(createRequest.getStatus());
        blog.setUserId(userId);
        return blogRepository.save(blog);
    }

    public Blog updateBlog(Long userId, BlogUpdateRequest updateRequest) {
        Blog blog = findById(updateRequest.getId());
        if (!blog.getUserId().equals(userId)) {
            throw new BlogException("User does not have permission to edit this blog");
        }
        blog.setTitle(updateRequest.getTitle());
        blog.setContent(updateRequest.getContent());
        blog.setTags(updateRequest.getTags());
        blog.setStatus(updateRequest.getStatus());
        return blogRepository.save(blog);
    }

    public boolean deleteBlog(Long userId, Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new BlogException("Blog not found"));
        if (!blog.getUserId().equals(userId)) {
            throw new BlogException("User does not have permission to delete this blog");
        }
        blog.setDeleted(true);
        blogRepository.save(blog);
        return true;
    }

    public boolean deleteBlogByAdmin(Long userId, Long id) {
        Blog blog = findById(id);
        User user = userService.findById(userId);
        if (user == null || !user.getRole().equals(ADMIN)) {
            throw new BlogException("Only admin can delete blogs");
        }
        blog.setDeleted(true);
        blogRepository.save(blog);
        return true;
    }


}
