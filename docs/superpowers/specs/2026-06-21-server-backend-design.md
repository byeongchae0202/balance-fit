# Balance Fit 서버 백엔드 설계서

## 1. 배경 및 현재 상태 확인
- 현재 저장소에는 Android 앱 스캐폴딩 코드만 존재하며, 독립 실행 가능한 서버(API) 코드는 없음
- 따라서 앱 개발/연동을 위한 최소 백엔드 서버를 신규 추가해야 함

## 2. 목표
- 로컬에서 즉시 실행 가능한 REST API 서버를 추가한다.
- Android 앱이 향후 연동할 수 있도록 헬스체크/캘리브레이션/가이드 API 골격을 제공한다.
- 배포 가능한 구조(`PORT` 환경변수, start 스크립트)를 갖춘다.

## 3. 접근 방식 비교
1. Node.js + Express (채택): 빠른 초기 구축, 낮은 진입 비용, 추후 Azure App Service/Container Apps 배포 용이
2. Python + FastAPI: 문서화 장점(OpenAPI) 있으나 현재 저장소 표준 런타임 추가 부담 발생
3. Kotlin + Ktor: 앱 언어와 통일성은 좋지만 초기 백엔드 구성 복잡도 상승

채택 사유: 현 단계에서는 기능 구현 속도와 단순성이 우선이며, Node.js + Express가 가장 빠르게 목표 달성 가능.

## 4. 설계 범위
### 포함
- `server/` 디렉터리 신규 추가
- Express 기반 API 서버
- 엔드포인트
  - `GET /health` : 서버 상태 확인
  - `POST /api/v1/calibration` : 캘리브레이션 입력 저장
  - `GET /api/v1/calibration/latest` : 최근 캘리브레이션 조회
  - `POST /api/v1/guidance` : 각도 기반 안내 메시지 반환
- JSON 요청 검증(필수 필드/타입) 및 명시적 에러 응답
- `README.md`, `docs/getting-started.md`에 서버 실행 방법 반영

### 제외
- DB 영속 저장(Cosmos DB/SQL 등)
- 인증/인가
- Azure 배포 자동화 파이프라인

## 5. 아키텍처
- `server/src/app.js`: Express 앱 생성, 공통 미들웨어(JSON 파싱), 라우터 연결, 404/에러 핸들러
- `server/src/index.js`: HTTP 서버 부트스트랩 (`PORT` 기반 listen)
- `server/src/routes/health.js`: 헬스체크 라우트
- `server/src/routes/calibration.js`: 캘리브레이션 입력/조회 라우트
- `server/src/routes/guidance.js`: 가이드 생성 라우트
- `server/src/domain/guidance.js`: 각도 → 메시지 규칙 함수
- `server/src/store/calibrationStore.js`: 메모리 저장소(최근 1건)

## 6. 데이터 흐름
1. 클라이언트가 JSON 요청 전송
2. 라우터에서 입력 검증 수행
3. 도메인 규칙/저장소 호출
4. 성공 시 `200/201` + JSON 응답, 실패 시 `400/422/500` + JSON 에러

## 7. 오류 처리 원칙
- 잘못된 입력: `400 Bad Request` + `code`, `message`, `details`
- 도메인 검증 실패: `422 Unprocessable Entity`
- 예기치 않은 서버 오류: `500 Internal Server Error`
- 모든 에러는 성공 형태로 숨기지 않고 명시적으로 응답

## 8. 테스트 전략
- 최소 단위:
  - `guidance` 규칙 함수 단위 테스트
  - 캘리브레이션 요청 검증 테스트
- 최소 통합:
  - `/health` 응답 테스트
  - `/api/v1/calibration` POST/GET 흐름 테스트
- 기존 Android 테스트(`testDebugUnitTest`)는 회귀 확인 유지

## 9. 완료 기준
- `node server/src/index.js`로 서버 기동 가능
- `/health`가 `{"status":"ok"}` 반환
- 캘리브레이션/가이드 API 정상 동작
- 문서에 실행 방법 반영
