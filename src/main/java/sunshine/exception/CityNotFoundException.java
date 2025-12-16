package sunshine.exception;

/**
 * 도시를 찾을 수 없을 때 발생하는 예외
 */
public class CityNotFoundException extends RuntimeException {

    private final String locationName;

    public CityNotFoundException(String locationName) {
        super("도시를 찾을 수 없습니다: " + locationName);
        this.locationName = locationName;
    }

    public String getLocationName() {
        return locationName;
    }
}
