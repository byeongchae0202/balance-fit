# Balance Fit 프로젝트 브레인스토밍 기록

**작성일**: 2026-06-13  
**세션 ID**: 9c7e7f31-ec70-431b-bcc2-b4be71fa6183  
**상태**: 문서 구조 설계 완료 - 구현 대기 중

---

## 프로젝트 개요

### 앱 이름
**Balance Fit**

### 핵심 기능
스마트폰의 내장 센서를 활용하여 물체의 균형 상태를 정확하게 측정하고 분석하는 안드로이드 애플리케이션.

### 주요 목적
- 선반이나 탁자 위에 물건을 놓을 때 균형을 확인
- 산업 현장에서 정밀한 균형 측정이 필요한 전문가들을 위한 솔루션

---

## 사용 시나리오

### 대상 사용자
1. **일반 사용자**: 일상생활에서 물체의 균형 확인
2. **산업용 전문가**: 건설, 물류 등에서 정밀한 균형 측정

### 핵심 사용 사례
물건을 선반이나 탁자 위에 놓을 때 균형 상태 체크

---

## 핵심 기능

### 1. 실시간 균형 상태 표시
- 화면 중앙에 원으로 표시
- 원의 기울기 = 물체의 기울기 (각도 표시)
- 색상 피드백:
  - 🟢 초록색: 완벽한 균형 (±1° 이내)
  - 🟡 노란색: 약간 기울어짐 (±2~5°)
  - 🔴 빨간색: 심하게 기울어짐 (5° 이상)

### 2. 센서 캘리브레이션
- 기기마다 센서 특성이 다르므로 정확도 조정 필요
- 기준점 설정 및 검증
- 프로필 관리 (실내/실외)
- 추가 기능:
  - 센서 상태 체크
  - 캘리브레이션 검증
  - 온도 보정
  - 테스트 모드
  - 정확도 신뢰도 평가 (%)
  - 자동 재캘리브레이션 제안
  - 빠른 재캘리브레이션

### 3. 균형 개선 가이드
- 물체가 기울어져 있을 때 조정 방법 제시
- 텍스트로 구체적 거리 제시
  - 예: "물체를 왼쪽으로 약 2cm 옮기세요"
- 실시간 업데이트

---

## 기록 기능 (Recording)

### 개념
사용자가 **선택적으로 데이터 저장 여부 결정**

### 동작 방식

**기록 OFF (기본값)**
- 센서 데이터 읽음 → 실시간 표시 → 저장 안 함

**기록 ON (사용자 선택)**
- 센서 데이터 읽음 → 실시간 표시 → 데이터베이스 저장

### 데이터 저장 정책
- 센서에서 매우 빠르게 데이터 들어옴 (초당 100회 이상)
- **스마트폰 자원 효율화**: 모든 데이터가 아닌 **필터링된 데이터만 저장**
  - 1초마다 1개씩 저장, 또는
  - 균형 상태 변화 시 저장
- **저장된 데이터는 절대 자동 삭제 안 함** (사용자 수동 삭제만 가능)
- 과거 기록은 언제든 조회 가능

---

## 기술 스택

### 플랫폼
- **개발**: Android
- **언어**: Kotlin / Java
- **클라우드**: Azure

---

## 문서 구조

### 생성된 문서 목록

```
balance-fit/
├── README.md                          ✅ 생성됨
├── docs/
│   ├── getting-started.md             ✅ 생성됨
│   ├── app-features.md                ✅ 생성됨
│   ├── project-structure.md           ✅ 생성됨
│   ├── architecture/
│   │   ├── sensor-data-processing.md  ✅ 생성됨
│   │   ├── balance-calculation.md     ✅ 생성됨
│   │   ├── calibration.md             ✅ 생성됨
│   │   └── azure-integration.md       ✅ 생성됨
│   ├── features/
│   │   ├── balance-display.md         ⏳ 미생성
│   │   ├── calibration-feature.md     ⏳ 미생성
│   │   └── improvement-guide.md       ⏳ 미생성
│   └── testing/
│       └── testing-guide.md           ⏳ 미생성
├── copilot-instructions.md            ✅ 생성됨
├── .gitignore                         ⏳ 미생성
└── (추가 필요시)
```

