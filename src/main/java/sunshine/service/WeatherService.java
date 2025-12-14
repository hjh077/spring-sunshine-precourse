package sunshine.service;

import org.springframework.stereotype.Service;
import sunshine.domain.WeatherCode;
import sunshine.dto.GeocodingApiResponse;
import sunshine.dto.WeatherApiResponse;
import sunshine.dto.WeatherResponse;
import sunshine.exception.CityNotFoundException;

/**
 * 날씨 조회 서비스
 */
@Service
public class WeatherService {

    private final GeocodingApiClient geocodingApiClient;
    private final WeatherApiClient weatherApiClient;

    public WeatherService(GeocodingApiClient geocodingApiClient,
                          WeatherApiClient weatherApiClient) {
        this.geocodingApiClient = geocodingApiClient;
        this.weatherApiClient = weatherApiClient;
    }

    /**
     * 도시명으로 날씨 정보를 조회한다
     *
     * @param cityName 도시명
     * @return 날씨 응답
     */
    public WeatherResponse getWeather(String cityName) {
        GeocodingApiResponse coordinates = getCoordinatesOrThrow(cityName);
        WeatherApiResponse weather = weatherApiClient.getWeather(
            coordinates.getLatitude(),
            coordinates.getLongitude()
        );
        return buildWeatherResponse(cityName, coordinates, weather);
    }

    private GeocodingApiResponse getCoordinatesOrThrow(String cityName) {
        GeocodingApiResponse coordinates = geocodingApiClient.getCoordinates(cityName);
        if (coordinates == null) {
            throw new CityNotFoundException(cityName);
        }
        return coordinates;
    }

    private WeatherResponse buildWeatherResponse(String cityName,
                                                  GeocodingApiResponse coordinates,
                                                  WeatherApiResponse weather) {
        WeatherApiResponse.Current current = weather.getCurrent();
        String skyCondition = WeatherCode.getDescription(current.getWeatherCode());

        return WeatherResponse.builder()
            .city(cityName)
            .latitude(coordinates.getLatitude())
            .longitude(coordinates.getLongitude())
            .temperature(current.getTemperature())
            .apparentTemperature(current.getApparentTemperature())
            .humidity(current.getRelativeHumidity())
            .skyCondition(skyCondition)
            .summary(createSummary(cityName, current, skyCondition))
            .build();
    }

    private String createSummary(String cityName,
                                  WeatherApiResponse.Current current,
                                  String skyCondition) {
        return String.format(
            "현재 %s의 기온은 %.1f°C이며, 체감온도는 %.1f°C입니다. 날씨는 %s이고, 습도는 %d%%입니다.",
            cityName,
            current.getTemperature(),
            current.getApparentTemperature(),
            skyCondition,
            current.getRelativeHumidity()
        );
    }
}
