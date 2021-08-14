package us.careydevelopment.util.webcontent.util;

import us.careydevelopment.model.api.reddit.RedditLink;
import us.careydevelopment.util.webcontent.constants.OriginalSource;
import us.careydevelopment.util.webcontent.model.Article;

public class ArticleUtil {

    public static Article convertRedditLinkToArticle(RedditLink link) {
        Article article = new Article();
        
        //article.setAuthor(link.get);
        article.setBlurb(link.getDescription());
        article.setOriginalSource(OriginalSource.REDDIT);
        article.setPersistTime(System.currentTimeMillis());
        //article.setSubject(null);
        article.setThumbnail(link.getThumbnail());
        article.setTitle(link.getTitle());
        article.setUrl(link.getUrl());
        article.setScore(link.getScore());
        article.setPublishTime(link.getCreated());
        
        return article;
    }
}
