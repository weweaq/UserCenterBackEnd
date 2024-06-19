package com.example.usercenterbackend.model;

import lombok.Data;

@Data
public class LoginReq {
    String account;

    String password;
}
