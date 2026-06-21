# Copilot 개발 지침

## 프로젝트 개요
- **프로젝트명**: Balance Fit
- **목적**: [프로젝트 목적을 입력하세요]
- **주요 기술 스택**: [사용 예정인 기술을 입력하세요]

## 개발 환경 설정

### 필수 도구
- Node.js / Python / [개발 환경에 맞는 도구 추가]
- Git
- [필요한 기타 도구들]

### 초기 설정 명령어
```bash
# 프로젝트 초기화
npm install    # 또는 pip install -r requirements.txt

# 개발 서버 실행
npm run dev

# 테스트 실행
npm run test

# 빌드
npm run build
```

## 코드 컨벤션

### 언어 및 스타일
- **코드 작성 언어**: 영어 (변수명, 함수명)
- **주석 및 문서**: 한국어 사용 가능
- **포맷팅**: [Prettier/ESLint 등 사용하는 도구 명시]

### 네이밍 컨벤션
- 변수/함수: camelCase (예: `getUserBalance`)
- 클래스/컴포넌트: PascalCase (예: `UserProfile`)
- 상수: UPPER_SNAKE_CASE (예: `MAX_BALANCE`)
- 파일: kebab-case (예: `user-profile.js`)

### 디렉토리 구조
```
balance-fit/
├── src/
│   ├── components/      # UI 컴포넌트
│   ├── pages/          # 페이지
│   ├── api/            # API 관련
│   ├── utils/          # 유틸리티 함수
│   ├── styles/         # 스타일
│   └── types/          # 타입 정의
├── tests/              # 테스트 파일
├── docs/               # 문서
└── package.json
```

## 버전 관리

### 커밋 메시지 규칙
```
type(scope): description

types: feat, fix, docs, style, refactor, test, chore
scope: 변경 범위 (선택사항)
description: 간단한 설명
```

예시:
```
feat(auth): 사용자 로그인 기능 구현
fix(balance): 잔액 계산 오류 수정
docs: README 업데이트
```

### 브랜치 전략
- `main`: 프로덕션 브랜치 (안정 버전)
- `develop`: 개발 브랜치
- `feature/feature-name`: 기능 개발
- `bugfix/bug-name`: 버그 수정

## 코드 리뷰 기준

- [ ] 코드가 프로젝트 컨벤션을 따르는가?
- [ ] 테스트 코드가 작성되었는가?
- [ ] 문서가 업데이트되었는가?
- [ ] 성능 문제가 없는가?
- [ ] 보안 취약점이 없는가?

## 테스트 가이드

### 테스트 유형
- **단위 테스트**: 개별 함수/모듈 테스트
- **통합 테스트**: 여러 모듈 간 상호작용 테스트
- **E2E 테스트**: 사용자 관점의 전체 흐름 테스트

### 테스트 실행
```bash
npm run test              # 모든 테스트 실행
npm run test:watch      # 감시 모드
npm run test:coverage   # 커버리지 리포트
```

## 배포 프로세스

### 개발 환경 배포
```bash
npm run build:dev
```

### 프로덕션 배포
```bash
npm run build:prod
npm run deploy
```

## 주의사항

- 민감한 정보(API 키, 비밀번호)는 `.env` 파일에서 관리
- Git에 `.env` 파일 커밋 금지
- 큰 파일은 Git LFS 사용
- 정기적으로 의존성 업데이트 (npm audit)

## 문서 및 리소스

- [프로젝트 README](./README.md)
- [API 문서](./docs/api.md)
- [개발 가이드](./docs/development.md)

---

**마지막 수정**: 2026-06-13  
**작성자**: Copilot
