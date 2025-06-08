package com.neutec.blog.model.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoDTO {

    private Long id;
    private String email;
    private String name;
}
