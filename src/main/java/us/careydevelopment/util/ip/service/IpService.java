package us.careydevelopment.util.ip.service;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import us.careydevelopment.util.ip.config.MongoConfig;

public class IpService {

    static {
        System.err.println("Got in hereeee");
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(MongoConfig.class);
        ctx.refresh();
    }
}
