package com.example.shoehub.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private  String username;
    private  String email;
    private  String phone;
    private  String address;
    private  String password;
    private  String role;
}
