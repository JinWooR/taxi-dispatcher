# 택시 배차(Portfolio) — README

## 무엇을 만드는가?
승객/기사 계정을 생성 관리하고 로그인/연동을 처리를 중심으로, 택시 **배차(Dispatcher)** 로 확장 가능한 모놀리식 프로젝트입니다.<br>


## 핵심 기능


## 아키텍쳐 개요
- 모노리스 + 헥사고날
- DDD 레이어링
  - domain: Aggregate/Entity/VO/도메인 이벤트 (프레임워크 무의존)
  - application: 유스케이스(포트 in) + 오케스트레이션(트랜잭션, 이벤트 퍼블리시)
  - adapter: web(REST), persistence(JPA), messaging(Outbox) 등 
  - config: DI/설정

## 바운디드 컨텍스트 (Bounded Context)
- Account (어카운트 및 인증 인가)
  - Aggregate Root: `Acount`
- User (사용자)
  - Aggregate Root: ` `
- Driver (택시 기사)
  - Aggregate Root: ` `
- Dispatcher (배차)
  - Aggregate Root: ` `

## 영속화
- JPA + MySQL(권장), @Version(낙관적 락) 사용