package sunshine.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;
import study.JokeController;
import sunshine.domain.WeatherCode;
import sunshine.dto.GeocodingApiResponse;
import sunshine.dto.WeatherApiResponse;
import sunshine.dto.WeatherResponse;
import sunshine.exception.CityNotFoundException;
import sunshine.external.client.GeocodingApiClient;
import sunshine.external.client.WeatherApiClient;

import java.util.Map;

/**
 * 날씨 조회 서비스
 */
@Service
public class WeatherService {

    private static final Logger log = LoggerFactory.getLogger(JokeController.class);
    private final ChatClient client;
    private final GeocodingApiClient geocodingApiClient;
    private final WeatherApiClient weatherApiClient;

    public WeatherService(ChatClient.Builder builder, GeocodingApiClient geocodingApiClient,
                          WeatherApiClient weatherApiClient) {
        this.client = builder.build();
        this.geocodingApiClient = geocodingApiClient;
        this.weatherApiClient = weatherApiClient;
    }

    /**
     * 도시명으로 날씨 정보를 조회한다
     *
     * @param locationName 도시명
     * @return 날씨 응답
     */
    public WeatherResponse getWeather(String locationName, Boolean onAiResponse) {
        GeocodingApiResponse coordinates = getCoordinatesOrThrow(locationName);
        WeatherApiResponse weather = weatherApiClient.getWeather(
            coordinates.getLatitude(),
            coordinates.getLongitude()
        );

        if (onAiResponse) return buildWeatherResponseByAI(locationName, coordinates, weather);

        return buildWeatherResponse(locationName, coordinates, weather);
    }

    private GeocodingApiResponse getCoordinatesOrThrow(String locationName) {
        GeocodingApiResponse coordinates = geocodingApiClient.getCoordinates(locationName);
        if (coordinates == null) {
            throw new CityNotFoundException(locationName);
        }
        return coordinates;
    }

    private WeatherResponse buildWeatherResponse(String locationName,
                                                  GeocodingApiResponse coordinates,
                                                  WeatherApiResponse weather) {
        WeatherApiResponse.Current current = weather.getCurrent();
        String skyCondition = WeatherCode.getDescription(current.getWeatherCode());

        return WeatherResponse.builder()
            .city(locationName)
            .latitude(coordinates.getLatitude())
            .longitude(coordinates.getLongitude())
            .temperature(current.getTemperature())
            .apparentTemperature(current.getApparentTemperature())
            .humidity(current.getRelativeHumidity())
            .skyCondition(skyCondition)
            .summary(createSummary(locationName, current, skyCondition))
            .build();
    }

    private WeatherResponse buildWeatherResponseByAI(String locationName,
                                                 GeocodingApiResponse coordinates,
                                                 WeatherApiResponse weather) {
        WeatherApiResponse.Current current = weather.getCurrent();
        String skyCondition = WeatherCode.getDescription(current.getWeatherCode());

        return WeatherResponse.builder()
                .city(locationName)
                .latitude(coordinates.getLatitude())
                .longitude(coordinates.getLongitude())
                .temperature(current.getTemperature())
                .apparentTemperature(current.getApparentTemperature())
                .humidity(current.getRelativeHumidity())
                .skyCondition(skyCondition)
                .summary(createSummaryByAI(createSummary(locationName, current, skyCondition)))
                .build();
    }

    private String createSummary(String locationName,
                                  WeatherApiResponse.Current current,
                                  String skyCondition) {
        return String.format(
            "현재 %s의 기온은 %.1f°C이며, 체감온도는 %.1f°C입니다. 날씨는 %s이고, 습도는 %d%%입니다.",
            locationName,
            current.getTemperature(),
            current.getApparentTemperature(),
            skyCondition,
            current.getRelativeHumidity()
        );
    }

    private String createSummaryByAI(String weatherCondition) {
        var template = new PromptTemplate("다음 지역의 날씨 컨디션을 참고로 복장을 추천해주되, 요약해서 3문장으로 응답해줘. {weatherCondition}");
        var prompt = template.render(Map.of("weatherCondition", weatherCondition));

        ChatResponse chatResponse = client.prompt(prompt)
                .call()
                .chatResponse();
        log.info("useage: {}", chatResponse);

        if (chatResponse.getResult() == null) return "None AI Response";
        log.info("text: {}", chatResponse.getResult().getOutput().getText());

        return chatResponse.getResult().getOutput().getText();
    }

}
