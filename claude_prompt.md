다음 작업을 위한 최적의 프롬프트를 생성해 줘

## goal
주어진 도시 이름을 입력받아 외부 REST API를 호출해 해당 도시의 날씨 정보를 조회하고 이를 정리해 반환하는 간단한 웹서비스를 스프링 프레임워크로 구현한다

## server spec
- spring boot version 3.x
- java version 21
- build tool: gradle

## instructions
- code package: src/main/java/sunshine
- test code package: src/test/java/sunshine
- mvc structure use
- java code style: https://google.github.io/styleguide/javaguide.html
    - 단, 들여쓰기는 '2 spaces'가 아닌 '4 spaces'로 한다
- test tool: JUnit 5, AssertJ
- 들여쓰기 단계가 3을 넘지 않도록 구현함. 2까지만 허용
    - 예를 들어, while문 안의 if문은 들여쓰기 2이다
    - 들여쓰기가 3을 넘어가면 함수로 분리
- 함수(또는 메서드)의 길이가 15줄을 넘어가지 않도록 구현
    - 함수에는 한가지 기능만 구현
- switch문, 3항 연산자는 사용하지 않음
- if문에서 else 키워드를 쓰지 않음
    - 힌트: if문에서 값을 반환하는 방식으로 구현하여 else를 사용하지 않도록 구현

## context
- 사용자는 도시 이름을 입력하면 해당 도시의 위도와 경도를 기반으로 날씨 정보를 조회할 수 있다
- 최소 다섯 개 이상의 도시는 반드시 지원해야 한다
  - e.g. Seoul, Tokyo, NewYork, Paris, London
- 도시 이름과 좌표를 매핑하는 자료 구조를 설계한다
- 좌표를 조회하는 api는 Geolocation API를 사용한다
    - 참조 url: https://developer.mozilla.org/ko/docs/Web/API/Geolocation/getCurrentPosition
- 날씨 정보를 조회하는 api는 Open-Meteo API를 사용한다
    - 참조 url: https://open-meteo.com/
    - curl 예시: https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&current=temperature_2m,wind_speed_10m&hourly=temperature_2m,relative_humidity_2m,wind_speed_10m
- Open-Meteo API를 호출하여 아래 정보를 조회한다
    - 현재 온도
    - 체감 온도
    - 하늘 상태(맑음, 흐림 등)
    - 습도
- 조회한 데이터를 기반으로 간단한 한 줄 요약 문장을 생성하고, 요청한 도시, 좌표정보, 날씨 정보 4개와 더불어 api 응답으로 반환한다
    - e.g. "현재 서울의 기온은 3.4°C이며, 풍속은 5.7m/s입니다. 날씨는 흐림입니다."

## task step
- step 1
    - 웹 서버 기본 set up: config, mvc 패키지 생성
- step 2
    - 웹 서비스 기능 목록 생성
    - 기능을 구현하기 위한 step을 정리
    - 기능 목록 및 구현을 확인받은 후 다음 task 진행
- step 3
    - 기능 목록 및 구현 step을 README.md 파일에 기록
- step 4
    - 기능 구현 step에 맞춰 코드 생성
    - 코드 생성 step마다 git commit 생성
    - 이 때 commit을 확인받은 후 다음 step 코드 구현 진행 
- step 5
    - 테스트 코드 작성
