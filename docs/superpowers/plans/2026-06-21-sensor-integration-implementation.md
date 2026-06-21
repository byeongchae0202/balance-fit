# Sensor Integration Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Android 기기 센서 입력으로 대시보드 각도/상태/가이드가 실시간 갱신되도록 만든다.

**Architecture:** 센서 수집 구현은 `AndroidSensorDataSource`로 분리하고, 각도 계산은 순수 함수로 분리해 테스트 가능하게 유지한다. `AppRoot`는 스트리밍 생명주기와 UI 렌더링만 담당한다.

**Tech Stack:** Kotlin, Android SensorManager, Jetpack Compose, JUnit4

---

## File Structure Map
- Create: `app/src/main/java/com/balancefit/app/sensor/SensorReading.kt`
- Create: `app/src/main/java/com/balancefit/app/sensor/SensorMath.kt`
- Create: `app/src/main/java/com/balancefit/app/sensor/AndroidSensorDataSource.kt`
- Modify: `app/src/main/java/com/balancefit/app/sensor/SensorDataSource.kt`
- Modify: `app/src/main/java/com/balancefit/app/ui/AppRoot.kt`
- Create: `app/src/test/java/com/balancefit/app/sensor/SensorMathTest.kt`

### Task 1: Implement Sensor Math and Tests

**Files:**
- Create: `app/src/main/java/com/balancefit/app/sensor/SensorMath.kt`
- Create: `app/src/test/java/com/balancefit/app/sensor/SensorMathTest.kt`

- [ ] **Step 1: Write failing test**

```kotlin
@Test
fun flatSurface_returnsNearZero() {
    val angle = calculateTiltAngle(ax = 0f, ay = 0f, az = 9.8f)
    assertEquals(0f, angle, 0.3f)
}
```

- [ ] **Step 2: Run test to verify fail**

Run: `.\gradlew.bat testDebugUnitTest --tests "com.balancefit.app.sensor.SensorMathTest"`  
Expected: FAIL with unresolved `calculateTiltAngle`

- [ ] **Step 3: Add minimal implementation**

```kotlin
fun calculateTiltAngle(ax: Float, ay: Float, az: Float): Float {
    val roll = atan2(ax.toDouble(), az.toDouble())
    val pitch = atan2(ay.toDouble(), az.toDouble())
    return Math.toDegrees(sqrt(roll * roll + pitch * pitch)).toFloat()
}
```

- [ ] **Step 4: Run test to pass**

Run: `.\gradlew.bat testDebugUnitTest --tests "com.balancefit.app.sensor.SensorMathTest"`  
Expected: PASS

- [ ] **Step 5: Commit**

```bash
git add app/src/main/java/com/balancefit/app/sensor/SensorMath.kt app/src/test/java/com/balancefit/app/sensor/SensorMathTest.kt
git commit -m "feat(sensor): add tilt angle math"
```

### Task 2: Add Android SensorDataSource Implementation

**Files:**
- Create: `app/src/main/java/com/balancefit/app/sensor/SensorReading.kt`
- Create: `app/src/main/java/com/balancefit/app/sensor/AndroidSensorDataSource.kt`
- Modify: `app/src/main/java/com/balancefit/app/sensor/SensorDataSource.kt`

- [ ] **Step 1: Write failing compile expectation**

```kotlin
val dataSource = AndroidSensorDataSource(context)
```

- [ ] **Step 2: Run unit test compile target to verify fail**

Run: `.\gradlew.bat testDebugUnitTest`  
Expected: FAIL with unresolved `AndroidSensorDataSource`

- [ ] **Step 3: Implement data source**

```kotlin
interface SensorDataSource {
    fun isAvailable(): Boolean
    fun startStreaming(onData: (SensorReading) -> Unit, onError: (AppError) -> Unit)
    fun stopStreaming()
}
```

`AndroidSensorDataSource`는 accelerometer/gyroscope listener를 등록하고 계산 각도를 `SensorReading`으로 콜백한다.

- [ ] **Step 4: Run tests to verify pass**

Run: `.\gradlew.bat testDebugUnitTest`  
Expected: PASS

- [ ] **Step 5: Commit**

```bash
git add app/src/main/java/com/balancefit/app/sensor/SensorDataSource.kt app/src/main/java/com/balancefit/app/sensor/SensorReading.kt app/src/main/java/com/balancefit/app/sensor/AndroidSensorDataSource.kt
git commit -m "feat(sensor): implement android sensor data source"
```

### Task 3: Wire Sensor Stream to Dashboard UI

**Files:**
- Modify: `app/src/main/java/com/balancefit/app/ui/AppRoot.kt`

- [ ] **Step 1: Write failing UI logic test for status label with sensor angle**

```kotlin
@Test
fun statusLabel_forSensorAngle() {
    assertEquals("심하게 기울어짐", balanceStatusLabel(8.2f))
}
```

- [ ] **Step 2: Run target tests and verify fail if behavior mismatched**

Run: `.\gradlew.bat testDebugUnitTest --tests "com.balancefit.app.ui.BalanceUiLogicTest"`  
Expected: FAIL when rule mismatch

- [ ] **Step 3: Connect AppRoot to sensor stream**

`DisposableEffect`에서 스트리밍 시작/종료하고, 버튼 데모 상태 변경 로직을 제거한다.

- [ ] **Step 4: Run full regression**

Run: `.\gradlew.bat testDebugUnitTest`  
Expected: PASS

- [ ] **Step 5: Commit**

```bash
git add app/src/main/java/com/balancefit/app/ui/AppRoot.kt
git commit -m "feat(ui): connect dashboard to device sensors"
```
