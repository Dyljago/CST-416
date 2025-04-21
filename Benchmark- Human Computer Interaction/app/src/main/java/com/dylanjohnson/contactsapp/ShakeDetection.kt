package com.dylanjohnson.contactsapp

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
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
import com.dylanjohnson.contactsapp.data.ContactViewModel
import com.dylanjohnson.contactsapp.data.ContactViewModelInterface
import com.dylanjohnson.contactsapp.models.PersonModel
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
    var dialogCountdown by mutableStateOf(10)
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
                dialogCountdown = 10
                Log.d("ShakeDetector", "Shake detected: $accelerationChange")
            }
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    fun register() {
        isFirstReading = true
        readingCount = 0
        accelerometer?.let {
            sensorManager.registerListener(
                sensorEventListener,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
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

    fun logEmergency(context: Context, viewModel: ContactViewModelInterface) {
        val contacts = viewModel.contactsList
            .filterIsInstance<PersonModel>()
            .filter { it.getEmergencyContact() }

        // Extract phone numbers
        val phoneNumbers = contacts.map { it.getPhoneNum() }

        // Join them into a comma-separated string for group SMS
        val recipients = phoneNumbers.joinToString(",")

        val message = "EMERGENCY! SOS! Please check in with me immediately."

        sendGroupTextMessage(context, recipients, message)
    }
}

fun sendGroupTextMessage(context: Context, recipients: String, message: String) {
    val uri = Uri.parse("smsto:$recipients")
    val intent = Intent(Intent.ACTION_SENDTO, uri)
    intent.putExtra("sms_body", message)

    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Could not send SMS: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun ShakeDetectorUI(handler: ShakeDetectorHandler, viewModel: ContactViewModelInterface) {
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
            handler.logEmergency(context, viewModel)
            handler.dismissDialog()
        }
    }
}
