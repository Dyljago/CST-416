package com.dylanjohnson.project4

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.math.roundToInt

class ViewModelForTemperatures : ViewModel() {

    // state of current temp. This can be toggled with the switch control.
    var isFahrenheit by mutableStateOf(true)

    // calculated result. This will be updated when the calculateConversion function is called
    var convertedTemperature by mutableStateOf("")

    // change the stats of temperature when the switch control is toggled.
    fun doSwitchToggle() {
        isFahrenheit = !isFahrenheit
    }

    // return a converted value given an input
    fun calculateConversion(inputValue: String) {

        try {
            val temperature = inputValue.toInt()

            if (isFahrenheit) {
                // convert to C /U2103 = C
                convertedTemperature = ((temperature - 32) * 5/9f).roundToInt().toString() + "\u2103"
            } else {
                // convert to F /U2109 = F
                convertedTemperature = ((temperature * 9/5f) + 32).roundToInt().toString() + "\u2109"
            }
        } catch (e: Exception) {
            // something went wrong with .toInt() conversion. User probably input invalid data.
            convertedTemperature = "Error"
        }
    }
}