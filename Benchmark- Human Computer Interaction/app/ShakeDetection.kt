package com.dylanjohnson.benchmarkexamples

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.sqrt
import java.util.concurrent.TimeUnit

class ShakeDetectorHandler(private val context: Context) {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private val shakeThreshold = 3.0f
    private val minInterval = TimeUnit.MILLISECONDS.toNanos(200)
    private var lastShakeTime: Long = 0

    private var lastX = 0f
    private var lastY = 0f
    private var lastZ = 0f
    private var isFirstReading = true
    private var readingCount = 0
    private val initialReadingsToIgnore = 5

    var showDialog by mutableStateOf(false)
    var dialogCountdown by mutableStateOf(30)
        private set

    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            if (event.sensor.type != Sensor.TYPE_ACCELEROMETER) return

            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            if (isFirstReading) {
                lastX = x; lastY = y; lastZ = z
                isFirstReading = false
                return
            }

            if (readingCount < initialReadingsToIgnore) {
                readingCount++
                lastX = x; lastY = y; lastZ = z
                return
            }

            val deltaX = x - lastX
            val deltaY = y - lastY
            val deltaZ = z - lastZ
            val accelerationChange = sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ)

            lastX = x; lastY = y; lastZ = z
            val currentTime = System.nanoTime()
            if (accelerationChange > shakeThreshold && (currentTime - lastShakeTime > minInterval)) {
                lastShakeTime = currentTime
                showDialog = true
                dialogCountdown = 30
                Log.d("ShakeDetector", "Shake detected: $accelerationChange")
            }
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    fun register() {
        isFirstReading = true
        readingCount = 0
        accelerometer?.let {
            sensorManager.registerListener(sensorEventListener, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun unregister() {
        sensorManager.unregisterListener(sensorEventListener)
    }

    fun dismissDialog() {
        showDialog = false
        dialogCountdown = 30
    }

    fun tickCountdown() {
        dialogCountdown--
    }

    fun logEmergency() {
        Log.e("EMERGENCY", "SOS! Emergency triggered.")
        Toast.makeText(context, "EMERGENCY! SOS!", Toast.LENGTH_LONG).show()
    }
}

@Composable
fun ShakeDetectorUI(handler: ShakeDetectorHandler) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    if (handler.showDialog) {
        Dialog(onDismissRequest = {}) {
            Surface(
                modifier = Modifier.wrapContentSize(),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ProvideTextStyle(
                        value = TextStyle(
                            fontFamily = FontFamily.SansSerif,
                            textAlign = TextAlign.Center,
                            color = Color.Red
                        )
                    ) {
                        Text("Emergency Detected", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "If this is not an emergency, please press Cancel.",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Countdown: ${handler.dialogCountdown} seconds", fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = { handler.dismissDialog() }) {
                            Text("Cancel")
                        }
                    }
                }
            }
        }

        // Countdown
        LaunchedEffect(handler.showDialog) {
            while (handler.dialogCountdown > 0) {
                delay(1000)
                handler.tickCountdown()
            }
            handler.logEmergency()
            handler.dismissDialog()
        }
    }
}
