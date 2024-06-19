package com.example.usercenterbackend.model;

import lombok.Data;

@Data
public class RegisterReq {

    String account;

    String password;

    String checkPassword;
}
