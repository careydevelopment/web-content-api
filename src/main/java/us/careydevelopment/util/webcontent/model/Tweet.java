package us.careydevelopment.util.webcontent.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import us.careydevelopment.model.api.twitter.BaseTweet;
import us.careydevelopment.util.webcontent.constants.ContentType;

@Document(collection = "#{@environment.getProperty('mongo.tweet.collection')}")
public class Tweet extends BaseTweet implements WebContent {

    @Id
    private String id;
    
    private Long persistTime;
    
        
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getPersistTime() {
        return persistTime;
    }

    public void setPersistTime(Long persistTime) {
        this.persistTime = persistTime;
    }

    @Override
    public String getTitle() {
        String title = "Tweet";
        
        if (getAuthor() != null) {
            title = "Tweet by @" + getAuthor().getUsername();            
        }

        return title;
    }

    @Override
    public Long getPublishTime() {
        return this.getCreatedAt();
    }

    @Override
    public String getDescription() {
        return this.getText();
    }

    @Override
    public String getUrl() {
        String url = "https://twitter.com/";
        
        if (getAuthor() != null) {
            url = "https://twitter.com/" + getAuthor().getUsername() + "/status/" + getTweetId();
        }
        
        return url;
    }

    @Override
    public String getThumbnail() {
        return null;
    }

    @Override
    public ContentType getType() {
        return ContentType.TWEET;
    }

    @Override
    public String getContentId() {
        return getTweetId();
    }

    @Override
    public String getSource() {
        return "Twitter";
    }

    @Override
    public String getSubject() {
        return null;
    }

}
