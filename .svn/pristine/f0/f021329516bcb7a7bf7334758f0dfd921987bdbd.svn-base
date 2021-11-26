package edu.nuist.ojs.baseinfo.entity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class PublisherService {
    @Autowired
    PublisherReps publisherReps;
    public Publisher findById(long id){
        return publisherReps.findById(id);
    }

    public Publisher findByAbbr(String abbr){
        return publisherReps.findByAbbr(abbr);
    }

    public Publisher findByAbbr(String abbr, boolean disable){
        return publisherReps.findByAbbrAndDisable(abbr, disable);
    }

    public Publisher save(Publisher publisher){
        Publisher publisher1 = findByAbbr(publisher.getAbbr());
        if(publisher1 == null){
            return publisherReps.save(publisher);
        }else{
            return null;
        }
    }

    public Page<Publisher> findAll(Pageable pq){
        return publisherReps.findAll( pq );
    };

    public Publisher update(Publisher publisher){
        return publisherReps.save(publisher);
    }
    
    public Page<Publisher> findAll(Example<Publisher> example, PageRequest pageRequest){
        return publisherReps.findAll(example,pageRequest);
    }

    public Publisher updateAbbr(String abbr, String alter){
        Publisher publisher = publisherReps.findByAbbr(abbr);
        if (publisher != null){
            publisher.setAbbr(alter);
            return save(publisher);
        }else{
            return null;
        }
    }

    public Publisher updateName(String abbr, String name){
        Publisher publisher = publisherReps.findByAbbr(abbr);
        if (publisher != null){
            publisher.setName(name);
            return publisherReps.save(publisher);
        }else{
            return null;
        }
    }

    public Publisher updateI18n(String abbr, String i18n){
        Publisher publisher = publisherReps.findByAbbr(abbr);
        if (publisher != null){
            publisher.setI18n(i18n);
            return publisherReps.save(publisher);
        }else{
            return null;
        }
    }

    public Publisher updateModuleJson(long id, String modules){
        Publisher publisher = publisherReps.findById(id);
        if (publisher != null){
            publisher.setModuleJson(modules);
            return publisherReps.save(publisher);
        }else{
            return null;
        }
    }

    public Publisher updateDisable(long id, boolean disable){
        Publisher publisher = publisherReps.findById(id);
        if (publisher != null){
            publisher.setDisable(disable);
            return publisherReps.save(publisher);
        }else{
            return null;
        }
    }

}
