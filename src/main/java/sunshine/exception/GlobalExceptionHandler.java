package sunshine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 전역 예외 처리 핸들러
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 도시를 찾을 수 없을 때 예외 처리
     */
    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCityNotFound(CityNotFoundException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "CITY_NOT_FOUND");
        error.put("message", e.getMessage());
        error.put("city", e.getLocationName());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * 일반 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneral(Exception e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "INTERNAL_SERVER_ERROR");
        error.put("message", "서버 오류가 발생했습니다.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
