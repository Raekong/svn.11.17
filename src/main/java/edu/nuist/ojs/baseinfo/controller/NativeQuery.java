package edu.nuist.ojs.baseinfo.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class NativeQuery {
    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping("/article/list/nativeQuery")
    public List listNativeQuery(@RequestAttribute String sql ){ 
        
        Query query= entityManager.createNativeQuery(sql);
        entityManager.close();
        List<Long> aids = query.getResultList();
        return aids;
    }
}
