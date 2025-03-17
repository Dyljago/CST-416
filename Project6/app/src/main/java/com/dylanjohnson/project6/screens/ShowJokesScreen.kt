package com.dylanjohnson.project6.screens

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.dylanjohnson.project6.Joke1
import com.dylanjohnson.project6.data.JokesViewModelInterface

public var stringToSearch = ""

@Composable
public fun ShowJokesScreen(viewModel: JokesViewModelInterface) {
    // get the jokes from the viewmodel property
    var jokes = viewModel.jokesList

    // if the search result is empty, show all jokes
    if (viewModel.jokeSearchResult.size == 0) {
        jokes = viewModel.jokesList
    } else {
        // otherwise, show the search result
        jokes = viewModel.jokeSearchResult
    }

    var jokeCount by remember { mutableStateOf(0) }

    val filteredJokes = remember(jokes, stringToSearch) {
        jokes.filter {
            it.question.contains(stringToSearch, ignoreCase = true) ||
            it.answer.contains(stringToSearch, ignoreCase = true)
        }
    }
    jokeCount = filteredJokes.size
    LazyColumn() {
        items(jokes.size) {
            index -> Joke1(joke = jokes[index]) {
                Log.d("ShowJokesScreen", "Joke Clicked: ${jokes[index]}")
                Log.d("ShowJokesScreen", "Index Number Clicked: $index")
                viewModel.hideShowJoke(jokes[index])
            }
        }
    }

    if (jokeCount < 1) {
        if (stringToSearch != "") {
            Text(
                modifier = Modifier.padding(10.dp),
                // fontSize = 36.sp,    // Set font size
                fontSize = 8.em,    // Relative font size
                lineHeight = 1.em,
                textAlign = TextAlign.Center,
                text = "No Found Jokes for term: \"$stringToSearch\""
            )
        } else {
            Text(
                modifier = Modifier.padding(10.dp),
                // fontSize = 36.sp,    // Set font size
                fontSize = 8.em,    // Relative font size
                lineHeight = 1.em,
                textAlign = TextAlign.Center,
                text = "No Jokes In System"
            )
        }
        Log.d("JOKE COUNT", "JOKES: $jokeCount")
    }
}