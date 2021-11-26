package edu.nuist.ojs.baseinfo.entity.emailTpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailConfigPointServer {
    @Autowired 
    private EmailConfigPointReps ecpReps;

    @Autowired 
    private EmailTplReps tplReps;

    public EmailConfigPoint save(EmailConfigPoint ecp){
        return ecpReps.save(ecp);
    }

    public EmailConfigPoint findByConfigPointAndJid(String configPoint, long jid){
        EmailConfigPoint ecp = ecpReps.findByConfigPointAndJid(configPoint, jid);
        List<EmailTemplate> tpls = tplReps.findByConfigPointAndJid(configPoint, jid);
        if( ecp != null)  ecp.setTpls(tpls);
        return ecp;
    }

    public List<EmailConfigPoint> getAllEmailConfigPointsByJid(long jid){
        List<EmailConfigPoint> points = ecpReps.findByJid(jid);
        for(EmailConfigPoint ecp : points){
            List<EmailTemplate> tpls = tplReps.findByConfigPointAndJid(ecp.getConfigPoint(), jid);
            ecp.setTpls(tpls);
        }
        return points;
    }

    public void setWithEmail(long id, boolean flag){
        ecpReps.setWithEmail(id, flag);
    }
}
