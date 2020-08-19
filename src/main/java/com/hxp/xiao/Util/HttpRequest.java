package com.hxp.xiao.Util;

import com.hxp.xiao.bean.Request;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

/**
 * @Author: hxp
 * @Date: 2019/8/6 14:57
 * @Describe:
 */
public class HttpRequest {
    public static int post(Request request) {
        //关闭
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "stdout");

        //创建httpclient工具对象
        HttpClient client = new HttpClient();
        //创建post请求方法
        PostMethod myPost = new PostMethod(request.getUrl());
        //设置请求超时时间
        client.setConnectionTimeout(300 * 1000);
        String responseString = null;
        int statusCode =0;
        try {
            //设置请求头部类型
            myPost.setRequestHeader("Content-Type", "text/xml");
            myPost.setRequestHeader("charset", "utf-8");
            if(request.getHasbody()){
                myPost.setRequestEntity(new StringRequestEntity(request.getXml(),"text/xml","utf-8"));
            }
            statusCode = client.executeMethod(myPost);
        } catch (Exception e) {
            e.printStackTrace();
        }
        myPost.releaseConnection();
        return statusCode;
    }

}
