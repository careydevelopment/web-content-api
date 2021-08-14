package us.careydevelopment.util.webcontent.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import us.careydevelopment.model.api.reddit.RedditLink;
import us.careydevelopment.util.date.DateConversionUtil;
import us.careydevelopment.util.webcontent.config.WebContentApiConfig;
import us.careydevelopment.util.webcontent.model.Article;
import us.careydevelopment.util.webcontent.model.RedditVideo;
import us.careydevelopment.util.webcontent.model.WebContent;
import us.careydevelopment.util.webcontent.model.YouTubeVideo;
import us.careydevelopment.util.webcontent.repository.ArticleRepository;
import us.careydevelopment.util.webcontent.repository.RedditVideoRepository;
import us.careydevelopment.util.webcontent.repository.YouTubeVideoRepository;
import us.careydevelopment.util.webcontent.util.ArticleUtil;


/**
 * This is a Spring-aware singleton.
 * 
 * You can instantiate it from any Java application, whether it's using Spring or not.
 * 
 * The whole point here is to take advantage of Spring framework benefits (repository
 * interface, document-to-object mapping, etc.) without burdening the application that
 * use this singleton with Spring dependencies.
 * 
 * @author Brian Carey
 *
 */
@Service
public class WebContentService {

    private static final Logger LOG = LoggerFactory.getLogger(WebContentService.class);

    private static final Long MAX_TIME_FOR_ARTICLES = DateConversionUtil.NUMBER_OF_MILLISECONDS_IN_DAY;
    private static final Long MAX_ARTICLES = 50l;
    
    private static final Long MAX_TIME_FOR_YOUTUBE = DateConversionUtil.NUMBER_OF_MILLISECONDS_IN_DAY;
    private static final Long MAX_YOUTUBE_VIDEOS = 6l;
    
    private static final Long MAX_TIME_FOR_REDDIT_VIDEOS = DateConversionUtil.NUMBER_OF_MILLISECONDS_IN_DAY;
    private static final Long MAX_REDDIT_VIDEOS = 6l;
    
    
    private static WebContentService WEB_CONTENT_SERVICE;
    
    @Autowired
    private YouTubeVideoRepository youTubeVideoRepository;
        
