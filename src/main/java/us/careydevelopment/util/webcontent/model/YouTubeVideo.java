package us.careydevelopment.util.webcontent.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import us.careydevelopment.model.api.youtube.Video;

@Document(collection = "#{@environment.getProperty('mongo.youtube.collection')}")
public class YouTubeVideo extends Video {

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

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
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
        YouTubeVideo other = (YouTubeVideo) obj;
        if (id == null) {
            if (other.getId() != null)
                return false;
            } else if (!id.equals(other.getId()))
                return false;
            
        return true;
    }
}