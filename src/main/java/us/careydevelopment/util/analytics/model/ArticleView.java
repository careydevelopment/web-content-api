package us.careydevelopment.util.analytics.model;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ArticleView {
    
    private Long count;
    private String title;
    private String urlSlug;
    private Long time;
    private List<String> tags;
    private String imageUrl;
    private String category;
    
    public Long getCount() {
        return count;
    }
    public void setCount(Long count) {
        this.count = count;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUrlSlug() {
        return urlSlug;
    }
    public void setUrlSlug(String urlSlug) {
        this.urlSlug = urlSlug;
    }
    
    public Long getTime() {
        return time;
    }
    public void setTime(Long time) {
        this.time = time;
    }
    
    public List<String> getTags() {
        return tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
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
        ArticleView other = (ArticleView) obj;
        if (title == null) {
            if (other.getTitle() != null)
                return false;
        } else if (!title.equals(other.getTitle()))
            return false;
        return true;
    }
    
    
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
