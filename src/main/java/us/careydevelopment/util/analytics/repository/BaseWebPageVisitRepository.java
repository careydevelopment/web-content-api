package us.careydevelopment.util.analytics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import us.careydevelopment.util.analytics.model.BaseWebPageVisit;

public interface BaseWebPageVisitRepository<T extends BaseWebPageVisit, ID extends Object> extends MongoRepository<T, ID> {

    
}
