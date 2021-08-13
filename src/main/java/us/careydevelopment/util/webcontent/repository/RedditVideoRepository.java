package us.careydevelopment.util.webcontent.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import us.careydevelopment.util.webcontent.model.RedditVideo;


@Repository
public interface RedditVideoRepository extends MongoRepository<RedditVideo, String> {

    public RedditVideo findByPermalink(String permalink);
}
