package edu.nuist.ojs.baseinfo.controller;

import edu.nuist.ojs.baseinfo.entity.Link;
import edu.nuist.ojs.baseinfo.entity.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinkController {
    @Autowired
    LinkService linkService;
    @RequestMapping("/link/save")
    public Link saveLink(@RequestAttribute Link link){
        System.out.println("=========------------------------------------");
        return linkService.save(link);
    }
    
    @RequestMapping("/link/findByMD5")
    public Link findLink(@RequestAttribute String md5){
        return linkService.findByMD5(md5);
    }
}
