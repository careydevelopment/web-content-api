package us.careydevelopment.util.ip.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Config {

    static {
        System.err.println("Got in hereeee");
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(MongoConfig.class);
        ctx.refresh();
    }
    
//    public static void main(String[] args) {
//        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
//        ctx.register(MongoConfig.class);
//        ctx.refresh();
//    }
}
