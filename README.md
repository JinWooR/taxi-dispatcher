# 🚕 택시 배차(Portfolio) — README

> **DDD + 이벤트 기반 비동기 설계** 를 중심으로 단일 모놀리식 백엔드 시스템입니다.<br>
>  **MSA 전환이 용이한 구조를 목표**로 하며, 별도의 인프라(DB, 메시지 브로커 등) 구축 없이 실행 및 테스트가 가능합니다.


## 프로젝트 개요
승객과 택시 기사 간 **배차 요청 자동화 프로세스**를 구현한 백엔드 시스템입니다.<br>
도메인 이벤트 기반 비동기 흐름을 통해 빠른 처리가 가능하며, 필요에 따라 동기 API를 호출하여 처리 작업의 안정성을 고려하여 개발하였습니다.  

## 사용 기술 스택
- Java 17 + Spring Boot 3.5.4
- JPA/Hibernate + Mysql
- Spring Event (향후 kafka 전환 가능 구조 수립)
- JUnit5 + Testcontainers (통합 테스트)

## 주요 기능 시나리오
> ### 사용자 배차 요청 시나리오
> 1. [WriteDispatchService](./src/main/java/com/taxidispatcher/modules/dispatcher/application/service/WriteDispatchService.java)
>    - 사용자 배차 요청 생성
>    - 이벤트 발행 요청 ```eventPublisher.publish(new FindDispatchCandidateDriverEvent(dispatchId));```
> 2. [FindDispatchCandidateDriverEvent](./src/main/java/com/taxidispatcher/modules/dispatcher/adapter/event/publisher/FindDispatchCandidateDriverEventPublisherImpl.java)
>    - 이벤트 발행 ```eventPublisher.publishEvent(event);```
>    - 인메모리 딜레이 큐 방식 구현 ```private final InMemoryDelayQueue inMemoryDelayQueue;```
>      - [InMemoryDelayQueue](./src/main/java/com/taxidispatcher/shared/core/InMemoryDelayQueue.java)
> 3. [FindDispatchCandidateDriverAdapter](./src/main/java/com/taxidispatcher/modules/dispatcher/application/service/FindDispatchCandidateDriverService.java)
>    - 배차 요청서에 맞는 후보 기사 탐색 ```driverClient.callDrivers(candidateRequest)```
>      - 후보 기사 탐색시 [FindDispatchCandidateDriverClientImpl](./src/main/java/com/taxidispatcher/modules/dispatcher/adapter/client/FindDispatchCandidateDriverClientImpl.java) 외부 API 호출
>    - 후보 기사가 없거나 제한 시간내에 승인 또는 거절을 진행하지 않은 경우 딜레이 큐 방식을 통한 후보 기사 탐색 이벤트 재발행 ```eventPublisher.publish(new FindDispatchCandidateDriverEvent(dispatch.getId(), nextSeconds));```

> -> 이를 통해 외부 메시지 큐 없이 이벤트 기반 아키텍처 수립 <br>
>  추후 Kafka 또는 Rabbit MQ 마이그레이션 가능

## 아키텍쳐 개요
- 모노리스 + 헥사고날
- DDD 레이어링
  - `domain`: Aggregate/Entity/VO/도메인 이벤트 (프레임워크 무의존)
  - `application`: 유스케이스(포트 in/out) + 오케스트레이션(트랜잭션)
  - `adapter` 
    - `web` (REST API)
    - `persistence` (JPA, Repository)
    - `event` (DomainEvent Pub/Sub)
    - `client` (External API Request)
  - config: DI/설정

## 핵심 기능
- 계정 (Account)
  - 회원 가입 / 로그인 / 권한 관리
  - 권한: `USER` / `DRIVER` 구분
- 사용자 (User)
  - 배차 요청 승객 프로필 관리
- 택시 기사 (Driver)
  - 근무 상태 관리
  - 운행 이력 및 경로 관리
- 배차 (Dispatcher)
  - 배차 요청 생성 및 후보 기사 탐색
  - 이벤트 기반 기사 후보 수집 및 배차 확정

## 바운디드 컨텍스트 (Bounded Context)
- Account (인증/인가)
  - Aggregate:
    - `Acount`: 어카운트
    - `Credential`: 로그인 수단
- User (사용자)
  - Aggregate
    - `User`: 사용자 정보
- Driver (택시 기사)
  - Aggregate
    - `Driver`: 기사 정보
    - `DrvierWorkHistory`: 기사 근무 이력
    - `DriverWorkGeo`: (근무 중) 운행 경로
- Dispatcher (배차)
  - Aggregate
    - `Dispatch`: 배차
    - `DispatchCandidateDriver`: 배차 후보 기사
    - `DispatchDriverGeoHistory`: (배차 운행 중) 운행 경로

## 실행 및 테스트
- 빌드: `./gradlew clean build`
- 실행: `./gradlew bootRun`
- 테스트: `./gradlew test`
