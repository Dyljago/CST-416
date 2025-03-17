package com.dylanjohnson.project6.models

data class JokeModel(
    val id: Int = 0,
    var question: String = "",
    var answer : String = "",
    var answerIsVisible: Boolean = false
)