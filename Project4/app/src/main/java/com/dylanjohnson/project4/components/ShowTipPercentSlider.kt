package com.dylanjohnson.project4.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dylanjohnson.project4.ViewModelForTips
import java.text.NumberFormat

@Composable
fun ShowTipPercentSlider(tipViewModel: ViewModelForTips) {
    // show the slider, label, and calculated tip amount

    var sliderstate = remember { mutableStateOf(0f) }

    Card(
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            // two types of formatting used: $ and currency.
            val str = String.format(
                "Tip Percent: %.1f%% Tip Value: " + NumberFormat.getCurrencyInstance()
                    .format(tipViewModel.tipValue), tipViewModel.tipPercent
            )
            Text(str)
            Slider(
                value = sliderstate.value,
                valueRange = 0f..100f,
                onValueChange = {
                    // called every time the slider moves
                    sliderstate.value = it
                    tipViewModel.tipPercent = ( sliderstate.value )
                    tipViewModel.calculateTipValues()
                }
            )

            // setup a row of buttons for user convenience - optional feature.
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        // set the slide value and update the tip amount.
                        sliderstate.value = 10f
                        tipViewModel.tipPercent = ( sliderstate.value )
                        tipViewModel.calculateTipValues()
                    }
                ) {
                    Text(text = "10%")
                }
                Button(
                    onClick = {
                        sliderstate.value = 15f
                        tipViewModel.tipPercent = ( sliderstate.value )
                        tipViewModel.calculateTipValues()
                    }
                ) {
                    Text(text = "15%")
                }
                Button(
                    onClick = {
                        sliderstate.value = 20f
                        tipViewModel.tipPercent = ( sliderstate.value )
                        tipViewModel.calculateTipValues()
                    }
                ) {
                    Text(text = "20%")
                }
                Button(
                    onClick = {
                        sliderstate.value = 25f
                        tipViewModel.tipPercent = ( sliderstate.value )
                        tipViewModel.calculateTipValues()
                    }
                ) {
                    Text(text = "25%")
                }
            }
        }
    }
}

@Composable
private fun ShowTipPercentSlider(tipPercent: Float, tipValue: Float, updatePercent: (Float) -> Unit) {
    // show the slider, label, and calculated tip amount

    var sliderstate = remember { mutableStateOf(0f) }

    Card(
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            // two types of formatting used: $ and currency.
            val str = String.format(
                "Tip Percent: %.1f%% Tip Value: " + NumberFormat.getCurrencyInstance()
                    .format(tipValue), tipPercent
            )
            Text(str)
            Slider(
                value = sliderstate.value,
                valueRange = 0f..100f,
                onValueChange = {
                    // called every time the slider moves
                    sliderstate.value = it
                    updatePercent( sliderstate.value )
                }
            )

            // setup a row of buttons for user convenience - optional feature.
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        // set the slide value and update the tip amount.
                        sliderstate.value = 10f
                        updatePercent( sliderstate.value )
                    }
                ) {
                    Text(text = "10%")
                }
                Button(
                    onClick = {
                        sliderstate.value = 15f
                        updatePercent( sliderstate.value )
                    }
                ) {
                    Text(text = "15%")
                }
                Button(
                    onClick = {
                        sliderstate.value = 20f
                        updatePercent( sliderstate.value )
                    }
                ) {
                    Text(text = "20%")
                }
                Button(
                    onClick = {
                        sliderstate.value = 25f
                        updatePercent( sliderstate.value )
                    }
                ) {
                    Text(text = "25%")
                }
            }
        }
    }
}
