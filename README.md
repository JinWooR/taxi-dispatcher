# 택시 배차(Portfolio) — README

## 기술 스택
- Java 17 + Spring Boot 3.5.4
- JPA/Hibernate + Mysql
- Spring Event (향후 kafka 전환 가능)
- JUnit5 + Testcontainers (통합 테스트)

## 무엇을 만드는가?
승객/기사 계정을 생성 관리하고 로그인/연동을 처리를 중심으로, 택시 **배차(Dispatcher)** 로 확장 가능한 모놀리식 프로젝트입니다.<br>

> 기본적으로 모놀리식 기반으로 개발하고 있으며, 추후 MSA로 전환하기 쉬운 구조를 목표로 합니다.

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
    - `DispatchGeo`: (배차 운행 중) 운행 경로
    - `DispatchCandidateDriver`: 배차 후보 기사

## 영속화
- JPA + MySQL(권장), @Version(낙관적 락) 사용