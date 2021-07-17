package us.careydevelopment.util.analytics.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

import us.careydevelopment.util.analytics.model.BaseWebPageVisit;
import us.careydevelopment.util.analytics.repository.BaseWebPageVisitRepository;

public abstract class BaseWebPageVisitService<T extends BaseWebPageVisit, ID extends Object> {
    
    private static final Logger LOG = LoggerFactory.getLogger(BaseWebPageVisitService.class);

    private ExecutorService executor = Executors.newFixedThreadPool(10);
            
    
    public void savePageVisit(T webPageVisit) {
        Runnable runnable = () -> {
            LOG.debug("Saving " + webPageVisit);
            getRepo().save(webPageVisit);
        };
        
        executor.execute(runnable);
    }
    
    
    
    protected abstract MongoTemplate getMongoTemplate();
    
    protected abstract BaseWebPageVisitRepository<T, ID> getRepo();

}
