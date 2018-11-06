package com.mmall.common;

/**
 * @author axes
 * create at  2018/5/7  下午3:51
 * 常量类
 */
public class Const {
    public static final String CURRENT_USER = "current_user";

    //用枚举太重了，刚好可以用这种方式分组
    public interface checkType {
        String TYPE_USERNAME = "type_username";
        String TYPE_EMAIL = "type_email";
    }

    public interface Role {
        int ROLE_COMMON = 0;//普通用户
        int ROLE_ADMIN = 1;//管理元用户
    }
}
