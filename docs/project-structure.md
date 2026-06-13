# 프로젝트 구조 (Project Structure)

Balance Fit 앱의 코드 폴더 구조를 설명합니다. 각 폴더에는 특정 역할을 하는 코드 파일들이 들어갑니다.

---

## 전체 폴더 구조

```
balance-fit/
├── app/
│   └── src/
│       └── main/
│           ├── java/com/balancefit/
│           │   ├── MainActivity.kt              # 앱 시작 화면
│           │   ├── sensors/                    # 센서 관련 코드
│           │   ├── ui/                         # 화면 UI 코드
│           │   ├── models/                     # 데이터 구조 정의
│           │   ├── calculations/               # 균형 계산 로직
│           │   ├── calibration/                # 센서 캘리브레이션
│           │   ├── database/                   # 로컬 데이터 저장
│           │   ├── network/                    # Azure 클라우드 연동
│           │   ├── constants/                  # 상수값들
│           │   ├── config/                     # Azure 설정
│           │   └── utils/                      # 도움말 함수들
│           └── res/                            # 이미지, 색상 등 자원
├── docs/                                       # 문서
├── build.gradle                                # 의존성 설정
├── README.md
├── .gitignore
└── copilot-instructions.md
```

---

## 각 폴더별 역할

### 📱 **MainActivity.kt**
- **역할**: 앱이 시작될 때 가장 먼저 실행되는 파일
- **하는 일**: 
  - 앱의 메인 화면 표시
  - 다른 파일들을 조합해서 앱 구성
  - 사용자 상호작용 관리 (터치, 버튼 클릭 등)

---

### 📊 **sensors/ 폴더**
- **역할**: 스마트폰의 센서 데이터를 읽고 관리
- **포함 파일**:
  - `SensorManager.kt` - 센서 데이터 수집
  - `AccelerometerListener.kt` - 가속도계 데이터 수신
  - `GyroscopeListener.kt` - 자이로스코프 데이터 수신
- **하는 일**: 
  - 스마트폰의 내장 센서에서 데이터 읽기
  - 센서 데이터를 정리해서 다른 코드에 전달

---

### 🎨 **ui/ 폴더**
- **역할**: 사용자가 보는 화면 UI 담당
- **포함 파일**:
  - `BalanceDisplayFragment.kt` - 균형 상태 표시 화면
  - `CalibrationFragment.kt` - 캘리브레이션 화면
  - `SettingsFragment.kt` - 설정 화면
  - `GuidanceDisplayFragment.kt` - 균형 개선 가이드 표시
- **하는 일**: 
  - 원 그리기, 텍스트 표시 등 화면 구성
  - 버튼, 메뉴 등 사용자 상호작용 요소

---

### 📋 **models/ 폴더**
- **역할**: 데이터 구조 정의 (어떤 정보를 저장할지 결정)
- **포함 파일**:
  - `BalanceData.kt` - 균형 측정 데이터 구조
  - `CalibrationData.kt` - 캘리브레이션 정보 구조
  - `MeasurementRecord.kt` - 측정 기록 구조
- **하는 일**: 
  - "균형 데이터에는 각도, 시간, 기기ID 정보가 들어간다"라고 정의
  - 다른 파일들이 데이터를 일관되게 사용하도록 보장

---

### 🧮 **calculations/ 폴더**
- **역할**: 센서 데이터로부터 균형 정보를 계산
- **포함 파일**:
  - `BalanceCalculator.kt` - 각도 계산
  - `GuidanceCalculator.kt` - "몇 cm 옮겨야 하는가" 계산
  - `StabilityAnalyzer.kt` - 균형 상태 분석 (안정적/불안정한 상태)
- **하는 일**: 
  - 원시 센서 데이터 → 균형 각도로 변환
  - 균형 각도 → 조정 거리(cm)로 변환
  - 균형 상태 판정 (초록/노랑/빨강)

---

### ⚙️ **calibration/ 폴더**
- **역할**: 센서 캘리브레이션 관리
- **포함 파일**:
  - `CalibrationManager.kt` - 캘리브레이션 프로세스 관리
  - `CalibrationStorage.kt` - 캘리브레이션 데이터 저장/로드
  - `CalibrationValidator.kt` - 캘리브레이션이 올바른지 확인
