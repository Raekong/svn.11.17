package edu.nuist.ojs.userandconfig;

import com.alibaba.fastjson.JSONObject;

import org.junit.Test;

import cn.hutool.crypto.SecureUtil;
import edu.nuist.ojs.baseinfo.entity.User;



public class JSONTest {
    @Test
    public void k(){
        String s = "{'email':'ffff@126.com','username':'ss'}";

        User u = JSONObject.parseObject(s, User.class);
        
        ;
    }
}
