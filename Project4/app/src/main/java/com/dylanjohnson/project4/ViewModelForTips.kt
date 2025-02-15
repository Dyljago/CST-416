package com.dylanjohnson.project4

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

// viewModel will allow all properties and calculations to be done in this file.
class ViewModelForTips : ViewModel() {
    // variable for the cost of food. This will be moved to a viewModel in future revision.
    var mealCost by mutableStateOf(10f)

    // this will be moved to a viewModel in future revision.
    var numberOfFriends by mutableStateOf(1)

    // 0% to 100% of meal value
    var tipPercent by mutableStateOf(0f)

    // tipValue = meal * tipPercent
    var tipValue by mutableStateOf(0f)

    // meal + tip value
    var totalBill by mutableStateOf(0f)

    // totalBill / numberOfPeople
    var totalPerPerson by mutableStateOf(0f)

    fun calculateTipValues() {
        tipValue = mealCost * tipPercent / 100
        totalBill = mealCost + tipValue
        totalPerPerson = totalBill / numberOfFriends
    }
}