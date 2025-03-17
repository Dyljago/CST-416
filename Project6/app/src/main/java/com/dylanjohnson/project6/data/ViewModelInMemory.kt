package com.dylanjohnson.project6.data

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.dylanjohnson.project6.models.JokeModel

// this version of the view model stores the jokes in an array list in memory
// this is not persistent storage, so the jokes will be lost when the app is closed
// this version is simpler than the database version which we will implement in the next lesson

// this class implements the JokesViewModelInterface and extends the ViewModel class
class ViewModelInMemory : JokesViewModelInterface, ViewModel() {
    override var jokesList: MutableList<JokeModel> = mutableStateListOf<JokeModel>()
    override var jokeSearchResult: MutableList<JokeModel> = mutableStateListOf<JokeModel>()

    init {
        // start with some jokes in the list
        // check if the list is empty first to avoid adding duplicates if the init block is called again
        if (jokesList.size == 0) {
            jokesList.addAll(
                mutableStateListOf(
                    JokeModel(
                        0,
                        "What time is it when an elephant sits on your fence?",
                        "Time to get a new fence.",
                        true
                    ),
                    JokeModel(
                        1,
                        "What is red and smells like blue paint?",
                        "Red paint.",
                        false
                    ),
                    JokeModel(
                        2,
                        "Why did the chicken cross the playground?",
                        "To get to the other slide.",
                        true
                    ),
                    JokeModel(
                        3,
                        "What did the mother buffalo say to her child when leaving him at school?",
                        "Bison.",
                        false
                    )
                )
            )
        }
    }

    override fun getAllJokes() {
        // do nothing.
        // this is already done in the init block
        // the next version of the viewmodel in the next lesson will get the jokes from a database
    }

    override fun addJoke(joke: JokeModel) {
        // this version of the viewmodel stores the jokes in an array list in memory
        // the next version of the viewmodel in the next lesson will store the jokes in a database
        jokesList.add(joke)
    }

    override fun removeJoke(joke: JokeModel) {
        // this version of the viewmodel stores the jokes in an array list in memory
        // the next version of the viewmodel in the next lesson will store the jokes in a database
        jokesList.remove(joke)
    }

    override fun findJokesByKeyword(findPhrase: String) {
        // clear previous search results
        jokeSearchResult.clear()

        // loop through all jokes in the list and add any that contain the search phrase
        // to the search results list

        for (joke in jokesList) {
            // check if the search phrase is in either the question or the answer
            if (joke.question.contains(findPhrase, ignoreCase = true) || joke.answer.contains(findPhrase, ignoreCase = true)) {
                jokeSearchResult.add(joke)
            }
        }
    }

    override fun hideShowJoke(joke: JokeModel) {
        // if there are no search results, then we are not in search mode
        if (jokeSearchResult.size == 0) {
            // set all jokes to answerVisible = false
            jokesList.forEach(
                { it.answerIsVisible = false }
            )

            // find the position of the joke in the list
            var position = jokesList.indexOf(joke)

            // replace the joke in the list with a copy of the joke with answerVisible = true
            // we must use replace in order to trigger the recomposition of the list.
            // setting the visibility of the answer to true without replacing an item in the list
            // will not trigger the recomposition of the list
            if (position >= 0) {
                jokesList[position] = jokesList[position].copy(answerIsVisible = true)
            }
        } else {
            // search results are not empty. We are in search mode
            jokeSearchResult.forEach(
                { it.answerIsVisible = false }
            )

            // set the joke passed in to answerVisible = true
            // find the position of the joke in the list
            var position = jokeSearchResult.indexOf(joke)

            // replace the joke in the list with a copy of the joke with answerVisible = true
            // We must use replace in order to trigger the recomposition of the list.
            // setting the visibility of the answer to true without replacing an item in the list
            // will not trigger the recomposition of the list
            if (position >= 0) {
                jokeSearchResult[position] = jokeSearchResult[position].copy(answerIsVisible = true)
            }
        }
    }
}