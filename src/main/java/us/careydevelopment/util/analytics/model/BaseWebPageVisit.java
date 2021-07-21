package us.careydevelopment.util.analytics.model;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import us.careydevelopment.util.date.DateFormatUtil;

public abstract class BaseWebPageVisit {

    private Long time;
    private String urlSlug;
    private String title;
    private List<String> tags;
    private String category;
    private String referer;
    private String imageUrl;
    
    public Long getTime() {
        return time;
    }
    public void setTime(Long time) {
        this.time = time;
    }
    public String getUrlSlug() {
        return urlSlug;
    }
    public void setUrlSlug(String urlSlug) {
        this.urlSlug = urlSlug;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    
    public List<String> getTags() {
        return tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getReferer() {
        return referer;
    }
    public void setReferer(String referer) {
        this.referer = referer;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getHourOfVisit() {
        String hour = "";
        
        if (time != null) {
            hour = DateFormatUtil.getHourOfDay(time);
        }
        
        return hour;
    }
    
    public String getDateOfVisit() {
        String date = "";
        
        if (time != null) {
            date = DateFormatUtil.getDate(time);
        }
        
        return date;
    }
    
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
