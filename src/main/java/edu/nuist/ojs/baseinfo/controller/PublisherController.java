package edu.nuist.ojs.baseinfo.controller;


import edu.nuist.ojs.baseinfo.entity.Publisher;
import edu.nuist.ojs.baseinfo.entity.PublisherService;
import edu.nuist.ojs.baseinfo.entity.Role;
import edu.nuist.ojs.baseinfo.entity.RoleService;
import edu.nuist.ojs.baseinfo.entity.User;
import edu.nuist.ojs.baseinfo.entity.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

@RestController
public class PublisherController {

    @Autowired
    PublisherService publisherService;
    @Autowired
    UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/publish/regist")
    public Publisher regist(@RequestAttribute Publisher publisher, @RequestAttribute User user){
        Publisher p = publisherService.save(publisher);
        if( p != null ){
            user.setPublisherId( p.getId());
            userService.checkAndSave(user);
        }
        return p;
    }

    @RequestMapping("/publish/disable")
    public Publisher disable(@RequestAttribute long pId){
        return publisherService.updateDisable(pId, true);
    }

    @RequestMapping("/publish/enable")
    public Publisher enable(@RequestAttribute long pId){
        return publisherService.updateDisable(pId, false);
    }

    @RequestMapping("/publish/update")
    public Publisher update(@RequestAttribute Publisher publisher){
        if(publisher.getId()!=0){
            return publisherService.update(publisher);
        }else{
            return null;
        }
    }

    @RequestMapping("/publish/searchPagePublishers")
    public String search(@RequestAttribute int  page,
                         @RequestAttribute int size,
                         @RequestAttribute(required = false)String name,
                         @RequestAttribute(required = false)String abbr){
        Publisher publisher=new Publisher();
        publisher.setAbbr(abbr);
        publisher.setName(name);

        ExampleMatcher matcher=ExampleMatcher.matching()
            .withMatcher("abbr", ExampleMatcher.GenericPropertyMatchers.contains())
            .withMatcher("name",ExampleMatcher.GenericPropertyMatchers.contains())
            .withIgnorePaths("id")
            .withIgnorePaths("disable")
            .withIgnorePaths("port");
        Example<Publisher> example=Example.of(publisher, matcher);
        PageRequest pageRequest=PageRequest.of(page-1,size);
        Page<Publisher> publishers=publisherService.findAll(example,pageRequest);

        return JSON.toJSONString(publishers);

    }

    @RequestMapping("/publisher/findById")
    public Publisher findById(@RequestAttribute long id){
        return publisherService.findById(id);
    }

    @RequestMapping("/publish/findByAbbr")
    public Publisher update(@RequestAttribute String abbr){
        return publisherService.findByAbbr(abbr, false);
    }

    @RequestMapping("/publish/updateModules")
    public Publisher updateModules(@RequestAttribute long pId, @RequestAttribute String modules){
        return publisherService.updateModuleJson(pId, modules);
    }

    @RequestMapping("/publish/getAllPagePublishers")
    public String getall(
            @RequestAttribute int  page,
            @RequestAttribute int size){
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC, "id"));//设置时间倒序
        Sort sort = Sort.by(orders);
        Pageable pageRequest = PageRequest.of(page-1,size, sort);
        Page<Publisher> publishers = null;
        publishers = publisherService.findAll(pageRequest);

        return JSON.toJSONString(publishers);
    }

    @RequestMapping("/jouranl/findAllRoleForPublic")
    public List<Role> findAllRoleForPublic(
            @RequestAttribute long pid  ){
        return roleService.findAllRoleForPublic(pid);
    }

}
