package us.careydevelopment.util.webcontent.model;

import us.careydevelopment.util.webcontent.constants.ContentType;

public interface WebContent {

    public String getTitle();
    public Long getPublishTime();
    public String getDescription();
    public String getUrl();
    public String getThumbnail();
    public ContentType getType();
    public String getContentId();
    
}
