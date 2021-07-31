package us.careydevelopment.util.ip.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import us.careydevelopment.util.ip.model.CityBlocksIpv4;

@Repository
public interface CityBlocksIpv4Repository extends MongoRepository<CityBlocksIpv4, String> {

    /**
     * Search by IP address - exact match.
     * 
     * @param ipAddress
     * @return list of ip address objects that match
     */
    public List<CityBlocksIpv4> findByIpAddress(String ipAddress);
    
    
    /**
     * Searches for a match based on Class C network.
     * 
     * That means it will search based on the first three numbers in an
     * IPv4 IP address (e.g., 192.4.5).
     * 
     * The method accepts a String that includes only the first three 
     * numbers (e.g., 192.4.5).
     * 
     * @param ipAddress - first three digits of the IP address
     * @return list of IP addresses that match
     */
    @Query("{ 'ipAddress': { '$regex' : /^?0/ }}") 
    public List<CityBlocksIpv4> findByClassC(String ipAddress);   
}
