# Android 스캐폴딩 설계서

## 1. 목적
Balance Fit 개발을 시작하기 위해, 로컬에서 즉시 빌드 가능한 Android 기본 프로젝트 골격을 구성한다.  
이번 범위는 **구조 확정과 빌드 가능 상태 확보**이며, 센서/캘리브레이션/가이드의 실제 기능 구현은 포함하지 않는다.

## 2. 범위
### 포함
- Kotlin + Gradle(Kotlin DSL) 기반 Android 프로젝트 생성
- `app` 단일 모듈 구성
- Jetpack Compose 단일 Activity 시작 구조
- 패키지명 `com.balancefit.app` 적용
- 기능 영역별 패키지 골격 생성
  - `ui`
  - `sensor`
  - `calibration`
  - `guidance`
- 기본 테스트 템플릿 유지(단위 테스트/계측 테스트 템플릿)
- `assembleDebug` 성공 확인

### 제외
- CI 워크플로 구성
- 센서 연동 로직 구현
- 캘리브레이션 알고리즘 구현
- 개선 가이드 로직 구현

## 3. 접근 방식 비교
1. 최소형(`gradle init` 중심 수동 구성): 가장 가볍지만 Android 필수 구성을 추가로 많이 보완해야 한다.
2. 균형형(Android 템플릿 구조를 CLI로 재현): 표준 구조와 단순성을 동시에 확보한다. **채택**
3. 확장형(초기부터 멀티모듈): 장기 확장성은 좋지만 시작 비용이 높다.

채택 이유: 현재는 빠른 개발 착수와 빌드 안정성이 우선이며, 모듈 분리는 기능 구현 이후에도 안전하게 진행할 수 있다.

## 4. 아키텍처/구성
### 루트
- Android Gradle 프로젝트
- `settings.gradle.kts`, 루트 `build.gradle.kts`
- `gradle/libs.versions.toml`로 버전 관리

### 앱 모듈(`app`)
- `minSdk`, `targetSdk`, Compose 설정 포함
- 진입점 `MainActivity` + `setContent { ... }`
- 초기 화면은 플레이스홀더 UI

### 패키지 설계
- `ui`: 화면/상태표현
- `sensor`: 센서 데이터 소스 인터페이스
- `calibration`: 캘리브레이션 인터페이스
- `guidance`: 개선 가이드 인터페이스
- 인터페이스 중심으로 경계를 먼저 만들고 구현은 후속 작업에서 채운다.

## 5. 데이터 흐름/오류 처리
- 목표 데이터 흐름: `sensor -> domain -> ui`
- 이번 단계에서는 흐름의 실제 계산 없이 타입/인터페이스 골격만 정의
- 추후 확장을 위해 `AppError`(sealed class) 골격을 정의해 오류 분류 기준만 고정
- 현재 단계의 성공 기준은 오류 처리 완성도가 아니라 실행/빌드 가능성이다.

## 6. 테스트 전략
- 템플릿 단위 테스트 1개, 계측 테스트 1개 유지
- 주요 검증은 `./gradlew assembleDebug` 성공
- 로직 테스트(센서/도메인)는 기능 구현 단계에서 추가

## 7. 완료 기준
아래 조건이 모두 만족되면 스캐폴딩 완료로 본다.
- Android 프로젝트가 생성되어 버전 관리에 반영됨
- `com.balancefit.app` 패키지로 앱이 구성됨
- Compose 기반 앱이 실행 가능한 상태
- `./gradlew assembleDebug` 성공

## 8. 후속 단계
1. 캘리브레이션 도메인 모델 정의
2. 센서 추상화 인터페이스 구현체 연결
3. 상태 표시/가이드 UI를 실제 데이터 흐름에 연결
