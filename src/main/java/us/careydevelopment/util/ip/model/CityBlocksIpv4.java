package us.careydevelopment.util.ip.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class CityBlocksIpv4 {

    private String ipAddress;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
