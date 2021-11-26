package edu.nuist.ojs.baseinfo.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JournalSettingService {
    @Autowired
    JournalSettingReps journalSettingReps;
    
    public JournalSetting save(JournalSetting journalSetting){
        JournalSetting js = journalSettingReps.findByJournalIdAndConfigPoint( journalSetting.getJournalId(), journalSetting.getConfigPoint());
        if( js != null ){
            journalSetting.setId(js.getId());
        }
        return journalSettingReps.save(journalSetting);
    }

    public List<JournalSetting> findByJournalId(long journalId){
        return journalSettingReps.findByJournalId(journalId);
    }

    public JournalSetting findById(long id){
        return journalSettingReps.findById(id);
    }

    
    public List<JournalSetting> findByPIdAndConfigPoint(long pid, String configPoint){
        return journalSettingReps.findByPIdAndConfigPoint(pid, configPoint);
    }

    public JournalSetting findByJournalIdAndConfigPoint(long journalId,String configPoint){
        return journalSettingReps.findByJournalIdAndConfigPoint(journalId, configPoint);
    }
}
