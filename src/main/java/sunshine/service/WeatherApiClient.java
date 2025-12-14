package sunshine.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import sunshine.dto.WeatherApiResponse;

/**
 * Open-Meteo API 클라이언트
 * 좌표를 기반으로 현재 날씨 정보를 조회한다
 */
@Component
public class WeatherApiClient {

    private static final String BASE_URL = "https://api.open-meteo.com/v1/forecast";
    private static final String CURRENT_PARAMS = "temperature_2m,apparent_temperature,relative_humidity_2m,weather_code";
    private final RestClient restClient;

    public WeatherApiClient(RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * 좌표로 현재 날씨를 조회한다
     *
     * @param latitude  위도
     * @param longitude 경도
     * @return 날씨 정보
     */
    public WeatherApiResponse getWeather(double latitude, double longitude) {
        return restClient.get()
            .uri(BASE_URL + "?latitude={lat}&longitude={lon}&current={params}",
                latitude, longitude, CURRENT_PARAMS)
            .retrieve()
            .body(WeatherApiResponse.class);
    }
}
