package sunshine.dto;

/**
 * 클라이언트에게 반환하는 날씨 응답 DTO
 */
public class WeatherResponse {

    private String city;
    private double latitude;
    private double longitude;
    private double temperature;
    private double apparentTemperature;
    private int humidity;
    private String skyCondition;
    private String summary;

    public WeatherResponse() {
    }

    private WeatherResponse(Builder builder) {
        this.city = builder.city;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
        this.temperature = builder.temperature;
        this.apparentTemperature = builder.apparentTemperature;
        this.humidity = builder.humidity;
        this.skyCondition = builder.skyCondition;
        this.summary = builder.summary;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getCity() {
        return city;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getApparentTemperature() {
        return apparentTemperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public String getSkyCondition() {
        return skyCondition;
    }

    public String getSummary() {
        return summary;
    }

    /**
     * WeatherResponse 빌더
     */
    public static class Builder {

        private String city;
        private double latitude;
        private double longitude;
        private double temperature;
        private double apparentTemperature;
        private int humidity;
        private String skyCondition;
        private String summary;

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder latitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder longitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder temperature(double temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder apparentTemperature(double apparentTemperature) {
            this.apparentTemperature = apparentTemperature;
            return this;
        }

        public Builder humidity(int humidity) {
            this.humidity = humidity;
            return this;
        }

        public Builder skyCondition(String skyCondition) {
            this.skyCondition = skyCondition;
            return this;
        }

        public Builder summary(String summary) {
            this.summary = summary;
            return this;
        }

        public WeatherResponse build() {
            return new WeatherResponse(this);
        }
    }
}
