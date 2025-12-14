package sunshine.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * WMO 날씨 코드를 한글 설명으로 변환하는 enum
 */
public enum WeatherCode {

    CLEAR(0, "맑음"),
    MAINLY_CLEAR(1, "대체로 맑음"),
    PARTLY_CLOUDY(2, "구름 조금"),
    OVERCAST(3, "흐림"),
    FOG(45, "안개"),
    DEPOSITING_RIME_FOG(48, "서리 안개"),
    DRIZZLE_LIGHT(51, "가벼운 이슬비"),
    DRIZZLE_MODERATE(53, "이슬비"),
    DRIZZLE_DENSE(55, "강한 이슬비"),
    RAIN_LIGHT(61, "가벼운 비"),
    RAIN_MODERATE(63, "비"),
    RAIN_HEAVY(65, "강한 비"),
    SNOW_LIGHT(71, "가벼운 눈"),
    SNOW_MODERATE(73, "눈"),
    SNOW_HEAVY(75, "강한 눈"),
    SNOW_GRAINS(77, "싸락눈"),
    RAIN_SHOWERS_LIGHT(80, "가벼운 소나기"),
    RAIN_SHOWERS_MODERATE(81, "소나기"),
    RAIN_SHOWERS_VIOLENT(82, "강한 소나기"),
    THUNDERSTORM(95, "뇌우"),
    THUNDERSTORM_HAIL_LIGHT(96, "뇌우와 약한 우박"),
    THUNDERSTORM_HAIL_HEAVY(99, "뇌우와 강한 우박");

    private static final Map<Integer, WeatherCode> CODE_MAP = new HashMap<>();
    private static final String DEFAULT_DESCRIPTION = "알 수 없음";

    static {
        for (WeatherCode code : values()) {
            CODE_MAP.put(code.code, code);
        }
    }

    private final int code;
    private final String description;

    WeatherCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * WMO 코드로 날씨 설명을 조회한다
     */
    public static String getDescription(int code) {
        WeatherCode weatherCode = CODE_MAP.get(code);
        if (weatherCode == null) {
            return DEFAULT_DESCRIPTION;
        }
        return weatherCode.description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
