package us.careydevelopment.util.analytics.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class CategoryView {

    private String category;
    private Long count;
    
    
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
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
