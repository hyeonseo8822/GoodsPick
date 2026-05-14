# Goodspick 프로젝트 리드미

## 1. 프로젝트 개요
Goodspick은 중고 물품 거래를 위한 웹 애플리케이션입니다. 사용자는 물품을 등록하고, 필요/제공 게시글을 통해 거래를 진행할 수 있습니다.

## 2. 기획 및 설계
- **주요 기능**: 회원가입/로그인, 물품 등록, 필요/제공 게시판, 채팅, 마이페이지
- **아키텍처**: Spring Boot 기반의 MVC 패턴, JSP 템플릿 엔진 사용
- **DB**: MySQL (JPA/Hibernate)
- **API 명세**: RESTful API 설계

## 3. DB 구조 (ERD)
- **User**: id, username, password, email, created_at
- **Item**: id, title, description, price, status, user_id, created_at
- **Need/ProvidePost**: id, title, content, type(need/provide), user_id, created_at
- **ChatRoom**: id, user1_id, user2_id, created_at
- **ChatMessage**: id, room_id, sender_id, message, sent_at

## 4. API 명세 (예시)
- `POST /api/auth/signup` : 회원가입
- `POST /api/auth/login` : 로그인
- `GET /api/items` : 전체 물품 조회
- `POST /api/items` : 물품 등록
- `GET /api/needs` : 필요 게시글 조회
- `POST /api/needs` : 필요 게시글 등록
- `GET /api/provides` : 제공 게시글 조회
- `POST /api/provides` : 제공 게시글 등록
- `GET /api/chat/rooms` : 채팅방 목록
- `POST /api/chat/rooms` : 채팅방 생성
- `GET /api/chat/messages/{roomId}` : 채팅 메시지 조회
- `POST /api/chat/messages` : 메시지 전송

## 5. 기술 스택
- **Backend**: Java 17, Spring Boot, Spring Data JPA, Spring Security
- **Frontend**: JSP, HTML/CSS, JavaScript
- **DB**: MySQL
- **Build**: Gradle

## 6. 구현의 어려움 및 해결 방법
- **JPA 연관관계 매핑**: 복잡한 엔티티 관계(예: User-Item, User-Post)에서 N+1 문제 발생 → Fetch Join, @EntityGraph, Lazy Loading 최적화 적용
- **파일 업로드**: 이미지/첨부파일 업로드 시 파일명 중복 및 경로 관리 문제 → UUID 파일명, 업로드 경로 분리로 해결
- **인증/인가**: Spring Security 설정 및 JWT 토큰 인증 구현에 어려움 → 공식 문서 및 샘플 코드 참고, 커스텀 필터 적용
- **채팅 기능**: 실시간 채팅 구현 시 WebSocket 세션 관리 문제 → STOMP 프로토콜, 세션 연결/해제 이벤트 핸들링으로 해결

## 7. 프로젝트 구조
```
├── src/main/java/com/example/goodspick
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── repository
│   ├── service
│   └── storage
├── src/main/resources
│   ├── static
│   ├── templates
│   └── application.properties
```

## 8. 실행 방법
1. `application.properties`에 DB 정보 입력
2. `./gradlew build` 후 `./gradlew bootRun` 실행
3. 브라우저에서 `http://localhost:8080` 접속

---
문의: [프로젝트 담당자 이메일]
