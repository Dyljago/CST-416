package com.dylanjohnson.project6.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.dylanjohnson.project6.data.JokesViewModelInterface

@Composable
public fun SearchScreen(navController: NavHostController, viewModel: JokesViewModelInterface) {
    // one variable for each field in the form
    var searchString by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Search for a Joke",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
        TextField(
            value = searchString,
            // this lambda function is called when the user enters data.
            // the value of searchString is set to textFieldContents whenever the text field contents change.
            // The login form uses the same technique but omitted the "textFieldContents ->" part.
            // Instead, it used "it" to refer to the contents of the text field.
            // Both techniques are valid.
            onValueChange = { textFieldContents -> searchString = textFieldContents },
            label = { Text("Joke Question") }
        )

        Button(
            onClick = {
                viewModel.findJokesByKeyword(searchString)
                stringToSearch = searchString
                navController.navigate("show-jokes")
            }
        ) {
            Text("Search")
        }
    }
}