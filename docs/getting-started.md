# 시작 가이드 (Getting Started)

이 문서는 Balance Fit 앱 개발을 시작하기 위해 필요한 환경을 설정하는 방법을 설명합니다.

---

## 필수 설치 프로그램

### 1. Android Studio 설치
**Android Studio**는 안드로이드 앱을 만들기 위한 공식 개발 도구입니다.

- 📥 다운로드: [Android Studio 공식 사이트](https://developer.android.com/studio)
- 💻 설치 후: 처음 실행할 때 자동으로 필요한 추가 도구들이 설치됩니다.
- ⏱️ 첫 설치는 시간이 걸릴 수 있습니다 (10-30분).

### 2. Git 설치
**Git**은 코드의 변경 사항을 관리하고 GitHub에 업로드하는 데 사용합니다.

- 📥 다운로드: [Git 공식 사이트](https://git-scm.com/)
- Windows 사용자: 기본 설정 그대로 "Next" 버튼을 눌러서 설치하면 됩니다.

### 3. Azure SDK 설정
**Azure SDK**는 앱에서 클라우드 데이터베이스에 접근할 때 필요합니다.

- ℹ️ Android Studio에 플러그인으로 설치됩니다.
- 추후 자세한 가이드는 별도 문서 참고

---

## 로컬 설정

로컬 환경에서 빌드하려면 아래 단계를 순서대로 완료하세요.

### 1) JDK 17 설치

- 📥 다운로드: [Eclipse Temurin JDK 17](https://adoptium.net/) 또는 원하는 JDK 17 배포판 설치
- 설치 후 환경 변수 `JAVA_HOME`이 JDK 17 경로를 가리키는지 확인하세요.

```bat
java -version
```

출력 예시: `openjdk version "17.x.x"`

### 2) Android SDK Platform 35 설치

Android Studio의 **SDK Manager** (`Settings → Languages & Frameworks → Android SDK`) 에서:

- **SDK Platforms** 탭 → **Android 15 (API 35)** 체크 후 Apply

또는 커맨드라인 도구를 사용하는 경우:

```bat
sdkmanager "platforms;android-35"
```

### 3) 프로젝트 루트에서 빌드 실행

프로젝트 루트 디렉터리에서 다음 명령어를 실행합니다:

```bat
.\gradlew.bat assembleDebug
```

빌드 성공 시 `app/build/outputs/apk/debug/` 경로에 APK 파일이 생성됩니다.

---

## 프로젝트 시작하기

### 단계 1: 코드 받아오기
터미널(Command Prompt)에서 다음 명령어를 실행합니다:

```bash
git clone https://github.com/byeongchae0202/balance-fit.git
cd balance-fit
```

### 단계 2: Android Studio에서 프로젝트 열기
1. Android Studio 실행
2. "Open an existing project" 선택
3. 받아온 `balance-fit` 폴더 선택

### 단계 3: 앱 실행하기
1. Android Studio 상단의 "Run" 버튼 클릭
2. 에뮬레이터 또는 연결된 안드로이드 폰에서 앱 실행

---

## 문제 해결

### Android Studio 설치 후 느린 경우
- 첫 실행 시 추가 도구 다운로드 중입니다.
- 인터넷 연결이 안정적인지 확인하세요.

### Git clone 실패
- Git이 제대로 설치되었는지 확인하세요.
- 터미널을 다시 시작해보세요.

더 자세한 문제 해결은 [troubleshooting 문서](../troubleshooting.md) 참고

---

## 다음 단계

환경 설정이 완료되면:
1. [앱 기능 설명](app-features.md)을 읽어 앱의 전체 구조 이해
2. [프로젝트 구조](project-structure.md)를 읽어 코드 폴더 구성 파악
