package us.careydevelopment.util.analytics.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

import us.careydevelopment.util.analytics.model.ArticleView;
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
    

    public List<ArticleView> getArticleViews() {
        List<AggregationOperation> ops = new ArrayList<>();
        
        AggregationOperation group = Aggregation
                                        .group("title")
                                        .count()
                                        .as("count")
                                        .addToSet("urlSlug")
                                        .as("urlSlug");
        
        AggregationOperation project = Aggregation
                                        .project("count", "urlSlug")
                                        .and("title")
                                        .previousOperation();
        
        AggregationOperation sort = Aggregation.sort(Direction.DESC, "count");
        
        ops.add(group);
        ops.add(project);
        ops.add(sort);
        
        Aggregation aggregation = Aggregation.newAggregation(ops);
        
        List<ArticleView> views = getMongoTemplate()
                                    .aggregate(aggregation, getMongoTemplate().getCollectionName(getKlazz()), ArticleView.class)
                                    .getMappedResults();
        
        return views;
    }
    
    
    protected abstract MongoTemplate getMongoTemplate();
    
    protected abstract BaseWebPageVisitRepository<T, ID> getRepo();

    protected abstract Class<T> getKlazz();
}
