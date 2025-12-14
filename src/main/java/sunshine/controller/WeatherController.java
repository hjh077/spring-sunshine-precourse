package sunshine.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sunshine.dto.WeatherResponse;
import sunshine.service.WeatherService;

/**
 * 날씨 조회 REST API 컨트롤러
 */
@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * 도시명으로 날씨를 조회한다
     *
     * @param city 도시명
     * @return 날씨 정보
     */
    @GetMapping
    public ResponseEntity<WeatherResponse> getWeather(@RequestParam String city) {
        WeatherResponse response = weatherService.getWeather(city);
        return ResponseEntity.ok(response);
    }
}
