package sunshine.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sunshine.dto.GeocodingApiResponse;
import sunshine.dto.WeatherApiResponse;
import sunshine.dto.WeatherResponse;
import sunshine.exception.CityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private GeocodingApiClient geocodingApiClient;

    @Mock
    private WeatherApiClient weatherApiClient;

    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        weatherService = new WeatherService(geocodingApiClient, weatherApiClient);
    }

    @Test
    @DisplayName("도시명으로 날씨 정보를 조회한다")
    void getWeather_success() {
        // given
        String cityName = "Seoul";
        GeocodingApiResponse coordinates = createCoordinates("37.5665", "126.9780");
        WeatherApiResponse weather = createWeatherResponse(3.4, 1.2, 65, 3);

        when(geocodingApiClient.getCoordinates(cityName)).thenReturn(coordinates);
        when(weatherApiClient.getWeather(37.5665, 126.9780)).thenReturn(weather);

        // when
        WeatherResponse response = weatherService.getWeather(cityName);

        // then
        assertThat(response.getCity()).isEqualTo("Seoul");
        assertThat(response.getTemperature()).isEqualTo(3.4);
        assertThat(response.getApparentTemperature()).isEqualTo(1.2);
        assertThat(response.getHumidity()).isEqualTo(65);
        assertThat(response.getSkyCondition()).isEqualTo("흐림");
        assertThat(response.getSummary()).contains("Seoul", "3.4", "1.2", "흐림", "65");
    }

    @Test
    @DisplayName("존재하지 않는 도시를 조회하면 예외가 발생한다")
    void getWeather_cityNotFound() {
        // given
        String cityName = "UnknownCity";
        when(geocodingApiClient.getCoordinates(cityName)).thenReturn(null);

        // when & then
        assertThatThrownBy(() -> weatherService.getWeather(cityName))
            .isInstanceOf(CityNotFoundException.class)
            .hasMessageContaining("UnknownCity");
    }

    private GeocodingApiResponse createCoordinates(String lat, String lon) {
        return new GeocodingApiResponse(lat, lon, "Test Location");
    }

    private WeatherApiResponse createWeatherResponse(double temp, double apparent,
                                                      int humidity, int weatherCode) {
        WeatherApiResponse.Current current = new WeatherApiResponse.Current(
            temp, apparent, humidity, weatherCode
        );
        return new WeatherApiResponse(current);
    }
}
