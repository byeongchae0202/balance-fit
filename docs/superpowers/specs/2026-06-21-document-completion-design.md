# 문서 완료 설계서 (2026-06-21)

## 1. 배경과 목표
현재 문서 상태에서 `docs/features`와 `docs/testing` 핵심 문서가 부재하고, `README.md` 및 `docs/project-session-record.md`에 미완료/추후 작성 표기가 남아 있어 문서 완료 상태로 보기 어렵다.

이번 작업 목표는 다음 4가지를 한 번에 완료하는 것이다.
1. `docs/features` 문서 3종 작성
2. `docs/testing/testing-guide.md` 작성
3. `README.md`의 라이선스/문의 섹션 정리(사용자 결정 반영)
4. `docs/project-session-record.md`의 미완료 항목을 최신 상태로 갱신

## 2. 문서 작성 원칙
- 기존 저장소 문서 톤(초급 친화적 설명, 단계형 구성, 이모지/구분선 사용)을 유지한다.
- 각 기능 문서는 동일한 뼈대를 사용해 탐색성을 높인다.
  - 목적
  - 화면/동작 구성
  - 상태/데이터 흐름
  - 예외/오류 처리
  - 구현 체크리스트
- 테스트 문서는 실행 가능한 수준으로 작성하되, 현재 코드베이스가 초기 단계임을 고려해 "테스트 기준/시나리오" 중심으로 정리한다.

## 3. 산출물 설계
### 3.1 신규 문서
- `docs/features/balance-display.md`
  - 실시간 균형 시각화의 UI 구성, 상태 표시 규칙, 업데이트 주기, 경계값 반응을 문서화
- `docs/features/calibration-feature.md`
  - 캘리브레이션 UI 플로우, 프로필(실내/실외), 검증/재시도/재캘리브레이션 시나리오 문서화
- `docs/features/improvement-guide.md`
  - 개선 안내 문구 생성 규칙, 단계별 피드백, 사용자 행동 유도 UX 문서화
- `docs/testing/testing-guide.md`
  - 환경 구성, 기능별 시나리오, 정확도/회귀/장치 테스트 기준 문서화

### 3.2 기존 문서 수정
- `README.md`
  - 사용자 결정에 따라 라이선스 섹션 생략
  - 사용자 결정에 따라 문의 섹션 생략
- `docs/project-session-record.md`
  - 미완료 항목 및 재개 체크리스트를 실제 완료 상태와 일치하도록 갱신

## 4. 데이터 흐름/연계 관점
- 신규 `features` 문서는 기존 `architecture` 문서와 링크 관계를 형성한다.
- `testing-guide`는 `features` 문서 기준으로 테스트 범위를 참조하도록 구성한다.
- `README`는 진입점 역할에 집중하고, 상세 내용은 `docs/` 하위 문서로 위임한다.

## 5. 오류/누락 방지 설계
- 문서 간 링크 깨짐 방지: 상대 경로를 점검해 연결한다.
- 상태 불일치 방지: "미완료" 표기 문서를 우선 정리해 프로젝트 상태를 일관되게 만든다.
- 모호성 방지: TODO/TBD/추후 작성 문구를 가능한 한 제거하고, 미확정 사항은 사용자 결정값으로 명시한다.

## 6. 완료 기준
- 위 4개 범위(신규 4문서 + 기존 2문서 수정)가 저장소에 반영됨
- `docs/features/**/*.md`, `docs/testing/testing-guide.md` 파일이 실제로 존재함
- `README.md`에서 라이선스/문의 섹션이 제거됨
- `docs/project-session-record.md`의 미완료 표기가 최신 상태로 업데이트됨