- **하는 일**: 
  - 사용자가 캘리브레이션 버튼을 누르면 기준점 설정
  - 설정된 기준점을 저장
  - 다음 실행 시 저장된 기준점 불러오기

---

### 💾 **database/ 폴더**
- **역할**: 앱에서 생성된 데이터를 기기에 저장
- **포함 파일**:
  - `DatabaseHelper.kt` - 데이터베이스 관리
  - `MeasurementDAO.kt` - 측정 기록 저장/조회
  - `CalibrationDAO.kt` - 캘리브레이션 데이터 저장/조회
- **하는 일**: 
  - 과거 측정 데이터를 기기 저장소에 기록
  - 필요할 때 과거 데이터 불러오기
  - ℹ️ 이 로컬 데이터는 나중에 Azure로도 업로드 가능

---

### 🌐 **network/ 폴더**
- **역할**: Azure 클라우드와 통신
- **포함 파일**:
  - `AzureClient.kt` - Azure 연동 코드
  - `DataUploader.kt` - 로컬 데이터를 Azure로 업로드
  - `DataDownloader.kt` - Azure에서 데이터 다운로드
  - `NetworkManager.kt` - 인터넷 연결 상태 관리
- **하는 일**: 
  - 측정 데이터를 Azure 클라우드로 전송
  - 클라우드에서 데이터 받아오기
  - 인터넷 연결 확인

---

### 🔢 **constants/ 폴더**
- **역할**: 자주 사용되는 고정값 관리
- **포함 파일**:
  - `BalanceConstants.kt` - 균형 판정 기준값
- **하는 일**: 
  ```
  예시:
  - GREEN_THRESHOLD = 1도 (±1° 이내 = 초록색)
  - YELLOW_THRESHOLD = 5도 (±2~5° = 노란색)
  - RED_THRESHOLD = 5도 이상 (= 빨간색)
  - CALIBRATION_DURATION = 3초
  ```
  - 이 값들을 한곳에서 관리하면 나중에 변경하기 쉬움

---

### 🔐 **config/ 폴더**
- **역할**: Azure 설정 정보 관리
- **포함 파일**:
  - `AzureConfig.kt` - Azure 연결 정보 (API 키, 데이터베이스 주소 등)
- **하는 일**: 
  - Azure 계정 정보 저장
  - ⚠️ 민감한 정보이므로 `.env` 파일에서 관리

---

### 🛠️ **utils/ 폴더**
- **역할**: 여러 곳에서 재사용되는 도움말 함수
- **포함 파일**:
  - `MathUtils.kt` - 수학 계산 함수
  - `StringUtils.kt` - 문자 처리 함수
  - `DateUtils.kt` - 날짜/시간 처리 함수
  - `LogUtils.kt` - 로그 출력 함수
- **하는 일**: 
  - 반복되는 작업을 함수로 만들어 재사용
  - 코드 중복 줄이기

---

## 파일 간 데이터 흐름

Balance Fit이 실행되는 과정:

```
1. MainActivity 실행
   ↓
2. sensors/ → 센서 데이터 수집
   ↓
3. calibration/ → 저장된 기준점 로드
   ↓
4. calculations/ → 센서 데이터 + 기준점 → 균형 각도 계산
   ↓
5. ui/ → 계산된 데이터 화면에 표시 (원, 텍스트)
   ↓
6. database/ → 측정 데이터 로컬 저장
   ↓
7. network/ → 필요시 Azure로 업로드
```

---

## 개발 팁

- 📂 **폴더별 책임**: 각 폴더는 정해진 역할만 합니다
  - `ui/`에서 센서를 직접 읽지 않음
  - `calculations/`에서 화면에 그리지 않음
  
- 🔄 **재사용성**: 폴더 간 인터페이스가 명확하면 다른 팀원이 각 폴더를 독립적으로 개발 가능

- 🧪 **테스트 용이**: 각 폴더 기능을 독립적으로 테스트 가능

---

## 다음 단계

실제 구현에 앞서 [architecture/ 폴더의 상세 문서](architecture/)를 읽어보세요:
- [센서 데이터 처리](architecture/sensor-data-processing.md)
- [균형 계산 로직](architecture/balance-calculation.md)
- [Azure 연동](architecture/azure-integration.md)
- [캘리브레이션](architecture/calibration.md)
