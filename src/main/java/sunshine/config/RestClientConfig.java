package sunshine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * REST API 호출을 위한 RestClient 설정
 */
@Configuration
public class RestClientConfig {

    private static final String USER_AGENT = "SpringSunshineApp/1.0";

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
            .defaultHeader("User-Agent", USER_AGENT)
            .build();
    }
}
