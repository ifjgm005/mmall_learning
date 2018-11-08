package com.mmall.dao;

import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUser(String userName);
//多个参数时必须使用 Param ,在xml 中实现的时候要和注解的 userName_
    User selectLogin(@Param("userName_") String  userName, @Param("passWord") String passWord);

    //检查email
    int checkEmail(String email);

    String getForgetQuestion(String username);

    int selectAnswer(@Param("username") String username,@Param("question") String question,@Param("answer") String answer);
}