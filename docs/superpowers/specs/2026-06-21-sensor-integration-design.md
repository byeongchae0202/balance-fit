# Balance Fit 센서 연동 설계서

## 1. 목표
- 데모 버튼으로 각도를 변경하던 UI를 실제 휴대전화 센서 입력 기반으로 동작하도록 전환한다.

## 2. 현재 상태
- `SensorDataSource` 인터페이스만 있고 구현체가 없음
- `AppRoot`는 내부 상태(`angle`)를 버튼으로 변경

## 3. 가정(사용자 부재로 기본 채택)
- Android 센서는 런타임 권한 없이 접근 가능한 가속도계/자이로스코프 사용
- 초기 버전은 단일 화면에서 자동 시작/자동 중지(액티비티 생명주기 연동)
- 서버 전송은 이번 범위에서 제외

## 4. 설계
- `AndroidSensorDataSource` 구현 추가:
  - `SensorManager`로 Accelerometer/Gyroscope 구독
  - 두 센서에서 최근 값을 유지하고 합성 각도 계산
  - 센서 미지원 시 `AppError.SensorUnavailable` 전달
- `SensorReading` 모델 추가:
  - `angle`, `x`, `y`, `z`, `timestampMillis`
- UI 상태 연결:
  - `AppRoot`에서 `DisposableEffect`로 스트리밍 시작/중지
  - 수신 값으로 상태 카드/가이드 카드 갱신
  - 데모 기울임 버튼 제거

## 5. 계산 규칙
- 기울기 계산은 문서 규칙과 동일하게 `atan2` 기반:
  - `roll = atan2(ax, az)`
  - `pitch = atan2(ay, az)`
  - `angle = sqrt(roll^2 + pitch^2)` (deg)
- 노이즈 완화를 위해 간단한 EMA 적용(가중치 0.2)

## 6. 오류 처리
- 센서 미지원: UI에 오류 카드 표시 + 상태값 초기화
- 스트리밍 중단: 마지막 정상값 유지, 재시도 버튼 제공(초기 버전은 화면 재진입으로 재시도)

## 7. 테스트
- 계산 함수 단위 테스트(`atan2`/상태 라벨 경계값)
- 센서 구현은 Android 의존성으로 단위 테스트 최소화, 계산 로직은 순수 함수 분리로 검증
- 기존 `testDebugUnitTest` 회귀 통과
