package us.careydevelopment.util.webcontent.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import us.careydevelopment.util.webcontent.model.YouTubeVideo;


@Repository
public interface YouTubeVideoRepository extends MongoRepository<YouTubeVideo, String> {

    public YouTubeVideo findByVideoId(String videoId);
}