    @Autowired
    private RedditVideoRepository redditVideoRepository;
   
    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired
    private MongoTemplate mongoTemplate;

    
    /**
     * This singleton will "Springify" this entire package if it 
     * hasn't already done so. 
     * 
     * It uses AnnotationConfigApplicationContext to load the config
     * file that ultimately scans the entire package for Spring-specific
     * annotations.
     * 
     * Then, it returns this object as retrieved from the application context.
     * 
     * @return WebContentService singleton
     */
    public static WebContentService getWebContentService() {
        if (WEB_CONTENT_SERVICE == null) {
            ApplicationContext context = new AnnotationConfigApplicationContext(WebContentApiConfig.class);
            WEB_CONTENT_SERVICE = context.getBean(WebContentService.class);
        }
        
        return WEB_CONTENT_SERVICE;
    }
    
    
    /**
     * Saves a YouTubeVideo object.
     * 
     * @param youTubeVideo
     */
    public void persistYouTubeVideo(YouTubeVideo video) { 
        String videoId = video.getVideoId();
        YouTubeVideo foundVideo = youTubeVideoRepository.findByVideoId(videoId);
        
        if (foundVideo != null) {
            LOG.debug("Found video " + videoId + ", not persisting");
        } else {
            LOG.debug("Persisting " + videoId + ": " + video.getTitle());
            video.setPersistTime(System.currentTimeMillis());
            youTubeVideoRepository.save(video);        
        }
    }
    
    
    /**
     * Saves a RedditVideo object.
     * 
     * @param redditVideo
     */
    public void persistRedditVideo(RedditVideo video) { 
        String permalink = video.getPermalink();
        RedditVideo foundVideo = redditVideoRepository.findByPermalink(permalink);
        
        if (foundVideo != null) {
            LOG.debug("Found video " + permalink + ", not persisitng");
        } else {
            LOG.debug("Persisting " + permalink + ": " + video.getTitle());
            video.setPersistTime(System.currentTimeMillis());
            redditVideoRepository.save(video);            
        }
    }
    
    
    /**
     * Saves a RedditLink object as an article.
     * 
     * @param redditLink
     */
    public void persistRedditLink(RedditLink link) { 
        String url = link.getUrl();
        Article foundArticle = articleRepository.findByUrl(url);
        
        if (foundArticle != null) {
            LOG.debug("Found article " + url + ", not persisitng");
        } else {
            LOG.debug("Persisting " + url + ": " + link.getTitle());
            Article article = ArticleUtil.convertRedditLinkToArticle(link);
            articleRepository.save(article);            
        }
    }
    
    
    public List<WebContent> fetchTrendingContent() {
        List<Article> articles = fetchTrendingArticles();
        List<YouTubeVideo> youtubeVideos = fetchTrendingYouTubeVideos();
        List<RedditVideo> redditVideos = fetchTrendingRedditVideos();
        
        List<WebContent> webContent = new ArrayList<WebContent>();
        webContent.addAll(articles);
        webContent.addAll(youtubeVideos);
        webContent.addAll(redditVideos);
        
        return webContent;
    }
    
    
    public List<RedditVideo> fetchTrendingRedditVideos() {
        List<AggregationOperation> ops = new ArrayList<>();

        Long minDate = System.currentTimeMillis() - MAX_TIME_FOR_REDDIT_VIDEOS;
        
        AggregationOperation dateThreshold = Aggregation.match(Criteria.where("created").gte(minDate));
        ops.add(dateThreshold);
        
        AggregationOperation sort = Aggregation.sort(Direction.DESC, "score");
        ops.add(sort);
        
        AggregationOperation limit = Aggregation.limit(MAX_REDDIT_VIDEOS);
        ops.add(limit);
         
        Aggregation aggregation = Aggregation.newAggregation(ops);
        
        List<RedditVideo> videos = mongoTemplate.aggregate(aggregation, mongoTemplate.getCollectionName(RedditVideo.class), RedditVideo.class).getMappedResults();
//        videos.forEach(video -> {
//            System.err.println( video.getCreated() + " " + video.getScore() + " " + video.getTitle());
//        });
        
        return videos;
    }
    
    
    public List<YouTubeVideo> fetchTrendingYouTubeVideos() {
        List<AggregationOperation> ops = new ArrayList<>();

        Long minDate = System.currentTimeMillis() - MAX_TIME_FOR_YOUTUBE;
        
        AggregationOperation dateThreshold = Aggregation.match(Criteria.where("publishedAt").gte(minDate));
        ops.add(dateThreshold);
        
        AggregationOperation sort = Aggregation.sort(Direction.DESC, "viewCount");
        ops.add(sort);
        
        AggregationOperation limit = Aggregation.limit(MAX_YOUTUBE_VIDEOS);
        ops.add(limit);
         
        Aggregation aggregation = Aggregation.newAggregation(ops);
        
        List<YouTubeVideo> videos = mongoTemplate.aggregate(aggregation, mongoTemplate.getCollectionName(YouTubeVideo.class), YouTubeVideo.class).getMappedResults();
//        videos.forEach(video -> {
//            System.err.println( video.getPublishedAt() + " " + video.getViewCount() + " " + video.getTitle());
//        });
        
        return videos;
    }
    
    
    public List<Article> fetchTrendingArticles() {
        List<AggregationOperation> ops = new ArrayList<>();

        Long minDate = System.currentTimeMillis() - MAX_TIME_FOR_ARTICLES;
        
        AggregationOperation dateThreshold = Aggregation.match(Criteria.where("publishTime").gte(minDate));
        ops.add(dateThreshold);
        
        AggregationOperation sort = Aggregation.sort(Direction.DESC, "score");
        ops.add(sort);
        
        AggregationOperation limit = Aggregation.limit(MAX_ARTICLES);
        ops.add(limit);
         
        Aggregation aggregation = Aggregation.newAggregation(ops);
        
        List<Article> articles = mongoTemplate.aggregate(aggregation, mongoTemplate.getCollectionName(Article.class), Article.class).getMappedResults();
//        articles.forEach(article -> {
//            System.err.println( article.getPublishTime() + " " + article.getScore() + " " + article.getTitle());
//        });
        
        return articles;
    }
}
