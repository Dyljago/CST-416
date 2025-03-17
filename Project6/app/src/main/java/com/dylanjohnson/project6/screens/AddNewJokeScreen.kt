package com.dylanjohnson.project6.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dylanjohnson.project6.data.JokesViewModelInterface
import com.dylanjohnson.project6.models.JokeModel

@Composable
public fun AddJokeScreen(navController: NavHostController, viewModel: JokesViewModelInterface) {
    var jokes = viewModel.jokesList

    // one variable for each field in the form
    var jokeQuestion by remember { mutableStateOf("") }
    var jokeAnswer by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Add a Joke",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onTertiary
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = jokeQuestion,
                // this lambda function is called when the user enters data.
                // the value of jokeQuestion is set to textFieldContents whenever the text field contents change.
                // The login form uses the same technique but omitted the "textFieldContents ->" part.
                // Both techniques are valid.
                onValueChange = { textFieldContents -> jokeQuestion = textFieldContents },
                label = { Text("Joke Question") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = jokeAnswer,
                onValueChange = { textFieldContents -> jokeAnswer = textFieldContents },
                label = { Text("Answer") }
            )
            Button(
                onClick = {
                    var newJoke = JokeModel()
                    if (jokes.size > 0) {
                        newJoke = JokeModel(jokes[jokes.size - 1].id + 1, jokeQuestion, jokeAnswer, false)
                    } else {
                        newJoke = JokeModel(0, jokeQuestion, jokeAnswer, false)
                    }
                    viewModel.addJoke(newJoke)
                    navController.navigate("show-jokes")

                }
            ) {
                Text("Add")
            }
        }
    }
}