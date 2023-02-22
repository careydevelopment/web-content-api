package us.careydevelopment.util.webcontent.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;


/**
 * Handles configuration for  MongoDB.
 * 
 * Properties are set in the application.properties file
 * 
 * @author Brian Carey
 *
 */
@Configuration
@ComponentScan("us.careydevelopment.util.webcontent")
@EnableMongoRepositories(basePackages= {"us.careydevelopment.util.webcontent.repository"})
public class MongoConfig extends AbstractMongoClientConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(MongoConfig.class);
    
    @Value("${mongodb.webcontent.db}")
    private String mongoConnection;
 
    @Value("${mongodb.webcontent.name}")
    private String mongoDatabaseName;
    
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
        return new MongoTemplate(mongoClient(), mongoDatabaseName);
    }
}
