package us.careydevelopment.util.ip.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import us.careydevelopment.util.ip.model.CityBlocksIpv4;

@Repository
public interface CityBlocksIpv4Repository extends MongoRepository<CityBlocksIpv4, String> {
    
}
