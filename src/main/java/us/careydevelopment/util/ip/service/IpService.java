package us.careydevelopment.util.ip.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import us.careydevelopment.util.ip.config.IpApiConfig;
import us.careydevelopment.util.ip.model.CityBlocksIpv4;
import us.careydevelopment.util.ip.repository.CityBlocksIpv4Repository;


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
public class IpService {

    private static IpService IP_SERVICE;
    
    @Autowired
    private CityBlocksIpv4Repository ipv4Repo;
        
    
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
     * @return IpService singleton
     */
    public static IpService getIpService() {
        if (IP_SERVICE == null) {
            ApplicationContext context = new AnnotationConfigApplicationContext(IpApiConfig.class);
            IP_SERVICE = context.getBean(IpService.class);
        }
        
        return IP_SERVICE;
    }
    
    
    /**
     * Saves an IPv4 IP address object.
     * 
     * @param ip
     */
    public void persistIpv4(CityBlocksIpv4 ip) {        
        ipv4Repo.save(ip);
    }
    
    
    /**
     * Searches for a match based on IP address
     * 
     * Will perform a Class C search if no matches on full IP address.
     * 
     * @param ipAddress - full IP address
     * @return - list of matching GeoInfo objects
     */
    public List<CityBlocksIpv4> findByIpAddress(String ipAddress) { 
        List<CityBlocksIpv4> list = new ArrayList<>();
        
        if (ipAddress != null) {
            list = ipv4Repo.findByIpAddress(ipAddress);
            
            if (list == null || list.size() == 0) {
                return findByClassC(ipAddress);
            }   
        }
        
        return list;
    }
    
    
    /**
     * Accepts a full IP address and searches for matches based on the first three numbers.
     * 
     * It's a search by Class C network, where everything before the last period is part of the same
     * network.
     * 
     * @param ipAddress - full IP address
     * @return - list of matching GeoInfo objects
     */
    public List<CityBlocksIpv4> findByClassC(String ipAddress) {
        List<CityBlocksIpv4> list = new ArrayList<>();
        
        if (ipAddress != null) {
            int lastPeriod = ipAddress.lastIndexOf(".");
            
            if (lastPeriod > -1) {
                String firstThreeNumbers = ipAddress.substring(0, lastPeriod);
                list = ipv4Repo.findByClassC(firstThreeNumbers);
            }
        }
        
        return list;
    }
}
