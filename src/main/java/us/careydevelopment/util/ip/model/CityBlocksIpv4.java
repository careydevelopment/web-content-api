package us.careydevelopment.util.ip.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Forgive the awkward class name. It's borrowed from the dataset provided by
 * MaxMind.
 * 
 * This object includes details about an IPv4 address.
 * 
 * @author Brian Carey
 *
 */
@Document(collection = "#{@environment.getProperty('mongo.ipv4.collection')}")
public class CityBlocksIpv4 {

    private String ipAddress;
    private String network;
    private GeoInfo geoInfo;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public GeoInfo getGeoInfo() {
        return geoInfo;
    }

    public void setGeoInfo(GeoInfo geoInfo) {
        this.geoInfo = geoInfo;
    }

    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((network == null) ? 0 : network.hashCode());
        return result;
    }

    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CityBlocksIpv4 other = (CityBlocksIpv4) obj;
        if (network == null) {
            if (other.getNetwork() != null)
                return false;
        } else if (!network.equals(other.getNetwork()))
            return false;
        return true;
    }
    
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
