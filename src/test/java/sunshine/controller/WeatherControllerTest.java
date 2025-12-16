package sunshine.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import sunshine.dto.WeatherResponse;
import sunshine.exception.CityNotFoundException;
import sunshine.service.WeatherService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherController.class)
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    @DisplayName("도시명으로 날씨를 조회하면 200 OK와 날씨 정보를 반환한다")
    void getWeather_success() throws Exception {
        // given
        String locationName = "Seoul";
        WeatherResponse response = createWeatherResponse(locationName);
        when(weatherService.getWeather(locationName, false)).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/weather").param("city", locationName))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.city").value("Seoul"))
            .andExpect(jsonPath("$.temperature").value(3.4))
            .andExpect(jsonPath("$.apparentTemperature").value(1.2))
            .andExpect(jsonPath("$.humidity").value(65))
            .andExpect(jsonPath("$.skyCondition").value("흐림"))
            .andExpect(jsonPath("$.summary").exists());
    }

    @Test
    @DisplayName("존재하지 않는 도시를 조회하면 404 Not Found를 반환한다")
    void getWeather_notFound() throws Exception {
        // given
        String locationName = "UnknownCity";
        when(weatherService.getWeather(locationName, false))
            .thenThrow(new CityNotFoundException(locationName));

        // when & then
        mockMvc.perform(get("/api/weather").param("city", locationName))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").value("CITY_NOT_FOUND"))
            .andExpect(jsonPath("$.city").value("UnknownCity"));
    }

    private WeatherResponse createWeatherResponse(String locationName) {
        return WeatherResponse.builder()
            .city(locationName)
            .latitude(37.5665)
            .longitude(126.9780)
            .temperature(3.4)
            .apparentTemperature(1.2)
            .humidity(65)
            .skyCondition("흐림")
            .summary("현재 Seoul의 기온은 3.4°C이며, 체감온도는 1.2°C입니다.")
            .build();
    }
}
