# spring-sunshine-precourse

도시 이름을 입력받아 외부 REST API를 호출해 해당 도시의 날씨 정보를 조회하고 반환하는 Spring Boot 웹 서비스

## 기능 목록

### 1. 날씨 조회 API
- 도시 이름을 입력받아 날씨 정보를 조회하는 REST API
- **Endpoint**: `GET /api/weather?city={cityName}`
- **Response**: 도시명, 좌표, 날씨 정보(현재온도, 체감온도, 하늘상태, 습도), 요약문장

### 2. Geocoding 기능
- Nominatim API를 사용하여 도시명을 좌표(위도, 경도)로 변환
- 지원 도시: Seoul, Tokyo, NewYork, Paris, London 등

### 3. 날씨 정보 조회 기능
- Open-Meteo API를 사용하여 좌표 기반 날씨 정보 조회
- 조회 항목: 현재 온도, 체감 온도, 습도, 하늘 상태

### 4. 날씨 요약 문장 생성
- 조회된 날씨 정보를 기반으로 한 줄 요약 문장 생성
- 예시: "현재 Seoul의 기온은 3.4°C이며, 체감온도는 1.2°C입니다. 날씨는 흐림이고, 습도는 65%입니다."

### 5. 예외 처리
- 도시를 찾을 수 없는 경우 적절한 에러 응답 반환

---

## 구현 단계

### Step 1: 기본 구조 셋업
- [x] config 패키지 생성 (RestClientConfig.java)
- [x] controller 패키지 생성
- [x] service 패키지 생성
- [x] domain 패키지 생성
- [x] dto 패키지 생성
- [x] exception 패키지 생성

### Step 2: 도메인 모델 구현
- [x] WeatherCode.java - WMO 날씨 코드 enum

### Step 3: DTO 구현
- [ ] GeocodingApiResponse.java - Nominatim API 응답 매핑
- [ ] WeatherApiResponse.java - Open-Meteo API 응답 매핑
- [ ] WeatherResponse.java - 클라이언트 응답 DTO

### Step 4: Service 계층 구현
- [ ] GeocodingApiClient.java - Nominatim API 호출
- [ ] WeatherApiClient.java - Open-Meteo API 호출
- [ ] WeatherService.java - 비즈니스 로직

### Step 5: Controller 구현
- [ ] WeatherController.java - REST API 엔드포인트

### Step 6: 예외 처리
- [ ] CityNotFoundException.java
- [ ] GlobalExceptionHandler.java

### Step 7: 테스트 코드 작성
- [ ] WeatherServiceTest.java
- [ ] WeatherControllerTest.java

---

## 기술 스택
- Spring Boot 3.x
- Java 21
- Gradle
- JUnit 5, AssertJ

## 외부 API
- **Geocoding**: Nominatim (OpenStreetMap)
- **Weather**: Open-Meteo
