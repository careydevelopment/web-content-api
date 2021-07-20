package us.careydevelopment.util.analytics.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import us.careydevelopment.util.analytics.model.BaseWebPageVisit;

public interface BaseWebPageVisitRepository<T extends BaseWebPageVisit, ID extends Object> extends MongoRepository<T, ID> {

    @Query("{'time': { '$gte' : ?0 } }")
    public List<T> findByDate(Long timestamp);
    
}
