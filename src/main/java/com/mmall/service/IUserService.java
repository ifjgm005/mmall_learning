package com.mmall.service;

import com.mmall.common.ServiceResponse;
import com.mmall.pojo.User;

/**
 * @author axes create at 2018-05-04-22-53
 * 用户 service 接口
 */
public interface IUserService {
    ServiceResponse<User> login(String useName, String passWord);

    ServiceResponse<String> checkValid(String value,String type);

    ServiceResponse<String> rigist(User user);
}

