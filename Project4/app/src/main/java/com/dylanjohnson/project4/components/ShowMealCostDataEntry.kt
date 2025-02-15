package com.dylanjohnson.project4.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.dylanjohnson.project4.ViewModelForTips

@Composable
fun ShowMealCostDataEntry(tipViewModel: ViewModelForTips) {
    TextField(
        value = tipViewModel.mealCost.toString(),
        label = { Text(text = "Cost of the meal") },
        modifier = Modifier.fillMaxWidth(),
        textStyle = MaterialTheme.typography.headlineMedium,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = {
            // attempt to convert the input value to a Float
            val inputValue = it.toFloatOrNull()
            if (inputValue != null) {
                // run the callback function - see second item in the parameter list
                tipViewModel.mealCost = inputValue
                tipViewModel.calculateTipValues()
            } else {
                Log.d("ShowMealCost", "could not convert to float")
            }
        }
    )
}

@Composable
// 1st param: current value of the meal. Changes when the user supplies a number.
// 2nd param: function sent back to parent. "Unit" means anonymous function in Kotlin.
private fun ShowMealCostDataEntry(mealCost: Float, updateMealPrice: (Float) -> Unit) {
    TextField(
        value = mealCost.toString(),
        label = { Text(text = "Cost of the meal") },
        modifier = Modifier.fillMaxWidth(),
        textStyle = MaterialTheme.typography.headlineMedium,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = {
            // attempt to convert the input value to a Float
            val inputValue = it.toFloatOrNull()
            if (inputValue != null) {
                // run the callback function - see second item in the parameter list
                updateMealPrice(inputValue)
            } else {
                Log.d("ShowMealCost", "could not convert to float")
            }
        }
    )
}