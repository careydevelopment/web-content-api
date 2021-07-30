package us.careydevelopment.util.ip.service;

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
    CityBlocksIpv4Repository repo;
    
    public static void main(String[] args) {
        IpService s = IpService.getIpService();
        s.persist();
    }
    
    public void persist() {
        CityBlocksIpv4 c = new CityBlocksIpv4();
        c.setIpAddress("1.1.1.1");
        
//        this.repo.save(c);
//        
//        this.repo.findAll().forEach(ip -> {
//            System.err.println(ip);
//        });;
        
//        this.repo.deleteAll();
    }
    
    public static IpService getIpService() {
        if (IP_SERVICE == null) {
            ApplicationContext context = new AnnotationConfigApplicationContext(IpApiConfig.class);
            IP_SERVICE = context.getBean(IpService.class);
        }
        
        return IP_SERVICE;
    }

}
