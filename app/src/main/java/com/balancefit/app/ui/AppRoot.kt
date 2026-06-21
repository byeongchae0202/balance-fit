package com.balancefit.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.balancefit.app.AppError
import com.balancefit.app.sensor.AndroidSensorDataSource
import kotlin.math.abs
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun appTitle(): String = "Balance Fit 대시보드"

fun balanceStatusLabel(angle: Float): String = when {
    abs(angle) <= 1f -> "완벽"
    abs(angle) <= 5f -> "약간 기울어짐"
    else -> "심하게 기울어짐"
}

fun balanceStatusColor(angle: Float): Color = when {
    abs(angle) <= 1f -> Color(0xFF2E7D32)
    abs(angle) <= 5f -> Color(0xFFF9A825)
    else -> Color(0xFFC62828)
}

fun guidanceMessage(angle: Float): String = when {
    abs(angle) <= 1f -> "현재 상태가 안정적입니다. 그대로 유지하세요."
    angle > 0f -> "오른쪽이 높습니다. 오른쪽을 조금 낮춰보세요."
    else -> "왼쪽이 높습니다. 왼쪽을 조금 낮춰보세요."
}

fun sensorConnectionText(hasError: Boolean): String = if (hasError) "센서 오류" else "센서 연결됨"

private fun formatTimestamp(timestampMillis: Long): String {
    if (timestampMillis <= 0L) return "데이터 수신 대기 중"
    val formatter = SimpleDateFormat("HH:mm:ss", Locale.KOREA)
    return "최근 업데이트: ${formatter.format(Date(timestampMillis))}"
}

@Composable
fun AppRoot() {
    var angle by remember { mutableFloatStateOf(0f) }
    var profile by remember { mutableStateOf("indoor") }
    var sensorError by remember { mutableStateOf<AppError?>(null) }
    var lastUpdatedMillis by remember { mutableLongStateOf(0L) }

    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current
    val sensorDataSource = remember(context) { AndroidSensorDataSource(context.applicationContext) }
    val status = balanceStatusLabel(angle)
    val statusColor = balanceStatusColor(angle)

    if (!isPreview) {
        DisposableEffect(sensorDataSource) {
            sensorDataSource.startStreaming(
                onData = { reading ->
                    angle = reading.angle
                    lastUpdatedMillis = reading.timestampMillis
                    sensorError = null
                },
                onError = { error ->
                    sensorError = error
                }
            )
            onDispose { sensorDataSource.stopStreaming() }
        }
    }

    MaterialTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = appTitle(), style = MaterialTheme.typography.headlineSmall)
                Text(text = "실시간 균형 상태를 확인하고 빠르게 조정하세요.")

                Card(colors = CardDefaults.cardColors(containerColor = statusColor.copy(alpha = 0.12f))) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("현재 각도: ${String.format("%.1f", angle)}°", style = MaterialTheme.typography.titleMedium)
                        Text("상태: $status", color = statusColor, style = MaterialTheme.typography.titleMedium)
                        Text(sensorConnectionText(sensorError != null))
                        Text(formatTimestamp(lastUpdatedMillis))
                    }
                }

                if (sensorError != null) {
                    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("센서 연결에 실패했습니다.", color = Color(0xFFB71C1C))
                            Text("기기 센서 지원 상태를 확인해주세요.")
                        }
                    }
                }

                Card {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("캘리브레이션", style = MaterialTheme.typography.titleMedium)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(onClick = { profile = "indoor" }) { Text("실내") }
                            Button(onClick = { profile = "outdoor" }) { Text("실외") }
                        }
                        Text("선택된 프로필: $profile")
                    }
                }

                Card {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("균형 개선 가이드", style = MaterialTheme.typography.titleMedium)
                        Text(guidanceMessage(angle))
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppRootPreview() {
    AppRoot()
}
