package sunshine.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Google Geocoding API 응답 DTO
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleGeocodingResponse {

    private List<Result> results;
    private String status;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result {
        @JsonProperty("formatted_address")
        private String formattedAddress;
        private Geometry geometry;

        public String getFormattedAddress() {
            return formattedAddress;
        }

        public void setFormattedAddress(String formattedAddress) {
            this.formattedAddress = formattedAddress;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Geometry {
        private Location location;

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Location {
        private double lat;
        private double lng;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }
}
