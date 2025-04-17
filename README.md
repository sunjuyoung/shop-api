# 스프링부트 리액트 기반 쇼핑몰 결제 시스템 API

> 스프링부트와 리액트를 활용한 이커머스 백엔드 API 서버

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0+-green.svg)
![JPA](https://img.shields.io/badge/JPA-Hibernate-orange.svg)
![Redis](https://img.shields.io/badge/Cache-Redis-red.svg)

## 📋 프로젝트 개요

 Spring Boot 기반 쇼핑몰 백엔드 API로, JWT 인증, 소셜 로그인, 결제 시스템, 상품 관리, 인기 상품 등  쇼핑몰 웹에서 필요한 핵심 기능들을 구현하였습니다. 

## 🔧 기술 스택

### Back-end
- **Spring Boot** - 애플리케이션 프레임워크
- **JPA/Hibernate** - ORM 및 데이터 액세스
- **Querydsl** - 타입 세이프 쿼리 작성
- **Spring Security** - 보안 및 인증
- **JWT** - 토큰 기반 인증

### Database & Cache
- **MySQL** - 주 데이터베이스
- **Redis** - 캐싱 및 토큰 저장소

### DevOps & Infra
- **Docker** - 컨테이너화
- **Jenkins** - CI/CD

### 외부 연동
- **Toss Payments API** - 결제 시스템
- **Google, Naver OAuth** - 소셜 로그인

## ✨ 주요 기능

### 1. JWT 및 소셜 로그인 인증
- **Spring Security와 JWT 기반 인증 시스템**
    - 안전한 토큰 기반 인증
    - Access Token과 Refresh Token 관리
    - Redis를 활용한 토큰 저장소로 빠른 액세스와 자동 만료 처리

- **소셜 로그인 연동 (Google, Naver)**
    - OAuth 2.0 프로토콜 기반 인증
    - 사용자 프로필 정보 동기화

### 2. Toss Payments 결제 시스템
- **결제 프로세스 구현**
    - 주문 생성 및 결제 정보 저장
    - 결제 승인 요청 및 검증
    - 결제 상태 관리 (성공, 실패, 취소)

- **결제 내역 조회 및 관리**
    - 사용자별 결제 내역
    - 주문 상태 추적

### 3. 이벤트 기반 인기 상품 알고리즘
- **실시간 인기 상품 선정**
    - 댓글, 좋아요, 조회수 기반 인기도 측정
    - Redis를 활용한 카운터 저장 및 집계

### 4. 상품 관리 및 검색 기능
- **다양한 필터 및 정렬 기능**
    - 가격, 카테고리, 인기도 기반 필터링
    - 동적 쿼리 처리 (Querydsl 활용)

- **성능 최적화**
    - Redis 캐싱으로 빠른 상품 목록 조회
    - 페이지네이션 처리

### 5. CI/CD 및 인프라
- **Jenkins Pipeline 자동화**
    - 지속적 통합 및 배포
    - 자동 테스트 및 빌드

- **Docker 컨테이너화**
    - 일관된 개발 및 배포 환경
    - 마이크로서비스 아키텍처 지원


## 🔜 향후 계획

1. **결제 기능 확장**
    - 결제 취소 및 환불 프로세스 구현
    - 다양한 결제 수단 지원

2. **AWS 클라우드 인프라 구축**
    - EC2/EKS를 활용한 확장 가능한 배포
    - RDS 및 ElastiCache 활용

3. **성능 최적화**
    - 부하 테스트 및 병목 현상 개선
    - 인덱싱 및 쿼리 최적화

