package com.dylanjohnson.project4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dylanjohnson.project4.components.ShowMealCostDataEntry
import com.dylanjohnson.project4.components.ShowNumberOfFriendsEntry
import com.dylanjohnson.project4.components.ShowTipPercentSlider
import com.dylanjohnson.project4.components.ShowTotals
import com.dylanjohnson.project4.ui.theme.Project4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Project4Theme {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    MyTipCalculatorApp()
                }
            }
        }
    }
}

@Composable
fun MyTemperatureConverterApp() {

    // create an instance of viewModel
    var viewModel: ViewModelForTemperatures = viewModel()

    // pass each part of the viewModel properties and events as parameters to
    // the next level down. Google doesn't recommend passing the viewModel itself.
    MainScreen(
        isFahrenheit = viewModel.isFahrenheit,
        result = viewModel.convertedTemperature,
        convertTemp = { viewModel.calculateConversion(it) },
        doToggle = { viewModel.doSwitchToggle() }
    )
}

@Composable
fun MainScreen(
    // number in text input is interpreted as F (true) or C (false)
    isFahrenheit: Boolean,

    // value displayed to the user after conversion.
    result: String,

    // pass a function call up to the viewModel in MyApp with a string input.
    convertTemp: (String) -> Unit,

    // pass a function call up to the viewModel in MyApp that switches a boolean
    doToggle: () -> Unit
) {
    // keep state of temperature input
    var inputTextState by remember { mutableStateOf("") }

    val onTextChange = { text:String ->
        inputTextState = text
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            "Temperature Converter",
            modifier = Modifier.padding(24.dp),
            style = MaterialTheme.typography.headlineMedium
        )
        Card(
            elevation = CardDefaults.cardElevation(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(24.dp)
            ) {
                Switch(
                    checked = isFahrenheit,
                    onCheckedChange = { doToggle() }
                )
                OutlinedTextField(
                    value = inputTextState,
                    onValueChange = { onTextChange(it) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    label = {Text("Enter Degrees")},
                    modifier = Modifier.padding(12.dp),
                    textStyle = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    ),
                    trailingIcon = {
                        if (isFahrenheit) {
                            Text("\u2109", style = MaterialTheme.typography.bodyMedium)
                        } else {
                            Text("\u2103", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                )
            }
        }
        Text(
            result,
            modifier = Modifier.padding(18.dp),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight(5)
        )
        Button(
            onClick = { convertTemp(inputTextState) },
            modifier = Modifier.padding(10.dp)
        ) {
            if (isFahrenheit) {
                Text(text = "Convert to C")
            } else {
                Text(text = "Convert to F")
            }
        }
    }
}

@Composable
fun MyTipCalculatorApp() {
    // Empty space at the top
    Spacer(modifier = Modifier.height(16.dp))

    val tipViewModel by remember {
        mutableStateOf(ViewModelForTips())
    }

    // run to ensure the first rendering of the page has valid data.
    tipViewModel.calculateTipValues()

    /*// outline of the four components in this app
    Column(modifier = Modifier.padding(10.dp)) {
        // add two params. float and function to handle callback.
        // updateMealPrice on arbitrary name defined in the function parameter
        ShowMealCostDataEntry(mealCost, updateMealPrice = {
            // 'it' = parameter. i.e. 'inputValue' in fun.
            mealCost = it
            tipValue = mealCost * tipPercent / 100
            totalBill = mealCost + tipValue
            totalPerPerson = totalBill / numberOfFriends
        })
        ShowNumberOfFriendsEntry(numberOfFriends) {
            numberOfFriends = it
            totalBill = mealCost + tipValue
            totalPerPerson = totalBill / numberOfFriends
        }
        ShowTipPercentSlider(tipPercent, tipValue) {
            tipPercent = it
            tipValue = mealCost * tipPercent / 100
            totalBill = mealCost + tipValue
            totalPerPerson = totalBill / numberOfFriends
        }
        ShowTotals(totalBill, totalPerPerson) // no callback function required for these controls
    }*/

    // outline of the four components in this app
    Column(modifier = Modifier.padding(10.dp)) {
        ShowMealCostDataEntry( tipViewModel )
        ShowNumberOfFriendsEntry( tipViewModel )
        ShowTipPercentSlider( tipViewModel )
        ShowTotals( tipViewModel )
    }
}
