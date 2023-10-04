package com.chris.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 史偕成
 * @date 2023/10/04 16:00
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String username;

    private Integer age;

    private String address;
}
