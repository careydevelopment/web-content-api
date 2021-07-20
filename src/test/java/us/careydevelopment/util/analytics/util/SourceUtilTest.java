package us.careydevelopment.util.analytics.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SourceUtilTest {

    @Test
    public void testGetDomainNameFromUrlWithHttps() {
        String domainName = SourceUtil.getDomainNameFromUrl("https://careydevelopment.us/blog");
        Assertions.assertEquals("careydevelopment.us", domainName);
    }
    
    @Test
    public void testGetDomainNameFromUrlWithHttp() {
        String domainName = SourceUtil.getDomainNameFromUrl("http://careydevelopment.us/blog");
        Assertions.assertEquals("careydevelopment.us", domainName);
    }
    
    @Test
    public void testGetDomainNameFromUrlWithNoTrailingSlash() {
        String domainName = SourceUtil.getDomainNameFromUrl("http://careydevelopment.us");
        Assertions.assertEquals("careydevelopment.us", domainName);
    }
    
    @Test
    public void testGetBrandNameFromUrlTwoParts() {
        String domainName = SourceUtil.getBrandNameFromUrl("https://careydevelopment.us/blog");
        Assertions.assertEquals("careydevelopment", domainName);
    }
    
    
    @Test
    public void testGetBrandNameFromUrlWithWww() {
        String domainName = SourceUtil.getBrandNameFromUrl("https://www.careydevelopment.us/blog");
        Assertions.assertEquals("careydevelopment", domainName);
    }
    
    
    @Test
    public void testGetBrandNameFromUrlWithThreeParts() {
        String domainName = SourceUtil.getBrandNameFromUrl("https://cn.bing.com");
        Assertions.assertEquals("bing", domainName);
    }
}
