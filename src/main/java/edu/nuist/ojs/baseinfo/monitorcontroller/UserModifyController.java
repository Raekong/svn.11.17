package edu.nuist.ojs.baseinfo.monitorcontroller;

import edu.nuist.ojs.baseinfo.monitorentity.UserModify;
import edu.nuist.ojs.baseinfo.monitorentity.UserModifyService;

import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserModifyController {
    @Autowired
    UserModifyService userModifyService;
    @RequestMapping("/monitor/user/modify")
    public void monitorModify(@RequestAttribute JSONObject data){
        long userId = data.getLongValue("userId");
        UserModify userModify=userModifyService.findByUserId(userId);
        if(userModify==null){
            userModify= UserModify.builder()
                    .userId(userId)
                    .modifyNum(1)
                    .build();
            userModifyService.save(userModify);
        }
        else {
            userModify.setModifyNum(userModify.getModifyNum()+1);
            userModifyService.save(userModify);
        }
    }
}
