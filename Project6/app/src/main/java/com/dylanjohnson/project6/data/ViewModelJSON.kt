package com.dylanjohnson.project6.data

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.io.File
import com.dylanjohnson.project6.models.JokeModel
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelJSON(context: Context) : JokesViewModelInterface, ViewModel() {
    val appContext = context
    override var jokesList: MutableList<JokeModel> = mutableStateListOf<JokeModel>()
    override var jokeSearchResult: MutableList<JokeModel> = mutableStateListOf<JokeModel>()

    fun loadJokesFromJSON() {
        Log.d("ViewModelJSON", "Loading Jokes from JSON")
        val file = File(appContext.filesDir, "jokes.json")

        val mapper = ObjectMapper()
        if (file.exists()) {
            // read the entire list of jokes from the json file.
            val newJokes = mapper.readValue(file, Array<JokeModel>::class.java).toMutableList()
            // remove all jokes from the list and add the new jokes
            jokesList.clear()
            jokesList.addAll(newJokes)
            /*
                Why not do it this way?
                jokesList = mapper.readValue(file, Array<JokeModel>::class.java).toMutableList()
                According to the way mutableStateListOf works, we cannot simply assign a new list to jokesList
                The UI will not be updated. We must replace the items in the list. This triggers the recomposition of the list
            */
            Log.d("ViewModelJSON", "Jokes loaded from JSON: ${jokesList.size}")
        }
    }

    fun saveJokesToJSON() {
        // perform the file IO on the IO thread using coroutine to prevent blocking the main thread
        // blocking the main thread can cause the app to freeze and become unresponsive during file IO
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("ViewModelJSON", "Saving jokes to JSON")
            val file = File(appContext.filesDir, "jokes.json")
            val mapper = ObjectMapper()
            mapper.writeValue(file, jokesList)
        }
        Log.d("ViewModelJSON", "Jokes saved to JSON: ${jokesList.size}")
    }

    override fun findJokesByKeyword(findPhrase: String) {
        // clear previous search results
        jokeSearchResult.clear()

        // search the jokesList for the findPhrase
        jokesList.forEach {
            if (it.question.contains(findPhrase) || it.answer.contains(findPhrase)) {
                jokeSearchResult.add(it)
            }
        }
    }

    override fun getAllJokes() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("ViewModelJSON", "Loading Jokes from JSON")
            val file = File(appContext.filesDir, "jokes.json")

            val mapper = ObjectMapper()
            if (file.exists()) {
                val newJokes = mapper.readValue(file, Array<JokeModel>::class.java).toMutableList()
                // Update UI-related data on the main thread
                launch(Dispatchers.Main) {
                    jokesList.clear()
                    jokesList.addAll(newJokes)
                    Log.d("ViewModelJSON", "Jokes loaded from JSON: ${jokesList.size}")
                }
            }
        }
    }

    override fun addJoke(joke: JokeModel) {
        // add the joke to the list and save the list to the JSON file
        jokesList.add(joke)
        saveJokesToJSON()
    }

    override fun removeJoke(joke: JokeModel) {
        // remove the joke from the list and save the list to the JSON file
        jokesList.remove(joke)
        saveJokesToJSON()
    }

    override fun hideShowJoke(joke: JokeModel) {
        // determine which list to use
        var jokesList = if (jokeSearchResult.isEmpty()) jokesList else jokeSearchResult
        // set all visible to false
        jokesList.forEach{ it.answerIsVisible = false }
        // set the joke passed in to true
        jokesList[jokesList.indexOf(joke)] = joke.copy(answerIsVisible = true)
    }

}