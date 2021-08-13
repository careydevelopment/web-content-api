package us.careydevelopment.util.webcontent.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import us.careydevelopment.util.webcontent.config.WebContentApiConfig;
import us.careydevelopment.util.webcontent.model.RedditVideo;
import us.careydevelopment.util.webcontent.model.YouTubeVideo;
import us.careydevelopment.util.webcontent.repository.RedditVideoRepository;
import us.careydevelopment.util.webcontent.repository.YouTubeVideoRepository;


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

    
    private static WebContentService WEB_CONTENT_SERVICE;
    
    @Autowired
    private YouTubeVideoRepository youTubeVideoRepository;
        
    @Autowired
    private RedditVideoRepository redditVideoRepository;
    
    
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
}
