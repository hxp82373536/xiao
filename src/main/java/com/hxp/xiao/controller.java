package com.hxp.xiao;

import org.springframework.web.bind.annotation.*;

/**
 * @Author: hxp
 * @Date: 2019/6/4 15:21
 * @Describe:
 */

@RestController
public class controller {

    @RequestMapping(method = RequestMethod.GET,value ="/test")
    @ResponseBody
    public String testRequestBody() {
        return "hello world";
    }
}


