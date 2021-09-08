package us.careydevelopment.util.webcontent.model;

import us.careydevelopment.util.webcontent.constants.ContentStatus;
import us.careydevelopment.util.webcontent.constants.ContentType;

public interface WebContent {

    public String getId();
    public String getTitle();
    public Long getPublishTime();
    public String getDescription();
    public String getUrl();
    public String getThumbnail();
    public ContentType getType();
    public String getContentId();
    public String getSource();
    public String getSubject();
    public ContentStatus getStatus();
    
}
