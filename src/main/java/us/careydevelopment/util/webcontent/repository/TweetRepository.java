package us.careydevelopment.util.webcontent.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import us.careydevelopment.util.webcontent.model.Tweet;


@Repository
public interface TweetRepository extends MongoRepository<Tweet, String> {

    public Tweet findByTweetId(String tweetId);
    
}
