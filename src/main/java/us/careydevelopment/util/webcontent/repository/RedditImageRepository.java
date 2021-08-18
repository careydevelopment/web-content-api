package us.careydevelopment.util.webcontent.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import us.careydevelopment.util.webcontent.model.RedditImage;


@Repository
public interface RedditImageRepository extends MongoRepository<RedditImage, String> {

    public RedditImage findByPermalink(String permalink);
}
