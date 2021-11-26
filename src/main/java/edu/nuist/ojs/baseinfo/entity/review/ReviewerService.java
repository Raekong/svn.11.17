package edu.nuist.ojs.baseinfo.entity.review;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReviewerService {
    @Autowired
    private ReviewerReps reps;

    public Reviewer save(Reviewer r){
        return reps.save(r);
    }

    public List<Reviewer> findByNameAndEmailAndInterestsAndAffiliationLike(String email, String name, String interests, String affiliation, long pid){
        return reps.findByNameAndEmailAndInterestsAndAffiliationLike(email, name, interests, affiliation, pid);
    }

    public Reviewer findByPidAndEmail(long pid, String email){
        return reps.findByPidAndEmail(pid, email);
    }

    public List<Reviewer> findByAidAndRoundId( long aid, long rid){
        return reps.findByAidAndRoundId( aid,  rid);
    }


    public JSONObject  queryReviewerLikeUnion(String email, String name, String interests, String affiliation, long pid, int pageNum, int pageSize){
        
        int start = pageNum*pageSize;
        List<Object[]> list = reps.queryReviewerLikeUnion(email, name, interests, affiliation, pid, start, pageSize);
        List<Object[]> number = reps.queryReviewerLikeUnionCount( email,  name,  interests,  affiliation,  pid);

        List<JSONObject> objs = new LinkedList<>();
        for(Object[] a : list){
            JSONObject obj = new JSONObject();
            obj.put("email", (String)a[0]);
            obj.put("name", (String)a[1]);
            obj.put("research", (String)a[2]);
            obj.put("affiliation", (String)a[3]);
            obj.put("total", (Integer)a[4] == null? 0: (Integer)a[4]);
            obj.put("completed", (Integer)a[5] == null? 0: (Integer)a[5]);

            if((Integer)a[4] == null){
                obj.put("source", "1");//是用户
            }else{
                obj.put("source", "2");//是审稿人
            }
            objs.add(obj);
        }

        long total = ((BigInteger)number.get(0)[0]).longValue();

        JSONObject rst = new JSONObject();
        rst.put("totalElements", total);
        rst.put("pageNum", pageNum);
        rst.put("pageSize", pageSize);
        rst.put("totalPage", total%pageSize == 0 ? total/pageSize : (total/pageSize)+1 );
        rst.put("content", objs);
        
        return rst;
    }

}
