package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
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
 * @author axes create at 2018-05-04-18-37
 * 用户 controller
 * RequestMapping 这里的作用是：
 * 我们要把我们的请求地址全部打到 /user/ 这个命名空间下，
 * 也就是说我们现在定义的接口全部在/user/下的，是个公共的，所以我们就把他写在类上面
 * 如果我们写在方法上，那么每个方法都要写这个 /user/ ，所以为了美观和方便，自然选用这种方式
 */
@Controller
@RequestMapping("/user")
//@RequestMapping("/user/")效果相同
public class UserController {

    //注入 IUserService 。在IUserService 的@service 注解里名字为 iUserService，所以这里
    // 也应该为 iUserService
    @Autowired
    IUserService iUserService;






    /**
     * create by axes at 2018/5/7 下午5:18
     * description: 用户登录接口
     *
     * @param username 用户名
     * @param password 密码
     * @param session  HttpSession
     * @return ServiceResponse<User>
     */
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody // 将返回值自动通过springmvc 的 Jackson 插件序列化为 json
    public ServiceResponse<User> login(String username, String password, HttpSession session) {
        //service -->mybatis -->dao
        ServiceResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()) {
            //设置 session
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }










    /**
     * create by axes at 2018/5/7 下午5:29
     * description:退出登录接口
     *
     * @param session HttpSession
     */
    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
    @ResponseBody // 将返回值自动通过springmvc 的 Jackson 插件序列化为 json
    public ServiceResponse<String> logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServiceResponse.createBySuccessMessage("登出成功");
    }










    /**
     * create by axes at 2018/5/7 下午5:30
     * description: 注册接口
     *
     * @param user User
     * @return ServiceResponse<String>
     */
    @RequestMapping(value = "rigist.do", method = RequestMethod.POST)
    @ResponseBody // 将返回值自动通过springmvc 的 Jackson 插件序列化为 json
    public ServiceResponse<String> rigist(User user) {
        return iUserService.rigist(user);
    }









    /**
     * create by axes at 2018/5/7 下午5:38
     * description:
     *
     * @param value username 或者 email
     * @param type  需要检测的类型
     * @return 根据 type 类型，检测姓名或者邮箱是否已存在。
     */
    @RequestMapping(value = "checkValid.do", method = RequestMethod.POST)
    @ResponseBody // 将返回值自动通过springmvc 的 Jackson 插件序列化为 json
    public ServiceResponse<String> checkValid(String value, String type) {
        return iUserService.checkValid(value, type);
    }








    /**
     * create by axes at 2018/11/4 5:32 PM
     * description: 获取用户信息
     *
     * @param session HttpSession
     * @return User 或者错误消息
     */
    @RequestMapping(value = "get_user_info.do", method = RequestMethod.POST)
    @ResponseBody // 将返回值自动通过springmvc 的 Jackson 插件序列化为 json
    public ServiceResponse<User> getUserInfo(HttpSession session) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            return ServiceResponse.createBySuccess(user);
        }
        return ServiceResponse.createByErrorMessage("用户未登录无法获取用户信息");
    }








    /**
     * create by axes at 2018/11/7 10:52 PM
     * description: 获取密码回答问题
     *
     * @param username 客户姓名
     * @return ServiceResponse<String> 包含密码回答问题或错误信息的 ServiceResponse<String>
     */
    @RequestMapping(value = "get_forget_question.do", method = RequestMethod.POST)
    @ResponseBody // 将返回值自动通过springmvc 的 Jackson 插件序列化为 json
    public ServiceResponse<String> getForgetQuestion(String username) {
        return iUserService.getForgetQuestion(username);
    }









    /**
     * create by axes at 2018/11/7 10:55 PM
     * description: 检查密码回答问题是否正确
     *
     * @param userName 用户名
     * @param question 密码回答问题
     * @param answer   用户填写密码问题
     * @return ServiceResponse<String> 密码回答问题的结果或错误信息的 ServiceResponse<String>
     */
    @RequestMapping(value = "check_question.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> checkQuestion(String userName, String question, String answer) {
        return iUserService.checkQuestion(userName, question, answer);
    }









    /**
     * create by axes at 2018/11/8 11:42 PM
     * description: 重置密码
     *
     * @param userName    用户名
     * @param passwordNew 新的password
     * @param token       用户端传过来的token
     * @return 重置密码的结果
     */
    @RequestMapping(value = "forget_reset_password.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> forgetResetPassword(String userName, String passwordNew, String token) {
        return iUserService.resetUserPassword(userName, passwordNew, token);

    }

    @RequestMapping(value = "reset_password.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> resetPassword(HttpSession session, String oldPassword, String passwordNew) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);

        //检查用户是否登录
        if (user == null) {
            return ServiceResponse.createByErrorMessage("用户未登录，无法重置");
        }

        return iUserService.resetPassword(oldPassword, passwordNew, user);
    }









    /**
     * create by axes at 2018/11/9 11:57 PM
     * description: 更新用户信息（登录状态下）
     *
     * @param session
     * @param user    用户  User
     * @param
     * @return ServiceResponse<User> 更新后user 及更新结果
     */
    @RequestMapping(value = "update_user_info.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<User> updateUserInfo( User user,HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        //检查用户是否登录
        if (currentUser == null) {
            return ServiceResponse.createByErrorMessage("用户未登录，无法更新");
        }
        //这两个字段是不能更新的,为防止更新时数据越权，从session 中获取这些信息。
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        return iUserService.updateUserInfo(user);
    }








    /**
     * create by axes at 2018/11/12 2:39 PM
     * description:
     *
     * @param session HttpSession
     * @return ServiceResponse<User> 用户信息或者错误信息
     */

    @RequestMapping(value = "get_information.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<User> getInformation(HttpSession session) {

        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        //检查用户是否登录
        if (currentUser == null) {
            return ServiceResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，需要强制登录status=10");
        }
        return iUserService.getUseInfo(currentUser.getId());

    }







}
