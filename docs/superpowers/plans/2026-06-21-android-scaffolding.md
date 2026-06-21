# Android Scaffolding Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Jetpack Compose 기반 `com.balancefit.app` Android 프로젝트 골격을 생성하고 `assembleDebug`가 성공하는 상태를 만든다.

**Architecture:** 루트 Gradle 설정 + `app` 단일 모듈 구조로 시작한다. 기능 구현 대신 `ui/sensor/calibration/guidance` 경계를 인터페이스 중심으로 먼저 정의하고, 실행 가능한 최소 Compose Activity를 제공한다.

**Tech Stack:** Kotlin, Android Gradle Plugin, Gradle Kotlin DSL, Jetpack Compose, JUnit4, AndroidX Test

---

## File Structure Map

- Create: `settings.gradle.kts` — 멀티모듈 포함 설정(현재 `:app` 1개)
- Create: `build.gradle.kts` — 루트 플러그인 선언
- Create: `gradle.properties` — AndroidX/Compose 빌드 옵션
- Create: `gradle/libs.versions.toml` — 의존성/플러그인 버전 카탈로그
- Create: `app/build.gradle.kts` — 앱 모듈 빌드 설정
- Create: `app/proguard-rules.pro` — 기본 프로가드 룰
- Create: `app/src/main/AndroidManifest.xml` — 앱 진입점 선언
- Create: `app/src/main/java/com/balancefit/app/MainActivity.kt` — Compose 진입 Activity
- Create: `app/src/main/java/com/balancefit/app/ui/AppRoot.kt` — 루트 화면 컴포저블
- Create: `app/src/main/java/com/balancefit/app/sensor/SensorDataSource.kt` — 센서 경계 인터페이스
- Create: `app/src/main/java/com/balancefit/app/calibration/CalibrationService.kt` — 캘리브레이션 경계 인터페이스
- Create: `app/src/main/java/com/balancefit/app/guidance/GuidanceService.kt` — 가이드 경계 인터페이스
- Create: `app/src/main/java/com/balancefit/app/AppError.kt` — 공통 오류 타입
- Create: `app/src/test/java/com/balancefit/app/ui/AppRootTest.kt` — UI 문자열 단위 테스트
- Create: `app/src/test/java/com/balancefit/app/AppErrorTest.kt` — 오류 타입 단위 테스트
- Create: `app/src/androidTest/java/com/balancefit/app/ExampleInstrumentedTest.kt` — 계측 테스트 템플릿

### Task 1: Creating Gradle Root Skeleton

**Files:**
- Create: `settings.gradle.kts`
- Create: `build.gradle.kts`
- Create: `gradle.properties`
- Create: `gradle/libs.versions.toml`

- [ ] **Step 1: Create root settings**

```kotlin
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "balance-fit"
include(":app")
```

- [ ] **Step 2: Create root build script**

```kotlin
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}
```

- [ ] **Step 3: Create Gradle properties**

```properties
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
android.useAndroidX=true
kotlin.code.style=official
android.nonTransitiveRClass=true
```

- [ ] **Step 4: Create version catalog**

```toml
[versions]
agp = "8.7.0"
kotlin = "2.0.21"
coreKtx = "1.15.0"
junit = "4.13.2"
junitVersion = "1.2.1"
espressoCore = "3.6.1"
lifecycleRuntimeKtx = "2.8.7"
activityCompose = "1.10.0"
composeBom = "2025.01.00"

[libraries]
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
junit = { group = "junit", name = "junit", version.ref = "junit" }
androidx-junit = { group = "androidx.test.ext", name = "junit", version.ref = "junitVersion" }
androidx-espresso-core = { group = "androidx.test.espresso", name = "espresso-core", version.ref = "espressoCore" }
androidx-lifecycle-runtime-ktx = { group = "androidx.lifecycle", name = "lifecycle-runtime-ktx", version.ref = "lifecycleRuntimeKtx" }
androidx-activity-compose = { group = "androidx.activity", name = "activity-compose", version.ref = "activityCompose" }
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
androidx-compose-ui = { group = "androidx.compose.ui", name = "ui" }
androidx-compose-ui-graphics = { group = "androidx.compose.ui", name = "ui-graphics" }
androidx-compose-ui-tooling = { group = "androidx.compose.ui", name = "ui-tooling" }
androidx-compose-ui-tooling-preview = { group = "androidx.compose.ui", name = "ui-tooling-preview" }
androidx-compose-material3 = { group = "androidx.compose.material3", name = "material3" }
androidx-compose-ui-test-junit4 = { group = "androidx.compose.ui", name = "ui-test-junit4" }
androidx-compose-ui-test-manifest = { group = "androidx.compose.ui", name = "ui-test-manifest" }

[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
kotlin-compose = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
```

- [ ] **Step 5: Validate Gradle root config**

Run: `.\gradlew.bat help`  
Expected: BUILD SUCCESSFUL

- [ ] **Step 6: Commit**

```bash
git add settings.gradle.kts build.gradle.kts gradle.properties gradle/libs.versions.toml
git commit -m "chore: add android gradle root skeleton"
```

### Task 2: Creating App Module With Compose Entry Point

**Files:**
- Create: `app/build.gradle.kts`
- Create: `app/proguard-rules.pro`
- Create: `app/src/main/AndroidManifest.xml`
- Create: `app/src/main/java/com/balancefit/app/MainActivity.kt`
- Create: `app/src/main/java/com/balancefit/app/ui/AppRoot.kt`
- Create: `app/src/test/java/com/balancefit/app/ui/AppRootTest.kt`

- [ ] **Step 1: Write failing test for initial screen text**

