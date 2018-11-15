package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ServiceResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author : Axes
 * create at:  2018/11/12  3:03 PM
 * @description: 后台用户 controller
 */

@Controller
@RequestMapping("/back/user")
public class UserManagerController {
    @Autowired
    IUserService iUserService;

    /**
     * create by axes at 2018/11/12 3:27 PM
     * description: 后台人员登录
     *
     * @param userName 用户名
     * @param password 密码
     * @param session  HttpSession
     * @return ServiceResponse<User>
     */
    @RequestMapping(value = "manager_login.do" ,method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<User> managerLogin(String userName, String password, HttpSession session) {
        ServiceResponse<User> userServiceResponse = iUserService.login(userName, password);
        if (userServiceResponse.isSuccess()) {
            if (userServiceResponse.getData().getRole() != Const.Role.ROLE_ADMIN) {
                return ServiceResponse.createByErrorMessage("非管理员账号无法登录");
            }
            session.setAttribute(Const.CURRENT_USER, userServiceResponse.getData());

        }
        return userServiceResponse;

    }
}
