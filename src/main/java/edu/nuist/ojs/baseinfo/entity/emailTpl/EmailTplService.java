package edu.nuist.ojs.baseinfo.entity.emailTpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailTplService {
    @Autowired
    EmailTplReps emailTplReps;
    public EmailTemplate save(EmailTemplate emailTemplate){
        return emailTplReps.save(emailTemplate);
    }

    public EmailTemplate findById(long id){
        return emailTplReps.findById(id);
    }

    public void delById (long id){
        emailTplReps.delById(id);
    }

    public void setDefault(long id){
        EmailTemplate tpl = emailTplReps.findById(id);
        if( tpl != null ){
            emailTplReps.clearDefault(tpl.getConfigPointId());
            emailTplReps.setDefault(id);
        }

    }

    public void update(long id, String json){
        emailTplReps.update(id, json);
    }
}
