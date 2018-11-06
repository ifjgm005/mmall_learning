package com.mmall.controller.portal;

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
 * @author axes create at 2018-05-04-18-37
 *用户 controller
 * RequestMapping 这里的作用是：
 * 我们要把我们的请求地址全部打到 /user/ 这个命名空间下，
 * 也就是说我们现在定义的接口全部在/user/下的，是个公共的，所以我们就把他写在类上面
 * 如果我们写在方法上，那么每个方法都要写这个 /user/ ，所以为了美观和方便，自然选用这种方式
 */
@Controller
@RequestMapping("/user/")
public class UserController {
    /**
     * 用户登录
     * create by axes at 下午10:14 2018/5/4
     *
     * @return Object
     * @param userName 用户姓名
     */

    //注入 IUserService 。在IUserService 的@service 注解里名字为 iUserService，所以这里
    // 也应该为 iUserService
    @Autowired
    IUserService iUserService;

    /**
    * create by axes at 2018/5/7 下午5:18
    * description: 用户登录接口
    * @return ServiceResponse<User>
    * @param username 用户名
    * @param password 密码
    * @param session HttpSession
    */
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody // 将返回值自动通过springmvc 的 Jackson 插件序列化为 json
    public ServiceResponse<User> login(String username, String password, HttpSession session){
        //service -->mybatis -->dao
        ServiceResponse<User> response=iUserService.login(username, password);
        if (response.isSuccess()) {
            //设置 session
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }



    /**
    * create by axes at 2018/5/7 下午5:29
    * description:退出登录接口
    * @param session  HttpSession
    */

    @RequestMapping(value = "logout.do",method = RequestMethod.GET)
    @ResponseBody // 将返回值自动通过springmvc 的 Jackson 插件序列化为 json
    public ServiceResponse<String> logout(HttpSession session) {
        if (session != null) {
            session.removeAttribute(Const.CURRENT_USER);
            return ServiceResponse.createBySuccessMessage("登出成功");

        } else {
            return ServiceResponse.createByErrorMessage("session 不能为空");
        }
    }



    /**
    * create by axes at 2018/5/7 下午5:30
    * description: 注册接口
    * @return ServiceResponse<String>
    * @param user User
    */

    @RequestMapping(value = "rigist.do",method = RequestMethod.POST)
    @ResponseBody // 将返回值自动通过springmvc 的 Jackson 插件序列化为 json
    public ServiceResponse<String> rigist(User user) {
        return iUserService.rigist(user);
    }

    /**
    * create by axes at 2018/5/7 下午5:38
    * description:
    * @return 根据 type 类型，检测姓名或者邮箱是否已存在。
    * @param value username 或者 email
    * @param type 需要检测的类型
    */
    @RequestMapping(value = "checkValid.do",method = RequestMethod.POST)
    @ResponseBody // 将返回值自动通过springmvc 的 Jackson 插件序列化为 json
    public ServiceResponse<String> checkValid(String value, String type) {
       return iUserService.checkValid(value, type);
    }
/**
* create by axes at 2018/11/4 5:32 PM
* description: 获取用户信息
* @return User 或者错误消息
 * @param session
*/
    @RequestMapping(value = "getUserInfo.do",method = RequestMethod.GET)
    @ResponseBody // 将返回值自动通过springmvc 的 Jackson 插件序列化为 json
    public ServiceResponse<User> getUserInfo(HttpSession session){

        if(session!=null){
            User user = (User) session.getAttribute(Const.CURRENT_USER);
            return ServiceResponse.createBySuccess(user);
        }
        return ServiceResponse.createByErrorMessage("用户未登录无法获取用户信息");
    }






}
