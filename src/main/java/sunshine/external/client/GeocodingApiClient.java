package sunshine.external.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import sunshine.dto.GeocodingApiResponse;
import sunshine.dto.GoogleGeocodingResponse;

/**
 * Google Geocoding API 클라이언트
 * 도시명을 좌표(위도, 경도)로 변환한다
 */
@Component
public class GeocodingApiClient {

    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json";
    private final RestClient restClient;
    private final String apiKey;

    public GeocodingApiClient(RestClient restClient,
                              @Value("${spring.application.geocoding}") String apiKey) {
        this.restClient = restClient;
        this.apiKey = apiKey;
    }

    /**
     * 도시명으로 좌표를 조회한다
     *
     * @param locationName 도시명
     * @return 좌표 정보 (없으면 null)
     */
    public GeocodingApiResponse getCoordinates(String locationName) {
        GoogleGeocodingResponse response = restClient.get()
            .uri(BASE_URL + "?address={address}&key={key}", locationName, apiKey)
            .retrieve()
            .body(GoogleGeocodingResponse.class);

        if (response == null || response.getResults() == null || response.getResults().isEmpty()) {
            return null;
        }

        GoogleGeocodingResponse.Result result = response.getResults().get(0);
        GoogleGeocodingResponse.Location location = result.getGeometry().getLocation();

        return new GeocodingApiResponse(
            String.valueOf(location.getLat()),
            String.valueOf(location.getLng()),
            result.getFormattedAddress()
        );
    }
}
