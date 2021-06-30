package com.sher.examonline.entity;

import lombok.Data;

/**
 * @Title User
 * @Package com.sher.examonline.entity
 * @Description user entity
 * @Author sher
 * @Date 2021/06/28 10:53 AM
 */
@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String role;
}
