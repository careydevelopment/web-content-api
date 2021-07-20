package us.careydevelopment.util.analytics.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;

import us.careydevelopment.util.analytics.model.ArticleView;
import us.careydevelopment.util.analytics.model.BaseWebPageVisit;
import us.careydevelopment.util.analytics.model.CategoryView;
import us.careydevelopment.util.analytics.model.TagView;
import us.careydevelopment.util.analytics.repository.BaseWebPageVisitRepository;
import us.careydevelopment.util.analytics.util.SourceUtil;
import us.careydevelopment.util.date.DateConversionUtil;

public abstract class BaseWebPageVisitService<T extends BaseWebPageVisit, ID extends Object> {
    
    private static final Logger LOG = LoggerFactory.getLogger(BaseWebPageVisitService.class);

    private ExecutorService executor = Executors.newFixedThreadPool(10);
            
    
    public void savePageVisit(T webPageVisit) {
        Runnable runnable = () -> {
            LOG.debug("Saving " + webPageVisit);
            getRepo().save(webPageVisit);
        };
        
        //run this in the background so the visitor doesn't have to wait while we
        //persist analytics info
        executor.execute(runnable);
    }
        
    
    public List<TagView> getTagViews() {
        Long currentTime = System.currentTimeMillis();
        Long dateThreshold = currentTime - DateConversionUtil.NUMBER_OF_MILLISECONDS_IN_FOUR_WEEKS;
        
        return getTagViews(dateThreshold);
    }
    
    
    public List<TagView> getTagViews(Long dateThreshold) {
        List<AggregationOperation> ops = new ArrayList<>();
        
        AggregationOperation minTime = Aggregation
                                        .match(Criteria.where("time")
                                        .gte(dateThreshold));
        
        AggregationOperation unwind = Aggregation.unwind("tags");
        
        AggregationOperation group = Aggregation
                                        .group("tags")
                                        .count()
                                        .as("count")
                                        .addToSet("tags")
                                        .as("tag");

        AggregationOperation project = Aggregation
                                        .project("count")
                                        .and("tag")
                                        .previousOperation();

        AggregationOperation sort = Aggregation
                                        .sort(Direction.DESC, "count");

        ops.add(minTime);
        ops.add(unwind);
        ops.add(group);
        ops.add(project);
        ops.add(sort);

        Aggregation aggregation = Aggregation.newAggregation(ops);

        List<TagView> views = getMongoTemplate()
                                    .aggregate(aggregation, getMongoTemplate().getCollectionName(getKlazz()), TagView.class)
                                    .getMappedResults();

        return views;
    } 
    
    
    public List<CategoryView> getCategoryViews() {
        Long currentTime = System.currentTimeMillis();
        Long dateThreshold = currentTime - DateConversionUtil.NUMBER_OF_MILLISECONDS_IN_FOUR_WEEKS;
        
        return getCategoryViews(dateThreshold);
    }
    
    
    public List<CategoryView> getCategoryViews(Long dateThreshold) {
        List<AggregationOperation> ops = new ArrayList<>();
        
        AggregationOperation minTime = Aggregation
                                        .match(Criteria.where("time")
                                        .gte(dateThreshold));
        
        AggregationOperation group = Aggregation
                                        .group("category")
                                        .count()
                                        .as("count");

        AggregationOperation project = Aggregation
                                        .project("count")
                                        .and("category")
                                        .previousOperation();

        AggregationOperation sort = Aggregation
                                        .sort(Direction.DESC, "count");

        ops.add(minTime);
        ops.add(group);
        ops.add(project);
        ops.add(sort);

        Aggregation aggregation = Aggregation.newAggregation(ops);

        List<CategoryView> views = getMongoTemplate()
                                    .aggregate(aggregation, getMongoTemplate().getCollectionName(getKlazz()), CategoryView.class)
                                    .getMappedResults();

        return views;
    }
    

    public List<ArticleView> getArticleViews() {
        Long currentTime = System.currentTimeMillis();
        Long dateThreshold = currentTime - DateConversionUtil.NUMBER_OF_MILLISECONDS_IN_FOUR_WEEKS;
        
        return getArticleViews(dateThreshold);
    }
    
    
    public List<ArticleView> getArticleViews(Long dateThreshold) {
        List<AggregationOperation> ops = new ArrayList<>();
        
        AggregationOperation minTime = Aggregation
                                        .match(Criteria.where("time").gte(dateThreshold));
        
        AggregationOperation group = Aggregation
                                        .group("title")
                                        .count()
                                        .as("count")
                                        .addToSet("urlSlug")
                                        .as("urlSlug")
                                        .addToSet("time")
                                        .as("time");

        AggregationOperation project = Aggregation
                                        .project("count", "urlSlug", "time")
                                        .and("title")
                                        .previousOperation();

        AggregationOperation sort = Aggregation
                                        .sort(Direction.DESC, "count");

        ops.add(minTime);
        ops.add(group);
        ops.add(project);
        ops.add(sort);

        Aggregation aggregation = Aggregation.newAggregation(ops);

        List<ArticleView> views = getMongoTemplate()
                                    .aggregate(aggregation, getMongoTemplate().getCollectionName(getKlazz()), ArticleView.class)
                                    .getMappedResults();

        return views;
    }
    
    
    public List<T> getWebPageVisits() {
        Long currentTime = System.currentTimeMillis();
        Long dateThreshold = currentTime - DateConversionUtil.NUMBER_OF_MILLISECONDS_IN_FOUR_WEEKS;
        
        return getWebPageVisits(dateThreshold, null, null);
    }
    
    
    /**
     * Gets web page visits without grouping.
     * 
     * Useful for overall counts rather than by-category counts.
     * 
     * @param dateThreshold
     * @param category
     * @param tag
     * @return list of WebPageVisit objects
     */
    public List<T> getWebPageVisits(Long dateThreshold, String category, String tag) {
        List<AggregationOperation> ops = new ArrayList<>();
        
        AggregationOperation minTime = Aggregation
                                        .match(Criteria.where("time")
                                        .gte(dateThreshold));

        ops.add(minTime);

        if (category != null) {
            AggregationOperation catMatch = Aggregation
                    .match(Criteria.where("category").is(category));
            
            ops.add(catMatch);
        }
        
        if (tag != null) {
            AggregationOperation tagMatch = Aggregation
                    .match(Criteria.where("tags").is(tag));
            
            ops.add(tagMatch);
        }
        
        Aggregation aggregation = Aggregation.newAggregation(ops);

        List<T> views = getMongoTemplate()
                                    .aggregate(aggregation, getMongoTemplate().getCollectionName(getKlazz()), getKlazz())
                                    .getMappedResults();

        return views;
    }
    
    
    protected abstract MongoTemplate getMongoTemplate();
    
    protected abstract BaseWebPageVisitRepository<T, ID> getRepo();

    protected abstract Class<T> getKlazz();
}
