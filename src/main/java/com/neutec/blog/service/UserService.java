package com.neutec.blog.service;

import com.neutec.blog.db.dao.UserRepository;
import com.neutec.blog.db.entity.User;
import com.neutec.blog.excption.UserException;
import com.neutec.blog.model.user.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepo;

    /**
     * 註冊新用戶
     */
    public User registerUser(String name, String email, String pwd) {
        User user = userRepo.findByEmail(email);
        if (user != null) {
            throw new UserException("User with email " + email + " already exists.");
        }
        user = new User();
        user.setName(name);
        user.setPassword(encodePassword(pwd));
        user.setEmail(email);
        user.setLastLoginDate(new Date());
        return userRepo.save(user);
    }

    /**
     * 用戶登錄
     */
    public String login(String email, String pwd) {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UserException("User with email " + email + " not found.");
        }
        if (!matches(pwd, user.getPassword())) {
            throw new UserException("Invalid password for user with email " + email);
        }
        user.setLastLoginDate(new Date());
        userRepo.save(user);
        return jwtService.generateToken(user);
    }

    public User findById(Long userId){
        return userRepo.findById(userId)
                .orElseThrow(() -> new UserException("User with ID " + userId + " not found."));
    }

    public UserInfoDTO getProfile(Long userId){
        User user = findById(userId);
        UserInfoDTO userInfo = new UserInfoDTO();
        userInfo.setId(user.getId());
        userInfo.setEmail(user.getEmail());
        userInfo.setName(user.getName());
        return userInfo;
    }

    /**
     * 加密密碼
     */
    private String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * 驗證密碼
     */
    private boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }


}
