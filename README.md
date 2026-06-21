# Balance Fit 🎯

## 프로젝트 소개

스마트폰의 내장 센서를 활용하여 물체의 균형 상태를 정확하게 측정하고 분석하는 안드로이드 애플리케이션입니다.

선반이나 탁자 위에 물건을 놓을 때 균형을 확인하거나, 산업 현장에서 정밀한 균형 측정이 필요한 전문가들을 위한 솔루션을 제공합니다.

---

## 주요 기능

- ✅ **실시간 균형 상태 표시** - 스마트폰 센서로 물체의 현재 균형 상태를 실시간으로 화면에 표시
- ⚙️ **센서 캘리브레이션** - 기기별 센서 정확도 조정으로 정밀한 측정 가능
- 📖 **균형 개선 가이드** - 물체를 어떻게 조정하면 더 균형잡힐 수 있는지 제시

---

## 개발 환경

- **플랫폼**: Android
- **데이터베이스**: Azure Cloud
- **개발 언어**: Kotlin / Java

---

## 빠른 시작

개발을 시작하려면 [시작 가이드](docs/getting-started.md)를 참고하세요.

### Android 스캐폴드 빌드 실행

프로젝트 루트에서 아래 명령어를 실행하여 디버그 APK를 빌드합니다:

```bat
.\gradlew.bat assembleDebug
```

빌드 결과물은 `app/build/outputs/apk/debug/` 경로에 생성됩니다.

---

## 문서

- [시작 가이드](docs/getting-started.md) - 개발 환경 설정
- [앱 기능 설명](docs/app-features.md) - 앱의 전반적인 기능 이해
- [프로젝트 구조](docs/project-structure.md) - 코드 폴더 구성
- [균형 상태 표시 기능](docs/features/balance-display.md) - 실시간 상태 UI 상세
- [캘리브레이션 기능](docs/features/calibration-feature.md) - 캘리브레이션 플로우 상세
- [균형 개선 가이드 기능](docs/features/improvement-guide.md) - 조정 안내 로직 상세
- [테스트 가이드](docs/testing/testing-guide.md) - 검증 시나리오와 기준
- [문제 해결 가이드](docs/troubleshooting.md) - 개발/테스트 이슈 대응

더 자세한 문서는 [docs/](docs/) 폴더에서 확인할 수 있습니다.
