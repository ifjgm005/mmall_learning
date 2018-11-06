package com.mmall.common;

import org.apache.commons.lang.enums.Enum;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * 服务器 Response 统一封装
 *
 * @author axes
 * create at 下午11:51 2018/5/4
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)// 序列号为json 的时候，去除 json 中 值为 null 的 key
public class ServiceResponse<T> implements Serializable {
    private int status;
    private String msg;
    private T data;

    private ServiceResponse(int status) {
        this.status = status;
    }

    private ServiceResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ServiceResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServiceResponse(int status, String msg, T data) {
        this.status = status;
        this.data = data;
        this.msg = msg;

    }
    /**
    * create by axes at 上午12:39 2018/5/5
    * description: 
    * @return 是否成功
    */
    @JsonIgnore //不在序列化 json 中，忽略这个公共方法，不把这个方法返回给前端
    public boolean isSuccess() {
        return status == ResponseCode.SUCCESS.getCode();
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    /**
    * create by axes at 2018/5/5 上午1:04
    * description: 构造出仅仅返回状态码的服务器响应（ServiceResponse）
    * @return ServiceResponse<T>
    */
    public static <T> ServiceResponse<T> createBySuccess() {
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode());
    }



    /**
    * create by axes at 2018/5/5 上午1:07
    * description: 构造 包含状态码和消息的 服务器响应（ServiceResponse）
    * @return ServiceResponse<T>
    * @param msg 消息，描述等
    */
    public static <T> ServiceResponse<T> createBySuccessMessage(String msg) {
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
    }
    

    /**
    * create by axes at 2018/5/5 上午1:15
    * description: 构造包含返回数据的 服务器响应（ServiceResponse）
    * @return ServiceResponse
    * @param data 需要返回的数据
    */
    public static <T> ServiceResponse<T> createBySuccess(T data) {
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }

    /**
    * create by axes at 2018/5/5 上午1:20
    * description:构造包含状态码，消息，服务器的响应 （ServiceResponse）
    * @return ServiceResponse
    * @param msg 消息
    * @param data 服务器的响应数据
    */
    public static <T> ServiceResponse<T> createBySuccess(String msg,T data) {
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }


    /**
    * create by axes at 2018/5/5 上午1:59
    * description: 构造包含一般错误信息的服务器响应（ServiceResponse）
    * @return ServiceResponse<T>
    */
    public static <T> ServiceResponse<T> createByError() {
        return new ServiceResponse<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDecr());
    }

    /**
    * create by axes at 2018/5/5 上午2:05
    * description: 构造包含自定义的错误信息的服务器响应（ServiceResponse）
    * @return <T> ServiceResponse<T>
    * @param errorCode 错误代码
    * @param errorDecr 错误描述，信息
    */
    public static <T> ServiceResponse<T> createByErrorMessage(int errorCode,String errorDecr ) {
        return new ServiceResponse<T>(errorCode, errorDecr);
    }



    /**
    * create by axes at 2018/5/7 下午1:09
    * description: 
    * @return ServiceResponse
    * @param errorDecr 错误描述
    */
    public static <T> ServiceResponse<T> createByErrorMessage(String errorDecr) {
        return new ServiceResponse<T>(ResponseCode.ERROR.getCode(), errorDecr);
    }





}
