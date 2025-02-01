package com.dylanjohnson.project3

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.dylanjohnson.project3.ui.theme.CustomTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomTheme {
                MyApp()
            }
        }
    }
}

@Composable
private fun MyApp() {
    var jokes = remember {
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
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn() {
//            items(jokes.size) {
//                index -> Joke1(joke = jokes[index], {
//                    Log.d("Joke tag", "You clicked joke number: $it")
//
//                    // toggle the visibility property of the joke that was clicked
//                    // notice the ! (negation) character
//                    var changedJoke = jokes[it].copy(answerIsVisible = !jokes[it].answerIsVisible)
//
//                    // replace the joke item at position number "it"
//                    jokes[it] = changedJoke
//                })
//            }
            items(jokes.size) { index -> Joke2(joke = jokes[index], context = LocalContext.current) }
        }
    }
}

@Composable
fun Joke1(joke: JokeModel, changeVisibility: (id:Int) -> Unit) {
    Text(modifier = Modifier.padding(10.dp),
        fontSize = 5.em,
        text = "Joke Number: ${joke.id}"
    )
    val isDarkMode = isSystemInDarkTheme() // Check if the system is in dark mode
    val textColor = if (isDarkMode) Color(0xFFD599FF) else Color(0xFFA14D02)
    Text(
        modifier = Modifier
            .padding(2.dp)
            .clickable {
                changeVisibility(joke.id)
                Log.d("Joke Tag", "Joke1: $joke")
            },

        color = textColor,
        // fontSize = 36.sp,    // Set font size
        fontSize = 8.em,    // Relative font size
        lineHeight = 1.em,
        textAlign = TextAlign.Center,
        text = joke.question
    )

    if (joke.answerIsVisible) {
        Text(
            modifier = Modifier.padding(10.dp),
            fontSize = 5.em,
            fontWeight = FontWeight.Bold,
            text = joke.answer
        )
    }
    Divider()
}

@Composable
fun Joke2(joke: JokeModel, context: Context) {
    Text( modifier = Modifier
        .padding(15.dp)
        .clickable {
            Log.d("Jokes tag", "You clicked $joke")
            Toast
                .makeText(context, joke.answer, Toast.LENGTH_LONG)
                .show()
        },
        fontSize = 6.em,
        text = joke.question)

    Divider()
}

@Preview(showBackground = true)
@Composable
fun JokePreview() {
    CustomTheme() {
        MyApp()
    }
}

data class JokeModel(
    val id: Int,
    var question: String,
    var answer : String,
    var answerIsVisible: Boolean
)