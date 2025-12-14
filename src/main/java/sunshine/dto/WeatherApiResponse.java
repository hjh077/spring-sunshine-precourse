package sunshine.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Open-Meteo API 응답 DTO
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherApiResponse {

    private Current current;

    public WeatherApiResponse() {
    }

    public WeatherApiResponse(Current current) {
        this.current = current;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    /**
     * 현재 날씨 정보
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Current {

        @JsonProperty("temperature_2m")
        private double temperature;

        @JsonProperty("apparent_temperature")
        private double apparentTemperature;

        @JsonProperty("relative_humidity_2m")
        private int relativeHumidity;

        @JsonProperty("weather_code")
        private int weatherCode;

        public Current() {
        }

        public Current(double temperature, double apparentTemperature,
                       int relativeHumidity, int weatherCode) {
            this.temperature = temperature;
            this.apparentTemperature = apparentTemperature;
            this.relativeHumidity = relativeHumidity;
            this.weatherCode = weatherCode;
        }

        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }

        public double getApparentTemperature() {
            return apparentTemperature;
        }

        public void setApparentTemperature(double apparentTemperature) {
            this.apparentTemperature = apparentTemperature;
        }

        public int getRelativeHumidity() {
            return relativeHumidity;
        }

        public void setRelativeHumidity(int relativeHumidity) {
            this.relativeHumidity = relativeHumidity;
        }

        public int getWeatherCode() {
            return weatherCode;
        }

        public void setWeatherCode(int weatherCode) {
            this.weatherCode = weatherCode;
        }
    }
}
