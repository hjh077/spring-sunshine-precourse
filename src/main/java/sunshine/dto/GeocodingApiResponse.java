package sunshine.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Nominatim Geocoding API 응답 DTO
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodingApiResponse {

    private String lat;
    private String lon;
    private String displayName;

    public GeocodingApiResponse() {
    }

    public GeocodingApiResponse(String lat, String lon, String displayName) {
        this.lat = lat;
        this.lon = lon;
        this.displayName = displayName;
    }

    public double getLatitude() {
        return Double.parseDouble(lat);
    }

    public double getLongitude() {
        return Double.parseDouble(lon);
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
