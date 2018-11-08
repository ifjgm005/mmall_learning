package com.mmall.service;

import com.mmall.common.ServiceResponse;
import com.mmall.pojo.User;

import javax.servlet.http.HttpSession;

/**
 * @author axes create at 2018-05-04-22-53
 * 用户 service 接口
 */
public interface IUserService {
    ServiceResponse<User> login(String useName, String passWord);

    ServiceResponse<String> checkValid(String value, String type);

    ServiceResponse<String> rigist(User user);

    ServiceResponse<String> getForgetQuestion(String username);

    ServiceResponse<String> checkQuestion(String username,String question,String answer);

    ServiceResponse<String> resetUserPassword(String userName, String passwordNew, String token);

    ServiceResponse<String> resetPassword(String oldPassword,String passwordNew,User user)

}

