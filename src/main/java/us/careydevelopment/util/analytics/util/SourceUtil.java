package us.careydevelopment.util.analytics.util;

public class SourceUtil {

    public static void main(String[] args) {
        System.err.println(getBrandNameFromUrl("http://www.careydevelopment.com.mx/blog"));
    }
    
    public static String getDomainNameFromUrl(String url) {
        String domainName = null;

        if (url != null) {
            int httpIndex = url.indexOf("http://");
            
            if (httpIndex > -1) {
                url = url.substring(7);
            }
            
            int httpsIndex = url.indexOf("https://");
            if (httpsIndex > -1) {
                url = url.substring(8);
            }
            
            int slashIndex = url.indexOf("/");
            if (slashIndex > -1) {
                url = url.substring(0, slashIndex);
            }
            
            domainName = url;
        }

        return domainName;
    }
    
    
    public static String getBrandNameFromUrl(String url) {
        String brandName = "Other";

        if (url != null) {
            String domainName = getDomainNameFromUrl(url);
            
            if (domainName != null) {
                if (domainName.startsWith("www.")) {
                    domainName = domainName.substring(4);
                }

                String[] parts = domainName.split("\\.");
                                
                if (parts != null) {
                    if (parts.length == 2) {
                        brandName = parts[0];
                    } else {
                        int comLoc = findInArray("com", parts);
                        if (comLoc > 0) {
                            brandName = parts[comLoc - 1];
                        }
                    }
                }
            }
        }

        return brandName;
    }
    
    
    private static int findInArray(String needle, String[] haystack) {
        int loc = -1;
        
        for (int i=0; i< haystack.length; i++) {
            String s = haystack[i];
            
            if (s.trim().equals(needle.trim())) {
                loc = i;
                break;
            }
        }
        
        return loc;
    }
}
