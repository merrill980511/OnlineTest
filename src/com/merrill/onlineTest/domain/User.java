package com.merrill.onlineTest.domain;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String password;
    private String name;
    private String sex;
    private String phone;
    private String qq;
    private String email;
    private String remark;
}
