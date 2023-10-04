package com.chris.controller;

import com.chris.common.entity.Result;
import com.chris.white.annotation.White;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 史偕成
 * @date 2023/10/04 15:59
 **/
@Slf4j
@RestController
@RequestMapping("/")
public class UserController {


    @White(key = "userId", msg = "非白名单可访问用户拦截")
    @GetMapping("/query")
    public Result queryUserInfo(@RequestParam("") String userId) {
        return Result.success(new User("虫虫:" + userId, 19, "天津市东丽区万科赏溪苑14-0000"));
    }

}
