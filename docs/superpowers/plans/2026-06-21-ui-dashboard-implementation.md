# UI Dashboard Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Balance Fit 앱 시작 화면을 카드 기반 대시보드 UI로 개선한다.

**Architecture:** `AppRoot`에서 화면 전체를 구성하고, 각도 기반 상태 계산은 순수 함수로 분리한다. UI는 상태 카드/캘리브레이션 카드/가이드 카드로 나누고 테스트는 상태 계산 로직 중심으로 작성한다.

**Tech Stack:** Kotlin, Jetpack Compose, JUnit4

---

## File Structure Map
- Modify: `app/src/main/java/com/balancefit/app/ui/AppRoot.kt`
- Create: `app/src/test/java/com/balancefit/app/ui/BalanceUiLogicTest.kt`

### Task 1: Adding Balance Status Logic

**Files:**
- Modify: `app/src/main/java/com/balancefit/app/ui/AppRoot.kt`
- Create: `app/src/test/java/com/balancefit/app/ui/BalanceUiLogicTest.kt`

- [ ] **Step 1: Write failing tests for status rules**

```kotlin
package com.balancefit.app.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class BalanceUiLogicTest {
    @Test
    fun angleWithinOne_returnsPerfect() {
        assertEquals("완벽", balanceStatusLabel(0.8f))
    }
}
```

- [ ] **Step 2: Run test to verify it fails**

Run: `.\gradlew.bat testDebugUnitTest --tests "com.balancefit.app.ui.BalanceUiLogicTest"`  
Expected: FAIL with unresolved reference `balanceStatusLabel`

- [ ] **Step 3: Implement minimal logic**

```kotlin
fun balanceStatusLabel(angle: Float): String = when {
    kotlin.math.abs(angle) <= 1f -> "완벽"
    kotlin.math.abs(angle) <= 5f -> "약간 기울어짐"
    else -> "심하게 기울어짐"
}
```

- [ ] **Step 4: Run tests to verify pass**

Run: `.\gradlew.bat testDebugUnitTest --tests "com.balancefit.app.ui.BalanceUiLogicTest"`  
Expected: PASS

- [ ] **Step 5: Commit**

```bash
git add app/src/main/java/com/balancefit/app/ui/AppRoot.kt app/src/test/java/com/balancefit/app/ui/BalanceUiLogicTest.kt
git commit -m "feat(ui): add balance status logic"
```

### Task 2: Building Dashboard Compose Screen

**Files:**
- Modify: `app/src/main/java/com/balancefit/app/ui/AppRoot.kt`
- Modify: `app/src/test/java/com/balancefit/app/ui/AppRootTest.kt`

- [ ] **Step 1: Update screen test expectation**

```kotlin
@Test
fun titleText_matchesExpected() {
    assertEquals("Balance Fit 대시보드", appTitle())
}
```

- [ ] **Step 2: Run test to verify it fails**

Run: `.\gradlew.bat testDebugUnitTest --tests "com.balancefit.app.ui.AppRootTest"`  
Expected: FAIL

- [ ] **Step 3: Implement dashboard UI**

```kotlin
@Composable
fun AppRoot() {
    // 카드 기반 대시보드(상태/캘리브레이션/가이드)
}
```

- [ ] **Step 4: Run full Android unit tests**

Run: `.\gradlew.bat testDebugUnitTest`  
Expected: PASS

- [ ] **Step 5: Commit**

```bash
git add app/src/main/java/com/balancefit/app/ui/AppRoot.kt app/src/test/java/com/balancefit/app/ui/AppRootTest.kt
git commit -m "feat(ui): build dashboard home screen"
```
