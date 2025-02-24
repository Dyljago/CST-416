package com.dylanjohnson.project5.screens

import android.util.Log
import androidx.compose.foundation.clickable
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
import com.dylanjohnson.project5.Joke1
import com.dylanjohnson.project5.models.JokeModel

public var stringToSearch = ""

@Composable
public fun ShowJokesScreen(jokes: MutableList<JokeModel>) {
    var jokeCount by remember { mutableStateOf(0) }

    val filteredJokes = remember(jokes, stringToSearch) {
        jokes.filter { it.question.contains(stringToSearch) }
    }
    jokeCount = filteredJokes.size
    LazyColumn() {
        items(jokes.size) {
            index ->
            if (jokes[index].question.contains(stringToSearch)) {
                Joke1(
                    joke = jokes[index], {
                        Log.d("Joke tag", "You clicked joke number: $it")

                        // toggle the visibility property of the joke that was clicked
                        // notice the ! (negation) character
                        var changedJoke = jokes[it].copy(answerIsVisible = !jokes[it].answerIsVisible)

                        // replace the joke item at position number "it"
                        jokes[it] = changedJoke
                    }
                )
            }
        }
    }

    if (jokeCount < 1) {
        Text(
            modifier = Modifier.padding(10.dp),
            // fontSize = 36.sp,    // Set font size
            fontSize = 8.em,    // Relative font size
            lineHeight = 1.em,
            textAlign = TextAlign.Center,
            text = "No Found Jokes for term: \"$stringToSearch\""
        )
        Log.d("JOKE COUNT", "JOKES: $jokeCount")
    }
}