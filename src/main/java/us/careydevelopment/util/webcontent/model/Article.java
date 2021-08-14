package us.careydevelopment.util.webcontent.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import us.careydevelopment.util.webcontent.constants.ContentType;
import us.careydevelopment.util.webcontent.constants.OriginalSource;

@Document(collection = "#{@environment.getProperty('mongo.article.collection')}")
public class Article implements WebContent {

    @Id
    private String id;
    
    private String title;
    private String blurb;
    private String subject;
    private String url;
    private String thumbnail;
    private Integer score;
    private Long persistTime;
    private String author;
    private OriginalSource originalSource;
    private String siteName;
    private Long publishTime;
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getBlurb() {
        return blurb;
    }
    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getThumbnail() {
        return thumbnail;
    }
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    public Integer getScore() {
        return score;
    }
    public void setScore(Integer score) {
        this.score = score;
    }
    public Long getPersistTime() {
        return persistTime;
    }
    public void setPersistTime(Long persistTime) {
        this.persistTime = persistTime;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    public OriginalSource getOriginalSource() {
        return originalSource;
    }
    public void setOriginalSource(OriginalSource originalSource) {
        this.originalSource = originalSource;
    }
    
    
    public Long getPublishTime() {
        return publishTime;
    }
    public void setPublishTime(Long publishTime) {
        this.publishTime = publishTime;
    }
    public String getSiteName() {
        return siteName;
    }
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }
    
    @Override
    public String getDescription() {
        return blurb;
    }
    
    @Override
    public ContentType getType() {
        return ContentType.ARTICLE;
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
        Article other = (Article) obj;
        if (id == null) {
            if (other.getId() != null)
                return false;
            } else if (!id.equals(other.getId()))
                return false;
            
        return true;
    }
    
}
