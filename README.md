<div align="center">

# 🛣️ 경로 기반 돌발상황 알림 API (Route Alarm API)

**사용자가 등록한 경로 주변에 실시간 돌발 상황(사고, 공사 등)이 발생하면 알림을 생성하고 조회하는 백엔드 API 서버**

</div>

<br>

<div align="center">

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgresql-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![PostGIS](https://img.shields.io/badge/PostGIS-CC0000?style=for-the-badge&logo=postgresql&logoColor=white)

</div>

---

## 📖 프로젝트 소개

사용자가 자주 이용하는 경로(출발지, 목적지, 경유지)를 등록하면, 해당 경로 주변의 실시간 교통 돌발 정보를 알려주는 알림 서비스를 제공합니다. 서울 열린데이터 광장의 '실시간 돌발 도로 정보' API를 주기적으로 동기화하고, PostGIS의 강력한 공간 쿼리 기능을 활용하여 정확한 위치 기반 서비스를 구현했습니다.

---

## 🚀 주요 기능

- ✅ **사용자 인증**: JWT 토큰 기반의 회원가입 및 로그인 기능
- ✅ **경로 관리**: 사용자의 경로(출발지, 목적지, 경유지) CRUD 기능
- ✅ **실시간 데이터 동기화**: `@Scheduled`를 이용해 1시간마다 공공 API의 돌발 정보를 DB에 자동 업데이트 (Upsert)
- ✅ **공간 쿼리 기반 근접 검색**: PostGIS의 `ST_DWithin` 함수를 활용하여, 특정 경로 주변의 돌발 상황을 효율적으로 검색
- ✅ **알림 시스템**: 경로 주변에 돌발 상황 발생 시 알림을 자동 생성하고, 사용자별 알림 목록 조회 기능 제공
- ✅ **알림 설정**: 경로별로 받을 알림(사고, 공사 등)을 On/Off 하는 기능

---

## 🏛️ 아키텍처 및 ERD

- **Layered Architecture**를 기반으로 Controller, Service, Repository 계층을 명확히 분리하여 역할과 책임을 관리합니다.
- **데이터베이스 설계 (ERD)**
- 
  > <img width="700" height="500" alt="image" src="https://github.com/user-attachments/assets/820f2736-7404-443e-88f5-223c4400e60a" />

---

## 🛠️ 기술 스택

| 구분 | 내용 |
| :--- | :--- |
| **Backend** | `Java 21`, `Spring Boot 3.3.0`, `Spring Data JPA`, `Spring Security` |
| **Database** | `PostgreSQL`, `PostGIS` |
| **Libraries**| `JJWT`, `Hibernate Spatial`, `JTS`, `Proj4j`, `Jackson (JSON/XML)`, `Springdoc OpenAPI (Swagger)` |
| **Infra**| `AWS EC2`, `Homebrew` |

---

## 📝 API 명세

전체 API 명세는 서버 실행 후 아래 Swagger UI 주소에서 확인하실 수 있습니다.

- ~~**Server**: `http://54.180.87.165:8080/swagger-ui.html`~~ // ec2 사용량 문제로 서버 꺼둡니다.

---

## ⚙️ 시작하기

1.  **프로젝트 클론**
    ```bash
    git clone [https://github.com/hoodyboi/route-alarm-app.git](https://github.com/hoodyboi/route-alarm-app.git)
    ```
2.  **데이터베이스 설정**
    - `psql`에 접속하여 `route_alarm_db` 데이터베이스를 생성합니다.
    - 생성한 DB에 접속하여 PostGIS 확장 기능을 활성화합니다.
      ```sql
      CREATE EXTENSION postgis;
      ```
3.  **설정 파일 수정**
    - `src/main/resources/application.properties` 파일에 자신의 환경에 맞는 DB 정보와 API 키를 입력합니다.
4.  **애플리케이션 실행**

---

## 💡 주요 개발 내용 및 학습

- **공공 API 연동 및 XML 파싱**: `WebClient`를 사용하여 외부 API를 호출하고, `UnsupportedMediaTypeException` 등의 오류를 해결하며 `Jackson`을 이용해 XML 데이터를 DTO로 파싱하는 과정을 구현했습니다.
- **PostGIS 공간 데이터 처리**: `Point`, `LineString` 같은 공간 데이터 타입을 다루기 위해 `hibernate-spatial` 라이브러리를 적용하고, `ST_DWithin`을 이용한 효율적인 공간 쿼리를 작성하여 '경로 주변'이라는 복잡한 비즈니스 로직을 구현했습니다.
- **개발 환경 구축 및 디버깅**: 로컬(macOS) 및 서버(Ubuntu) 환경에서 PostgreSQL, PostGIS 설치, `PATH` 환경 변수 설정, 의존성 충돌, Gradle 캐시 문제 등 실제 개발 과정에서 마주치는 다양한 환경 문제를 해결했습니다.
