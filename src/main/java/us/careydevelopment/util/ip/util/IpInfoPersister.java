package us.careydevelopment.util.ip.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.careydevelopment.util.ip.model.CityBlocksIpv4;
import us.careydevelopment.util.ip.model.GeoInfo;
import us.careydevelopment.util.ip.service.IpService;

/**
 * I use this class to update the database from time to time.
 * 
 * IP Info comes from MaxMind.
 * 
 * https://www.maxmind.com
 * 
 * @author Brian Carey
 */
public class IpInfoPersister {

    private static final String IPV4_DATA = "./GeoLite2-City-Blocks-IPv4.csv";
    private static final String GEO_DATA = "./GeoLite2-City-Locations-en.csv";
    
    private IpService service = IpService.getIpService();
    
    
    public static void main(String[] args) {
        IpInfoPersister persister = new IpInfoPersister();
        persister.go();
    }
    
    
    private void go() {
        List<GeoInfo> geos = getGeoInfo();        
        persistIpv4Data(geos);
    }

    
    private List<GeoInfo> getGeoInfo() {
        List<GeoInfo> geos = new ArrayList<>();
        
        Path path = Paths.get(GEO_DATA);
        
        try (Stream<String> stream = Files.lines(path)) {
            geos = stream.map(line -> {
              GeoInfo geo = getGeoInfoFromLine(line);
              //System.err.println(geo);
              return geo;
            }).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return geos;
    }
    
    
    private void persistIpv4Data(List<GeoInfo> geos) {
        Path path = Paths.get(IPV4_DATA);
        
        try (Stream<String> stream = Files.lines(path)) {
            stream
                .skip(3000001)
                //.limit(500000)
                .parallel()
                .map(line -> {         
                    CityBlocksIpv4 ip = getIpFromLine(line, geos);
                    System.err.println("Retrieved " + ip);  
                    return ip;
                }) 
                .filter(ip -> ip.getGeoInfo() != null)
                .forEach(ip -> {
                    service.persistIpv4(ip);
                });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    private GeoInfo getGeoInfoFromLine(String line) {
        GeoInfo geo = new GeoInfo();
        
        String[] parts = line.split(",");
        String geoNameId = parts[0];
        String localeCode = parts[1];
        String continentCode = parts[2];
        String continentName = stripQuotes(parts[3]);
        String countryIsoCode = parts[4];
        String countryName = stripQuotes(parts[5]);
        String cityName = stripQuotes(parts[10]);
        String timeZone = parts[12];
        
        String isInEuInd = parts[13];
        boolean isInEu = "1".equals(isInEuInd); 
       
        geo.setCityName(cityName);
        geo.setContinentCode(continentCode);
        geo.setContinentName(continentName);
        geo.setCountryIsoCode(countryIsoCode);
        geo.setCountryName(countryName);
        geo.setGeoNameId(geoNameId);
        geo.setIsInEuropeanUnion(isInEu);
        geo.setLocaleCode(localeCode);
        geo.setTimeZone(timeZone);
        
        return geo;
    }
    
    
    private CityBlocksIpv4 getIpFromLine(String line, List<GeoInfo> geos) {
        CityBlocksIpv4 ip = new CityBlocksIpv4();
        
        String[] parts = line.split(",");
        String network = parts[0];
        String ipAddress = getIpAddressFromNetwork(network);
   
        String geoNameId = parts[1];
        Optional<GeoInfo> geoOpt = geos
                                    .stream()
                                    .filter(geo -> geo.getGeoNameId().equals(geoNameId))
                                    .findFirst();
        
        if (geoOpt.isPresent()) {
            ip.setGeoInfo(geoOpt.get());
            ip.setNetwork(network);
            ip.setIpAddress(ipAddress);
        }
        
        return ip;
    }
    
    
    private String getIpAddressFromNetwork(String network) {
        String ipAddress = network;
        
        if (network != null) {
            int slash = network.indexOf("/");
            
            if (slash > -1) {
                ipAddress = network.substring(0, slash);
            }
        }
        
        return ipAddress;
    }
    
    
    private String stripQuotes(String orig) {
        String updated = orig;
        
        if (orig != null) {
            int quote = orig.indexOf("\"");
            
            if (quote == 0) {
                updated = orig.substring(1, orig.length() - 1);
            }
        }
        
        return updated;
    }
}
