package us.careydevelopment.util.webcontent.util;

import java.util.List;

public class UrlChecker {

    private static List<String> PROHIBITED_DOMAINS = List.of(
                                                        "usnews.com",
                                                        "youtube.com",
                                                        "seattletimes.com",
                                                        "washingtonpost.com",
                                                        "nytimes.com"
                                                        );
    
    
    public static boolean urlProhibited(String url) {
        boolean prohibited = false;

        if (url != null) {
            for (String domain : PROHIBITED_DOMAINS) {
                if (url.indexOf(domain) > -1) {
                    prohibited = true;
                    break;
                }
            }            
        } else {
            prohibited = true;
        }
        
        return prohibited;
    }
}
