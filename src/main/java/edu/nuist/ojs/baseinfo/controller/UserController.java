package edu.nuist.ojs.baseinfo.controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.nuist.ojs.baseinfo.entity.LinkService;
import edu.nuist.ojs.baseinfo.entity.Publisher;
import edu.nuist.ojs.baseinfo.entity.PublisherService;
import edu.nuist.ojs.baseinfo.entity.User;
import edu.nuist.ojs.baseinfo.entity.UserRoleRelation;
import edu.nuist.ojs.baseinfo.entity.UserRoleRelationService;
import edu.nuist.ojs.baseinfo.entity.UserService;
import cn.hutool.crypto.SecureUtil;
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    PublisherService publisherService;

    @Autowired
    UserRoleRelationService urService;

    @Autowired
    LinkService lService;

    @RequestMapping("/user/regist")
    public User regist(@RequestAttribute User user){
        User u = userService.checkAndSave( user );
        if( u == null) {
            u = User.builder().userId(-1).build();//user id 为－1代表已经有这个邮箱重复了
        }
        return u;
    }

    @RequestMapping("/user/isSuper")
    public User isSuper(@RequestAttribute String email){
        return userService.findSuperByEmail(email);
    }

    @RequestMapping("/user/setI18n")
    public User setI18n(@RequestAttribute long uid, @RequestAttribute String lang){
        userService.updateI18n(uid, lang);
        return userService.findByUserId(uid);
    }

    @RequestMapping("/user/findbyemailandpid")
    public User findByPublisherIdAndEmail(@RequestAttribute long publisherId, @RequestAttribute String email){
        return userService.findByPublisherIdAndEmail(publisherId, email);
    }

    @RequestMapping("/user/login")
    public User login(
            @RequestAttribute String email,
            @RequestAttribute String password,
            @RequestAttribute long publishId){
        User user= userService.findByPublisherIdAndEmailAndPassword(publishId, email, password);
        if (user == null ) return new User();
        else return user;
    }

    @RequestMapping("/user/getUserByEmailAndPid")
    public String getUserByEmailAndPid(
            @RequestAttribute String email,
            @RequestAttribute long pid ){
        return JSON.toJSONString(userService.findByPublisherIdAndEmailLike(pid, email));
    }

    @RequestMapping("/user/getSectionEditorByEmailAndJid")
    public String getSectionEditorByEmailAndPid(
            @RequestAttribute String email,
            @RequestAttribute long jid ){
        return JSON.toJSONString(userService.getAllEditorForJournalAndEmailLike(jid, email));
    }

    @RequestMapping("/user/getUserByNameAndPid")
    public String getUserByNameAndPid(
            @RequestAttribute String name,
            @RequestAttribute long pid ){
        return JSON.toJSONString(userService.findByPublisherIdAndNameLike(pid, name));
    }

    @RequestMapping("/user/queryUserWithRidAndNameAndEmail")
    public String queryUserWithRidAndNameAndEmail(
            @RequestAttribute(required = false) String email,
            @RequestAttribute long pid,
            @RequestAttribute(required = false) String  name,
            @RequestAttribute(required = false) long rid,
            @RequestAttribute int  page,
            @RequestAttribute int  size ){
                
        Pageable pageRequest = PageRequest.of(page-1,size);
        Page<User> rst = null;
        if( rid == -1 ){
            rst = userService.findByNameAndEmailLike(pageRequest, pid, email, name);
        }else{
            rst = userService.findByRoleIdAndNameAndEmailLike(pageRequest, pid, rid, email, name);
        }
        return JSONObject.toJSONString(rst);
    }

    @RequestMapping("/user/resetpassword")
    public Map<String,Object> resetPassword(
            @RequestAttribute String email,
            @RequestAttribute String publisherAbbr ){

        Map<String,Object> rst=new HashMap<>();

        long publishId = 0;

        if(publisherAbbr.equals("admin")){
            publishId = -1;
        }else if(publisherService.findByAbbr(publisherAbbr)!=null){
            Publisher publisher = publisherService.findByAbbr(publisherAbbr);
            publishId = publisher.getId();
        }

        if(publishId != 0 ){
            String code="";
            Random rand=new Random();//生成随机数
            for(int a=0;a<6;a++) {
                code += rand.nextInt(10);//生成6位验证码
            }

            userService.updatePassword(publishId, email, code);

            User u = userService.findByPublisherIdAndEmail(publishId, email);
            if( u == null ){
                rst.put("flag",false); //用户输入邮箱找不到
                return rst;
            }
            rst.put("flag",true);
            rst.put("code",code);
            rst.put("username", u.getUsername());

            return rst;
        }

        rst.put("flag",false);
        return rst;

    }

    @RequestMapping("/user/findById")
    public User findById(@RequestAttribute long id){
        return userService.findByUserId(id);
    }


    @RequestMapping("/user/update")
    public User update(@RequestAttribute User u){
        User user1 = userService.findByUserId(u.getUserId());
        if( !user1.getPassword().equals(u.getPassword())){
            String md5password = SecureUtil.md5(u.getPassword());
            u.setPassword(md5password);
        }
        return userService.update(u);
    }

    @RequestMapping("/user/active")
    public User activeUser(@RequestAttribute long id, @RequestAttribute(required = false) String md5){
        User u=userService.findByUserId(id);
        u.setActived(true);
        if( md5 != null)
            lService.close(md5);
        return userService.update(u);
    }

    @RequestMapping("/user/disable")
    public User disableUser(@RequestAttribute long id){
        User user = userService.findByUserId(id);
        boolean disable = user.isDisabled();
        if(disable==true){
            user.setDisabled(false);
        }
        else {
            user.setDisabled(true);
        }
        return userService.save(user);
    }

    @RequestMapping("/user/resetPassword")
    public User userResetPassword(@RequestAttribute long id){
        User user = userService.findByUserId(id);
        String md5password = SecureUtil.md5("666666");
        user.setPassword(md5password);
        return userService.save(user);
    }

    @RequestMapping("/user/getUserEditorRoles")
    public HashMap<String, Boolean> getUserEditorRoles(@RequestAttribute long pid, @RequestAttribute long uid){

        HashMap<String, Boolean> rst = new HashMap<>();
        rst.put("manager", false);
        rst.put("editor", false);
        rst.put("similarcheck", false);
        rst.put("financial", false);
        rst.put("copyeditor", false);
        rst.put("perreviewer", false);
       
        List<UserRoleRelation> relations = urService.getByUidAndPid(uid,pid); 
        for(UserRoleRelation ur: relations){
            if( ur.getRoleId() == 1 || ur.getRoleId() == 2 ) rst.put("manager",true);
            if( ur.getRoleId()>= 3 && ur.getRoleId() <=6 ) rst.put("editor",true);
            if( ur.getRoleId() == 7 ) rst.put("financial",true);
            if( ur.getRoleId() == 9 ) rst.put("copyeditor",true);
            if( ur.getRoleId() == 10 ) rst.put("similarcheck",true);
            if( ur.getRoleId() == 11 ) rst.put("perreviewer",true);
        }


        return rst;
    }
}
