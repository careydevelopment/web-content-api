package us.careydevelopment.util.ip.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class GeoInfo {

    private String geoNameId;
    private String localeCode;
    private String continentCode;
    private String continentName;
    private String countryIsoCode;
    private String countryName;
    private String cityName;
    private String timeZone;
    private Boolean isInEuropeanUnion;
    
    
    public String getGeoNameId() {
        return geoNameId;
    }
    public void setGeoNameId(String geoNameId) {
        this.geoNameId = geoNameId;
    }
    public String getLocaleCode() {
        return localeCode;
    }
    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
    }
    public String getContinentCode() {
        return continentCode;
    }
    public void setContinentCode(String continentCode) {
        this.continentCode = continentCode;
    }
    public String getContinentName() {
        return continentName;
    }
    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }
    public String getCountryIsoCode() {
        return countryIsoCode;
    }
    public void setCountryIsoCode(String countryIsoCode) {
        this.countryIsoCode = countryIsoCode;
    }
    public String getCountryName() {
        return countryName;
    }
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getTimeZone() {
        return timeZone;
    }
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
    public Boolean getIsInEuropeanUnion() {
        return isInEuropeanUnion;
    }
    public void setIsInEuropeanUnion(Boolean isInEuropeanUnion) {
        this.isInEuropeanUnion = isInEuropeanUnion;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((geoNameId == null) ? 0 : geoNameId.hashCode());
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
        GeoInfo other = (GeoInfo) obj;
        if (geoNameId == null) {
            if (other.getGeoNameId() != null)
                return false;
        } else if (!geoNameId.equals(other.getGeoNameId()))
            return false;
        return true;
    }
    
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
