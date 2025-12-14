package sunshine.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import sunshine.dto.GeocodingApiResponse;

import java.util.List;

/**
 * Nominatim Geocoding API 클라이언트
 * 도시명을 좌표(위도, 경도)로 변환한다
 */
@Component
public class GeocodingApiClient {

    private static final String BASE_URL = "https://nominatim.openstreetmap.org/search";
    private final RestClient restClient;

    public GeocodingApiClient(RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * 도시명으로 좌표를 조회한다
     *
     * @param cityName 도시명
     * @return 좌표 정보 (없으면 null)
     */
    public GeocodingApiResponse getCoordinates(String cityName) {
        List<GeocodingApiResponse> results = restClient.get()
            .uri(BASE_URL + "?q={city}&format=json&limit=1", cityName)
            .retrieve()
            .body(new ParameterizedTypeReference<>() {});

        if (results == null || results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }
}
