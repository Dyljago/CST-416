package com.dylanjohnson.project6.data

import com.dylanjohnson.project6.models.JokeModel

interface JokesViewModelInterface {
    var jokesList : MutableList<JokeModel>
    var jokeSearchResult : MutableList<JokeModel>

    fun getAllJokes()

    fun addJoke(joke: JokeModel)

    fun removeJoke(joke: JokeModel)

    fun findJokesByKeyword(findPhrase: String) {}

    fun hideShowJoke(joke: JokeModel)

}