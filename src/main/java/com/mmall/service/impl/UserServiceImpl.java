package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServiceResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户 service 接口实现
 *
 * @author axes  create at 下午10:14 2018/5/4
 */
//这里的名字为 iUserService，在controller 里用 @Autowired 注入时变量名也需要为 iUserService
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    /**
     * create by axes at 下午11:40 2018/5/4
     *
     * @return Object
     * @param usename 用户名
     * @param password 密码
     */
    //通过 Autowired 注入到这里。
    @Autowired
    private UserMapper userMapper;

    @Override
    public ServiceResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUser(username);
        if (resultCount == 0) {
            return ServiceResponse.createByErrorMessage("用户不存在");
        }
        //todo selectLogin
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username, md5Password);
        if (user == null) {
            return ServiceResponse.createByErrorMessage("密码错误");

        }
        //密码重置为空
        user.setPassword(StringUtils.EMPTY);
        return ServiceResponse.createBySuccess("登录成功", user);
    }

    /**
     * create by axes at 2018/5/7 下午5:48
     * description: 若type username 则检测username 是否合法，若type 为email 则检测 email 是否合法。
     *
     * @param value 需要检测的值
     * @param type  需要检测的类型
     * @return ServiceResponse<String>
     */
    @Override
    public ServiceResponse<String> checkValid(String value, String type) {
        if (StringUtils.isNotBlank(value) && StringUtils.isNotBlank(type)) {

            //检查用户是否存在
            if (type.equals(Const.checkType.TYPE_USERNAME)) {
                int resultCount = userMapper.checkUser(value);
                if (resultCount > 0) {
                    return ServiceResponse.createByErrorMessage("用户已存在");
                }
            }
            //检查eamil 是否存在
            else if (type.equals(Const.checkType.TYPE_EMAIL)) {

                int resultCount = userMapper.checkEmail(value);
                if (resultCount > 0) {
                    return ServiceResponse.createByErrorMessage("该 email 已经注册");
                }
            }

        } else {
            return ServiceResponse.createByErrorMessage("参数错误");
        }
        return ServiceResponse.createBySuccess();

    }


    /**
     * create by axes at 2018/5/7 下午6:16
     * description: 注册用户
     *
     * @param user 用户
     * @return ServiceResponse<String>
     */
    @Override
    public ServiceResponse<String> rigist(User user) {
        ServiceResponse<String> repose_check_username = checkValid(user.getUsername(), Const.checkType.TYPE_USERNAME);
        ServiceResponse<String> repose_check_email = checkValid(user.getEmail(), Const.checkType.TYPE_EMAIL);
       //返回校验姓名失败的 ServiceResponse<String>
        if (!repose_check_username.isSuccess()) {
            return repose_check_username;

        }
        //返回校验email 失败的 ServiceResponse<String>
        if (!repose_check_email.isSuccess()) {
            return repose_check_email;
        }
//        为了安全一般是传输之前就会编译，这里只是为了演示。所以这边进行了加密处理
        String mdPassword = MD5Util.MD5EncodeUtf8(user.getPassword());
        user.setPassword(mdPassword);

//        如果用户角色未指定默认为普通用户
        if (user.getRole()==null) {
            user.setRole(Const.Role.ROLE_COMMON);
        }
        int count = userMapper.insert(user);
        if (count == 0) {
            return ServiceResponse.createByErrorMessage("注册失败");
        }
        return ServiceResponse.createBySuccessMessage("注册成功");
    }
}
