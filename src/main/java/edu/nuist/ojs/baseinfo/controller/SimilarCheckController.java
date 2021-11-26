package edu.nuist.ojs.baseinfo.controller;

import com.alibaba.fastjson.JSON;
import edu.nuist.ojs.baseinfo.entity.SimilarCheck;
import edu.nuist.ojs.baseinfo.entity.SimilarCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SimilarCheckController {

    @Autowired
    SimilarCheckService similarCheckService;
    @RequestMapping("/similarCheck/save")
    public SimilarCheck saveSimilarCheck(@RequestAttribute SimilarCheck similarCheck){
        return similarCheckService.save(similarCheck);
    }

    @RequestMapping("/similarCheck/findByAid")
    public String findSimilarCheckByAid(@RequestAttribute long aid){
        return JSON.toJSONString(similarCheckService.findByAid(aid));
    }

    @RequestMapping("/similarCheck/unchecked")
    public String findUnchecked(){
        return JSON.toJSONString(similarCheckService.findUnchecked());
    }

    @RequestMapping("/similarCheck/unuploaded")
    public String findUnupoloaded(){
        return JSON.toJSONString(similarCheckService.findUnuploaded());
    }

    @RequestMapping("/similarCheck/findByAidAndRound")
    public String findByAidAndRound(
        @RequestAttribute long aid,
        @RequestAttribute int round
    ){
        return JSON.toJSONString(similarCheckService.findByAidAndRound(aid, round));
    }
}
