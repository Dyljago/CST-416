package com.dylanjohnson.project2

import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import com.dylanjohnson.project2.ui.theme.Project2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Project2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BurgerOrder("Android")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BurgerOrder(name: String) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    Surface {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(scrollState)) {

            var order by remember { mutableStateOf(false) }

            // Header Text
            Text("Order a burger", modifier = Modifier
                .border(2.dp, Color.Blue)
                .padding(4.dp)
                .align(Alignment.Start))

            // Input Field for name
            val inputtedName = remember { mutableStateOf("") }
            OutlinedTextField(
                value = inputtedName.value,
                onValueChange = { inputtedName.value = it; order = false },
                label = { Text("Name for order") },
                singleLine = true,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .padding(2.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.Transparent,
                    focusedBorderColor = Color.Blue,
                    unfocusedBorderColor = Color.DarkGray,
                    cursorColor = Color.Cyan,
                    focusedLabelColor = Color.Blue,
                    unfocusedLabelColor = Color.DarkGray
                )
            )

            // Checkbox
            var cheeseChecked by remember { mutableStateOf(false) }
            var lettuceChecked by remember { mutableStateOf(false) }
            var tomatoChecked by remember { mutableStateOf(false) }
            Row (modifier = Modifier.padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically){

                Checkbox(
                    checked = cheeseChecked,
                    onCheckedChange = { cheeseChecked = it; order = false }
                )
                Text("Cheese", modifier = Modifier.padding(start = 8.dp))
                Checkbox(
                    checked = lettuceChecked,
                    onCheckedChange = { lettuceChecked = it; order = false }
                )
                Text("Lettuce", modifier = Modifier.padding(start = 8.dp))
                Checkbox(
                    checked = tomatoChecked,
                    onCheckedChange = { tomatoChecked = it; order = false }
                )
                Text("Tomato", modifier = Modifier.padding(start = 8.dp))
            }

            Divider()

            // Radio Buttons
            val radioOptions = listOf("Small", "Medium", "Large")
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
            Row(
                modifier = Modifier.padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically // Align all items vertically
            ) {
                radioOptions.forEach { text ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = text == selectedOption,
                            onClick = { onOptionSelected(text); order = false },
                            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                        )
                        Text(text = text, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }

            // Side Switch
            var switchChecked by remember { mutableStateOf(false) }
            Text("Add a side?")
            Switch(
                checked = switchChecked,
                onCheckedChange = { switchChecked = it; order = false }
            )

            val sideChosen = remember { mutableStateOf("") }
            if (switchChecked) {
                // expanded is used to control the visibility of the DropdownMenu
                var expanded by remember { mutableStateOf(false) }

                // List of sides to display in the DropdownMenu
                val sides = listOf("French Fries", "Onion Rings", "Tater Tots", "Mozzarella Sticks", "Wings")
                var selectedSide by remember { mutableStateOf(0) }
                sideChosen.value = sides[selectedSide]

                Text("Choose your side!", modifier = Modifier.padding(top = 12.dp, bottom = 8.dp))

                Box {
                    OutlinedTextField(

                        value = sides[selectedSide],
                        onValueChange = { },

                        label = { Text("Select Side") },
                        trailingIcon = {
                            IconButton(onClick = { expanded = !expanded }) {
                                Icon(Icons.Filled.ArrowDropDown, "dropdown")
                            }
                        },
                        readOnly = true
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        sides.forEachIndexed { index, item ->
                            DropdownMenuItem(text = {
                                Text(item)
                            }, onClick = {
                                selectedSide = index
                                sideChosen.value = sides[selectedSide]
                                order = false
                                expanded = false
                            })
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            // Tip Slider
            var sliderTipValue by remember { mutableStateOf(0f) }
            Text("Tip Percentage ${sliderTipValue.toInt()}%")
            Slider(
                value = sliderTipValue.toInt().toFloat(),
                onValueChange = { sliderTipValue = it; order = false },
                valueRange = 0f..100f
            )

            // Order Button
            Button(onClick = {
                if (inputtedName.value != "") {
                    order = true
                } else {
                    val errorMessage = Toast.makeText(
                        context,
                        Html.fromHtml("<font color='#ff1100' ><b>NO NAME INPUTTED</b></font>"),
                        Toast.LENGTH_LONG
                    )
                    errorMessage.show()
                }
            } ) {
                Text("Place Order")
            }
            // Display the order text
            if (order) {
                Text(
                    "Order Details",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(top = 12.dp, bottom = 8.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 6.dp)
                ) {
                    Text(
                        "Name: ",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        fontSize = 16.sp
                    )
                    Text(
                        inputtedName.value,
                        fontSize = 16.sp
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "Has cheese?: ",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        fontSize = 16.sp
                    )
                    Text(
                        cheeseChecked.toString(),
                        fontSize = 16.sp
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "Has lettuce?: ",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        fontSize = 16.sp
                    )
                    Text(
                        lettuceChecked.toString(),
                        fontSize = 16.sp
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "Has tomatoes?: ",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        fontSize = 16.sp
                    )
                    Text(
                        tomatoChecked.toString(),
                        fontSize = 16.sp
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "Burger Size: ",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        fontSize = 16.sp
                    )
                    Text(
                        selectedOption,
                        fontSize = 16.sp
                    )
                }
                if(switchChecked) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            "Chosen Side: ",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            fontSize = 16.sp
                        )
                        Text(
                            sideChosen.value,
                            fontSize = 16.sp
                        )
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "Tipped: ",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        fontSize = 16.sp
                    )
                    Text(
                        sliderTipValue.toInt().toString() + "%",
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSearch(name: String) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    Surface {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(scrollState)) {

            var search by remember { mutableStateOf(false) }

            // Header Text
            Text("  Search for a book  ", modifier = Modifier
                .border(2.dp, Color.Blue)
                .padding(4.dp)
                .align(Alignment.Start))

            // Input Field for name
            val inputtedName = remember { mutableStateOf("") }
            OutlinedTextField(
                value = inputtedName.value,
                onValueChange = { inputtedName.value = it; search = false },
                label = { Text("Name for search") },
                singleLine = true,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .padding(2.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.Transparent,
                    focusedBorderColor = Color.Blue,
                    unfocusedBorderColor = Color.DarkGray,
                    cursorColor = Color.Cyan,
                    focusedLabelColor = Color.Blue,
                    unfocusedLabelColor = Color.DarkGray
                )
            )

            // Checkbox
            var fictionChecked by remember { mutableStateOf(false) }
            var mysteryChecked by remember { mutableStateOf(false) }
            var dramaChecked by remember { mutableStateOf(false) }
            Row (modifier = Modifier.padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically){

                Checkbox(
                    checked = fictionChecked,
                    onCheckedChange = { fictionChecked = it; search = false }
                )
                Text("Fiction", modifier = Modifier.padding(start = 8.dp))
                Checkbox(
                    checked = mysteryChecked,
                    onCheckedChange = { mysteryChecked = it; search = false }
                )
                Text("Mystery", modifier = Modifier.padding(start = 8.dp))
                Checkbox(
                    checked = dramaChecked,
                    onCheckedChange = { dramaChecked = it; search = false }
                )
                Text("Drama", modifier = Modifier.padding(start = 8.dp))
            }

            Divider()

            // Radio Buttons
            val radioOptions = listOf("Hardcover", "Paperback", "Digital")
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
            Row(
                modifier = Modifier.padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically // Align all items vertically
            ) {
                radioOptions.forEach { text ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = text == selectedOption,
                            onClick = { onOptionSelected(text); search = false },
                            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                        )
                        Text(text = text, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }

            // Language Switch
            val languageChosen = remember { mutableStateOf("English") }
            var switchChecked by remember { mutableStateOf(false) }
            Text("Choose Language?")
            Switch(
                checked = switchChecked,
                onCheckedChange = { switchChecked = it; search = false; languageChosen.value = "English" }
            )

            if (switchChecked) {
                // expanded is used to control the visibility of the DropdownMenu
                var expanded by remember { mutableStateOf(false) }

                // List of languages to display in the DropdownMenu
                val languages = listOf("English", "French", "Spanish", "Japanese", "German")
                var selectedLanguage by remember { mutableStateOf(0) }
                languageChosen.value = languages[selectedLanguage]

                Text("Choose your language!", modifier = Modifier.padding(top = 12.dp, bottom = 8.dp))

                Box {
                    OutlinedTextField(

                        value = languages[selectedLanguage],
                        onValueChange = { },

                        label = { Text("Select Language") },
                        trailingIcon = {
                            IconButton(onClick = { expanded = !expanded }) {
                                Icon(Icons.Filled.ArrowDropDown, "dropdown")
                            }
                        },
                        readOnly = true
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        languages.forEachIndexed { index, item ->
                            DropdownMenuItem(text = {
                                Text(item)
                            }, onClick = {
                                selectedLanguage = index
                                languageChosen.value = languages[selectedLanguage]
                                search = false
                                expanded = false
                            })
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            // Price Slider
            var sliderPriceValue by remember { mutableStateOf(1f) }
            Text("Price Range ${sliderPriceValue.toInt()}$ to 100$")
            Slider(
                value = sliderPriceValue.toInt().toFloat(),
                onValueChange = { sliderPriceValue = it; search = false },
                valueRange = 1f..100f
            )

            // Search Button
            Button(onClick = {
                if (inputtedName.value != "") {
                    search = true
                } else {
                    val errorMessage = Toast.makeText(
                        context,
                        Html.fromHtml("<font color='#ff1100' ><b>NO NAME INPUTTED</b></font>"),
                        Toast.LENGTH_LONG
                    )
                    errorMessage.show()
                }
            } ) {
                Text("Search")
            }
            // Display the search text
            if (search) {
                Text(
                    "Search Details",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(top = 12.dp, bottom = 8.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 6.dp)
                ) {
                    Text(
                        "Name: ",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        fontSize = 16.sp
                    )
                    Text(
                        inputtedName.value,
                        fontSize = 16.sp
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "Book Genres: ",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        fontSize = 16.sp
                    )
                    var genres = ""
                    if (fictionChecked) {
                        genres += "Fiction"
                    }
                    if (mysteryChecked) {
                        if (genres != "") {
                            genres += ", "
                        }
                        genres += "Mystery"
                    }
                    if (dramaChecked) {
                        if (genres != "") {
                            genres += ", "
                        }
                        genres += "Drama"
                    }
                    if (genres == "") {
                        genres = "Any"
                    }
                    Text(
                        genres,
                        fontSize = 16.sp
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "Book Type: ",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        fontSize = 16.sp
                    )
                    Text(
                        selectedOption,
                        fontSize = 16.sp
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "Chosen Language: ",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        fontSize = 16.sp
                    )
                    Text(
                        languageChosen.value,
                        fontSize = 16.sp
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "Price Range: ",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        fontSize = 16.sp
                    )
                    Text(
                        sliderPriceValue.toInt().toString() + "$ to 100$",
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
