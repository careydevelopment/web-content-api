package us.careydevelopment.util.webcontent.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import us.careydevelopment.model.api.reddit.BaseRedditVideo;
import us.careydevelopment.util.webcontent.constants.ContentStatus;
import us.careydevelopment.util.webcontent.constants.ContentType;

@Document(collection = "#{@environment.getProperty('mongo.redditVideo.collection')}")
public class RedditVideo extends BaseRedditVideo implements WebContent {

    @Id
    private String id;
    
    private Long persistTime;
    private ContentStatus status = ContentStatus.ACTIVE;
    
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

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
     
    @Override
    public String getSource() {
        //TODO: hijacking subreddit as original permalink
        return this.getSubreddit();
    }

    @Override
    public String getSubject() {
        return null;
    }

    @Override
    public Long getPublishTime() {
        return this.getCreated();
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getUrl() {
        return this.getPermalink();
    }

    @Override
    public ContentType getType() {
        return ContentType.REDDIT_VIDEO;
    }

    @Override
    public String getContentId() {
        return this.getPermalink();
    }
    
    public ContentStatus getStatus() {
        return status;
    }
    public void setStatus(ContentStatus status) {
        this.status = status;
    }
        
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
            
        return result;
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RedditVideo other = (RedditVideo) obj;
        if (id == null) {
            if (other.getId() != null)
                return false;
            } else if (!id.equals(other.getId()))
                return false;
            
        return true;
    }
}
