package us.careydevelopment.util.analytics.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class TagView {

    private String tag;
    private Long count;
    
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public Long getCount() {
        return count;
    }
    public void setCount(Long count) {
        this.count = count;
    }
    
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
