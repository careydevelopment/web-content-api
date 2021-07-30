package us.careydevelopment.util.ip.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;


@Configuration
@ComponentScan("us.careydevelopment.util.ip")
@EnableMongoRepositories(basePackages= {"us.careydevelopment.util.ip.repository"})
public class MongoConfig extends AbstractMongoClientConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(MongoConfig.class);
    
    @Value("${mongo.db}")
    private String mongoConnection;// = "mongodb://admin:ur5opttz9@40.121.23.23:39199/ipDB";

    @Value("${mongo.db.name}")
    private String mongoDatabaseName;// = "ipDB";
    
    @Override
    protected String getDatabaseName() {
        return mongoDatabaseName;
    }
  
    
    @Override
    @Bean
    public MongoClient mongoClient() {
    	MongoClient client = MongoClients.create(mongoConnection);
        return client;
    }

    
    public @Bean MongoTemplate mongoTemplate() {
        System.err.println("got in here 2 " + mongoConnection);

        return new MongoTemplate(mongoClient(), "ipDB");
    }
}
