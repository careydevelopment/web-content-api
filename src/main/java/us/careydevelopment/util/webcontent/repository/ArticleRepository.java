package us.careydevelopment.util.webcontent.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import us.careydevelopment.util.webcontent.model.Article;


@Repository
public interface ArticleRepository extends MongoRepository<Article, String> {

    public Article findByUrl(String url);
    
}
