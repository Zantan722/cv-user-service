package com.neutec.blog.db.entity;


import com.neutec.blog.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.Date;

@Data
@FieldNameConstants
@Entity
@Table(name = "users")
public class User extends GenerateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String name;
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;
    private Date lastLoginDate;

}
