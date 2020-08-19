package com.hxp.xiao.bean;

import lombok.Data;

/**
 * @Author: hxp
 * @Date: 2019/8/6 15:16
 * @Describe:
 */
@Data
public class Request {
    String url;
    Boolean hasbody;
    String xml;
    int sucesscode;
}
