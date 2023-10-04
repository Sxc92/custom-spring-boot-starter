package com.chris.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 史偕成
 * @date 2023/10/04 15:36
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private Object data;

    private String msg;

    private int code;

    public static Result success(Object data) {
        return new Result(data, "成功", 200);
    }


    public static Result error(String msg, int code) {
        return new Result(null, msg, code);
    }

    public static Result error(String msg) {
        return new Result(null, msg, 500);
    }
}