```kotlin
package com.balancefit.app.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class AppRootTest {
    @Test
    fun titleText_matchesExpected() {
        assertEquals("Balance Fit", appTitle())
    }
}
```

- [ ] **Step 2: Run test to verify it fails**

Run: `.\gradlew.bat testDebugUnitTest --tests "com.balancefit.app.ui.AppRootTest"`  
Expected: FAIL with unresolved reference `appTitle`

- [ ] **Step 3: Create app module build file**

```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.balancefit.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.balancefit.app"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
```

- [ ] **Step 4: Implement minimal app entry code**

```kotlin
// app/src/main/java/com/balancefit/app/MainActivity.kt
package com.balancefit.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.balancefit.app.ui.AppRoot

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AppRoot() }
    }
}
```

```kotlin
// app/src/main/java/com/balancefit/app/ui/AppRoot.kt
package com.balancefit.app.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

fun appTitle(): String = "Balance Fit"

@Composable
fun AppRoot() {
    Text(text = appTitle())
}
```

```xml
<!-- app/src/main/AndroidManifest.xml -->
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <application android:label="Balance Fit">
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
```

- [ ] **Step 5: Add empty ProGuard rules file**

```pro
# Keep default rules for now.
```

- [ ] **Step 6: Run test to verify it passes**

Run: `.\gradlew.bat testDebugUnitTest --tests "com.balancefit.app.ui.AppRootTest"`  
Expected: PASS

- [ ] **Step 7: Commit**

```bash
git add app
git commit -m "feat: add compose app module scaffold"
```

### Task 3: Adding Domain Boundaries and Error Model

**Files:**
- Create: `app/src/main/java/com/balancefit/app/sensor/SensorDataSource.kt`
- Create: `app/src/main/java/com/balancefit/app/calibration/CalibrationService.kt`
- Create: `app/src/main/java/com/balancefit/app/guidance/GuidanceService.kt`
- Create: `app/src/main/java/com/balancefit/app/AppError.kt`
- Create: `app/src/test/java/com/balancefit/app/AppErrorTest.kt`

- [ ] **Step 1: Write failing test for error type contract**

```kotlin
package com.balancefit.app

import org.junit.Assert.assertEquals
import org.junit.Test

class AppErrorTest {
    @Test
    fun sensorUnavailable_messageIsStable() {
        val error = AppError.SensorUnavailable
        assertEquals("SENSOR_UNAVAILABLE", error.code)
    }
}
```

- [ ] **Step 2: Run test to verify it fails**

Run: `.\gradlew.bat testDebugUnitTest --tests "com.balancefit.app.AppErrorTest"`  
Expected: FAIL with unresolved reference `AppError`

- [ ] **Step 3: Implement minimal boundaries**

```kotlin
// app/src/main/java/com/balancefit/app/sensor/SensorDataSource.kt
package com.balancefit.app.sensor

interface SensorDataSource {
    fun start()
    fun stop()
}
```

```kotlin
// app/src/main/java/com/balancefit/app/calibration/CalibrationService.kt
package com.balancefit.app.calibration

interface CalibrationService {
    fun calibrate(): Boolean
}
```

```kotlin
// app/src/main/java/com/balancefit/app/guidance/GuidanceService.kt
package com.balancefit.app.guidance

interface GuidanceService {
    fun recommendation(): String
}
```

```kotlin
// app/src/main/java/com/balancefit/app/AppError.kt
package com.balancefit.app

sealed class AppError(val code: String) {
    data object SensorUnavailable : AppError("SENSOR_UNAVAILABLE")
    data object PermissionDenied : AppError("PERMISSION_DENIED")
}
```

- [ ] **Step 4: Run tests to verify pass**

Run: `.\gradlew.bat testDebugUnitTest --tests "com.balancefit.app.AppErrorTest"`  
Expected: PASS

- [ ] **Step 5: Commit**

```bash
git add app/src/main/java/com/balancefit/app app/src/test/java/com/balancefit/app/AppErrorTest.kt
git commit -m "feat: add domain interfaces and app error model"
```

### Task 4: Adding Instrumentation Template and Final Build Validation

**Files:**
- Create: `app/src/androidTest/java/com/balancefit/app/ExampleInstrumentedTest.kt`

- [ ] **Step 1: Add instrumentation test template**

```kotlin
package com.balancefit.app

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.balancefit.app", appContext.packageName)
    }
}
```

- [ ] **Step 2: Run full debug build**

Run: `.\gradlew.bat clean assembleDebug`  
Expected: BUILD SUCCESSFUL

- [ ] **Step 3: Run unit tests**

Run: `.\gradlew.bat testDebugUnitTest`  
Expected: BUILD SUCCESSFUL, tests passed

- [ ] **Step 4: Commit**

```bash
git add app/src/androidTest/java/com/balancefit/app/ExampleInstrumentedTest.kt
git commit -m "test: add instrumentation template and validate debug build"
```

### Task 5: Updating Developer Documentation

**Files:**
- Modify: `README.md`
- Modify: `docs/getting-started.md`

- [ ] **Step 1: Add Android scaffold run instructions to README**

```markdown
## Android 개발 시작

~~~bash
.\gradlew.bat assembleDebug
~~~
```

- [ ] **Step 2: Add local setup section to docs/getting-started.md**

```markdown
## Android 스캐폴딩 실행

1. JDK 17 설치
2. Android SDK Platform 35 설치
3. 프로젝트 루트에서 `.\gradlew.bat assembleDebug` 실행
```

- [ ] **Step 3: Verify docs and build command**

Run: `.\gradlew.bat assembleDebug`  
Expected: BUILD SUCCESSFUL

- [ ] **Step 4: Commit**

```bash
git add README.md docs/getting-started.md
git commit -m "docs: add android scaffold quick start"
```
