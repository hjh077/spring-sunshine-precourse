package sunshine.exception;

/**
 * 도시를 찾을 수 없을 때 발생하는 예외
 */
public class CityNotFoundException extends RuntimeException {

    private final String cityName;

    public CityNotFoundException(String cityName) {
        super("도시를 찾을 수 없습니다: " + cityName);
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }
}
