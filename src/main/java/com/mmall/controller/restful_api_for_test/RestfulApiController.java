package com.mmall.controller.restful_api_for_test;

import com.mmall.common.ServiceResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author : Axes
 * create at:  2018/11/14  3:49 PM
 * description: 用来测试的接口,但是只完成了部分接口。需要添加新的接口时才会来维护这个分支
 */
@Controller
@RequestMapping("/api/test")
public class RestfulApiController {


    /**
     * create by axes at 2018/11/13 10:01 PM
     * description: 用来给客户端测试  get 接口
     */
    @RequestMapping(value = "get.do" ,method = RequestMethod.GET)
    @ResponseBody
    public ServiceResponse<String> test() {
        int index = (int) (Math.random() * (10));
        if (index < 5) {
            return ServiceResponse.createByErrorMessage("OKHttp 同步请求-错误的拿到小于5的数" + index);
        } else {

            return ServiceResponse.createBySuccessMessage("OKHttp 同步请求-成功获取大于5的数" + index);
        }

    }

    /**
     * create by axes at 2018/11/13 10:01 PM
     * description: 用来给客户端测试  get 接口
     */
    @RequestMapping(value = "get_asy.do" ,method = RequestMethod.GET)
    @ResponseBody
    public ServiceResponse<String> getAsy() {
        int index = (int) (Math.random() * (10));
        if (index < 5) {
            return ServiceResponse.createByErrorMessage("OKHttp 异步请求-错误的拿到小于5的数" + index);
        } else {

            return ServiceResponse.createBySuccessMessage("OKHttp 异步请求-成功获取大于5的数" + index);
        }

    }


    /**
     * create by axes at 2018/11/13 10:01 PM
     * description: 用来给客户端测试  post string  接口
     */
    @RequestMapping(value = "post_string.do" ,method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> post(String postString) {
       if(StringUtils.isNotBlank(postString)){
           return ServiceResponse.createBySuccessMessage("OKHttp post String 请求收到你的String 为：" + postString);
       }
        return ServiceResponse.createBySuccessMessage("OKHttp post String 接口调用成功，但是传参数为空" );

    }




    /**
     * create by axes at 2018/11/13 10:01 PM
     * description: 用来给客户端测试  post InputStream 接口
     */
    @RequestMapping(value = "post_stream.do" ,method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> post_stream(InputStream inputStream) {

        //读取流文件
        String postString= null;
        try {
            postString = getStringFromInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
           return ServiceResponse.createBySuccessMessage("OKHttp post inputStream 接口报错，请联系后台人员" );
        }


        if(StringUtils.isNotBlank(postString)){
            return ServiceResponse.createBySuccessMessage("OKHttp post inputStream 请求收到你的String 为：" + postString);
        }
        return ServiceResponse.createBySuccessMessage("OKHttp post inputStream 接口调用成功，但是传参数为空" );

    }

    private String getStringFromInputStream(InputStream inputStream) throws IOException {
        String newLine = System.getProperty("line.separator");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder result = new StringBuilder();
        String line;
        boolean flag = false;
        while ((line = reader.readLine()) != null) {
            result.append(flag? newLine: "").append(line);
            flag = true;
        }
        return result.toString();
    }

}