### 문서 구성 논리

1. **README.md**: 프로젝트 전체 소개
2. **docs/getting-started.md**: 개발 환경 설정 (초보자용)
3. **docs/app-features.md**: 사용자 관점의 3가지 핵심 기능 설명
4. **docs/project-structure.md**: 코드 폴더 구조 및 각 폴더의 역할
5. **docs/architecture/**: 기술적 상세 설명
   - sensor-data-processing.md: 센서 데이터 수집 및 처리 전략
   - balance-calculation.md: 물리학적 계산 방식 (arctan2 함수 사용)
   - calibration.md: 캘리브레이션 시스템 전체
   - azure-integration.md: 클라우드 데이터 관리
6. **docs/features/**: 각 기능별 구현 가이드 (미생성)
7. **docs/testing/**: 테스트 방법 (미생성)

---

## 주요 기술 결정사항

### 1. 센서 데이터 처리
- **선택 기준**: 모든 센서 데이터를 저장하지 않음 (배터리/저장소 효율)
- **필터링**: 1초마다 1개 데이터 저장 또는 변화 감지 시 저장
- **균형 표시**: 센서 데이터는 계속 읽음 (실시간 표시 필요)

### 2. 균형 각도 계산
- **물리학 원리**: 중력을 이용한 2축 기울기 각도 계산
- **공식**:
  - angle_pitch = arctan2(Y값, Z값)
  - angle_roll = arctan2(X값, Z값)
  - 최종 각도 = √(pitch² + roll²)
- **정확도 개선**: 이동 평균 필터로 노이즈 제거

### 3. 캘리브레이션 프로필
- **프로필 종류**: 실내, 실외 (추후 확장 가능)
- **검증 과정**: 센서 상태 체크 → 온도 보정 → 테스트 모드 → 신뢰도 평가
- **신뢰도 기준**:
  - 95% 이상: 매우 우수
  - 90~94%: 우수
  - 85~89%: 양호
  - 80% 미만: 재캘리브레이션 필요
- **자동 제안**: 7일마다 또는 신뢰도 80% 이하 시 알림

### 4. Azure 통합
- **저장되는 데이터**:
  - 측정 기록 (각도, 시간, 프로필, 온도, 신뢰도)
  - 사용자 프로필 (일반 vs 전문가)
  - 팀 정보 (전문가용 협력 기능)
- **기능**:
  - 개인: 개인 데이터만 접근
  - 팀: 공유 프로필, 팀 리포트 생성, 데이터 분석
- **오프라인 모드**: 인터넷 없을 때 로컬 저장 후 자동 동기화

---

## 개발 구조 (Project Structure)

### 폴더별 역할

| 폴더 | 역할 |
|------|------|
| **MainActivity.kt** | 앱 시작점 |
| **sensors/** | 센서 데이터 수집 |
| **ui/** | 화면 UI 구성 |
| **models/** | 데이터 구조 정의 |
| **calculations/** | 균형 각도 계산 |
| **calibration/** | 캘리브레이션 관리 |
| **database/** | 로컬 데이터 저장 |
| **network/** | Azure 클라우드 연동 |
| **constants/** | 고정값 관리 |
| **config/** | Azure 설정 정보 |
| **utils/** | 재사용 함수 |

### 데이터 흐름

```
센서 → 필터링 → 계산 → UI 표시 → 저장 → Azure
```

---

## 다음 작업 (To-Do)

### ✅ 완료된 작업
1. GitHub 저장소 연결
2. copilot-instructions.md 생성
3. README.md 작성
4. 기본 가이드 문서 작성 (getting-started, app-features, project-structure)
5. 아키텍처 문서 작성 (sensor-data-processing, balance-calculation, calibration, azure-integration)

### ⏳ 미완료 작업
1. **features/ 폴더 문서**
   - balance-display.md: UI 구현 가이드
   - calibration-feature.md: 캘리브레이션 UI 구현
   - improvement-guide.md: 개선 가이드 UI 구현

2. **testing/ 폴더 문서**
   - testing-guide.md: 앱 테스트 방법

3. **기타**
   - .gitignore 파일 생성
   - 필요시 troubleshooting.md 추가

---

## 중요 결정사항 정리

### 사용자 선택사항
- ✅ **앱 대상**: 일반 사용자 + 산업용 전문가
- ✅ **기록 기능**: ON/OFF 선택식
- ✅ **캘리브레이션**: 실내/실외 프로필
- ✅ **검증 기능**: 포함 (센서 체크, 테스트 모드, 신뢰도 평가)
- ✅ **자동 재캘리브레이션**: 포함 (7일 또는 신뢰도 기반)
- ✅ **빠른 재캘리브레이션**: 포함 (산업용)
- ✅ **Azure 통합**: 팀 협력 기능 포함
- ❌ **클라우드 백업** (캘리브레이션): 제외

### Copilot의 권장사항 채택
- ✅ 하이브리드 문서 구조 (Android 표준 + 도메인별 문서)
- ✅ 물리학적으로 정확한 균형 계산 (arctan2 사용)
- ✅ 센서 노이즈 제거 (이동 평균 필터)
- ✅ 온도 보정 기능
- ✅ 센서 상태 체크
- ✅ 테스트 모드
- ✅ 신뢰도 평가
- ✅ 팀 협력 기능 (Azure)

---

## 기술 사양 요약

### 센서
- 가속도계 (Accelerometer)
- 자이로스코프 (Gyroscope)

### 계산
- 각도: arctan2(수평축, Z축) 사용
- 필터링: 1초마다 또는 변화 감지 시
- 노이즈 제거: 이동 평균 필터

### 저장소
- 로컬: SQLite (앱 기본)
- 클라우드: Azure

### 보안
- 암호화: HTTPS(전송), AES-256(저장)
- 인증: 이메일+비밀번호, 소셜 로그인, 생체인증

---

## 재개 가이드

이 문서는 프로젝트를 **나중에 재개할 때** 참고하기 위해 작성되었습니다.

### 재개 시 체크리스트

1. **현재 진행도 확인**
   - ✅ 문서 구조 설계 완료
   - ⏳ features/ 문서 미완성
   - ⏳ 구현 시작 전

2. **다음 작업**
   - [ ] features/ 폴더 3개 문서 작성
   - [ ] testing-guide.md 작성
   - [ ] .gitignore 생성
   - [ ] 모든 문서 검토 및 업데이트
   - [ ] writing-plans 스킬 호출 → 구현 계획 수립

3. **참고 문서**
   - copilot-instructions.md: 개발 가이드라인
   - docs/project-structure.md: 코드 구조
   - docs/architecture/: 기술 상세 정보

4. **연락처 및 환경**
   - GitHub: https://github.com/byeongchae0202/balance-fit.git
   - 개발자: byeongchae0202 (비개발자, 첫 프로젝트)
   - 지역: 한국 (한국어 응답)

---

## 파일 관리

### 생성된 파일 현황 (2026-06-13)

```
C:\dev\balance fit\
├── .git/
├── README.md
├── docs/
│   ├── getting-started.md
│   ├── app-features.md
│   ├── project-structure.md
│   └── architecture/
│       ├── sensor-data-processing.md
│       ├── balance-calculation.md
│       ├── calibration.md
│       └── azure-integration.md
├── copilot-instructions.md
└── (features/, testing/ 폴더는 아직 파일 없음)
```

### Git 커밋 현황

```
1. "초기 설정: copilot-instructions.md 추가"
   - Commit ID: 0bf5ecc

(다음 커밋 예정)
```

---

**문서 작성일**: 2026-06-13 10:53 UTC+9  
**마지막 수정**: 진행중  
**상태**: 대기 중 (구현 단계 시작 대기)
