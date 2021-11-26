package edu.nuist.ojs.baseinfo.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkService {
    @Autowired
    LinkReps linkReps;
    public Link findByMD5(String md5){
        return linkReps.findByMD5(md5);
    }
    public Link save(Link link){
        return linkReps.save(link);
    }


    public void close(long id){
        linkReps.close(id);
    }

    public void close(String md5){
        linkReps.close(md5);
    }


    public Link findByRaidLike(String para){
        return linkReps.findByRaidLike(para);
    }
}
