# spring-sunshine-precourse

주어진 도시 이름을 입력받아 외부 REST API를 호출해 해당 도시의 날씨 정보를 조회하고, 이를 정리해 반환하는 간단한 웹 서비스를 스프링 프레임워크로 구현한다.

## 기능 목록

### 1. 날씨 조회 API
- 도시 이름을 입력받아 날씨 정보를 조회하는 REST API
- **Endpoint**: `GET /api/weather?city={locationName}`
- **Response**: 도시명, 좌표, 날씨 정보(현재온도, 체감온도, 하늘상태, 습도), 요약문장

### 2. Geocoding 기능
- Google Geocoding API를 사용하여 도시, 권역 명을 좌표(위도, 경도)로 변환
- 지원 도시: Seoul, Tokyo, NewYork, Paris, London 등
- 서울 전체, 수도권, 특정 구/동 등

### 3. 날씨 정보 조회 기능
- Open-Meteo API를 사용하여 좌표 기반 날씨 정보 조회
- 조회 항목: 현재 온도, 체감 온도, 습도, 하늘 상태

### 4. 날씨 요약 문장 생성
- 조회된 날씨 정보를 기반으로 한 줄 요약 문장 생성
- 예시: "현재 Seoul의 기온은 3.4°C이며, 체감온도는 1.2°C입니다. 날씨는 흐림이고, 습도는 65%입니다."

### 5. LLM 응답 기능
- 날씨 또는 기온을 기준으로 복장을 추천
  - 기온 구간, 강수 여부, 체감온도, 바람 등을 기준으로 추천 규칙을 적용
- 요청에 대해 사용량과 비용 추정치를 로그 기록
  - 입력 토큰, 출력 토큰, 총 토큰, 모델명, 캐시 사용 여부, 추정 비용

### 6. 파라미터에 따라 기존 날씨 요약을 반환할지, LLM의 요약을 반환할지 선택 가능


## 기술 스택
- Spring Boot 3.x
- Java 21
- Gradle
- JUnit 5, AssertJ

## 외부 API
- **Geocoding**: Googleapis (참고:https://developers.google.com/maps/documentation/geocoding/start?hl=ko)
- **Weather**: Open-Meteo
- **LLM**: Gemini 2.5

## 호출 예시
-  curl "http://localhost:8080/api/weather?city=Seoul"
-  curl "http://localhost:8080/api/weather?city=서울"
-  curl "http://localhost:8080/api/weather?city=노원구"
