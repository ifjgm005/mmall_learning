package com.mmall.common;

/**
 * @author axes
 * create at  2018/5/5  上午12:42
 * 服务器端 响应状态码值枚举
 */
public enum ResponseCode {

    SUCCESS(0, "SUCCESS"),
    ERROR(1, "ERROR"),
    NEED_LOGIN(10, "NEED_LOGIN"),
    ILLEGAL_ARGUMENT(2, "ILLEGAL_ARGUMENT");


    private final int code;
    private final String decr;

    /**
    * create by axes at 2018/5/5
    * description:构造函数
    * @param code 服务器 response 状态码
    * @param decr 服务器返回 response 状态描述
    */
    ResponseCode(int code, String decr) {
        this.code = code;
        this.decr = decr;
    }

    public int getCode() {
        return code;
    }

    public String getDecr() {
        return decr;
    }


}
