package edu.nuist.ojs.baseinfo.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JournalSectionService {
    @Autowired
    JournalSectionReps journalSectionReps;

    public JournalSection save(JournalSection journalSection){
        JournalSection t = journalSectionReps.save(journalSection);
        if( journalSection.getOrder() < 1){
            journalSection.setOrder(t.getId());
            return journalSectionReps.save(journalSection);
        }
        return t;
    }
    public JournalSection findById(long id){
        return journalSectionReps.findById(id);
    }
    public JournalSection findByGuid(String guid){
        return journalSectionReps.findByGuid(guid);
    }
    public List<JournalSection> findByJournalId( long journalId ){
        return journalSectionReps.findByJournalId(journalId);
    }

    public List<JournalSection> getSectionByTitleLike(long journalId ,String title){
        return journalSectionReps.getSectionByTitleLike(journalId,  title);
    }

    public JournalSection  getSectionByAid(long aid){
        return journalSectionReps.getSectionByAid(aid);
    }

}
