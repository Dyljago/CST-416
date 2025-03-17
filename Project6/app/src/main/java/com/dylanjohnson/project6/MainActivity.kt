package com.dylanjohnson.project6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.em
import com.dylanjohnson.project6.data.JokesViewModelInterface
import com.dylanjohnson.project6.data.ViewModelInMemory
import com.dylanjohnson.project6.data.ViewModelJSON
import com.dylanjohnson.project6.models.JokeModel
import com.dylanjohnson.project6.models.ScreenInfo
import com.dylanjohnson.project6.screens.AddJokeScreen
import com.dylanjohnson.project6.screens.LoginScreen
import com.dylanjohnson.project6.screens.SearchScreen
import com.dylanjohnson.project6.screens.ShowJokesScreen
import com.dylanjohnson.project6.screens.stringToSearch
import com.dylanjohnson.project6.ui.theme.Project6Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Project6Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    // set the data source for the list of jokes
//    val viewModel: JokesViewModelInterface = remember { ViewModelInMemory() }
    val context = LocalContext.current
    val viewModel: JokesViewModelInterface = remember { ViewModelJSON(context) }
    viewModel.getAllJokes()

    val navController = rememberNavController()

    /*
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
    */

    // A list of all the screens in the app
    val screens = listOf(
        ScreenInfo(
//            routeName = "home",
//        title = "home",
//        navIcon = Icons.Default.Home,
//        composable =  { HomeScreen() }
            routeName = "login",
            title = "Login",
            navIcon = Icons.Default.AccountBox,
            composable = { LoginScreen( navController ) }
        ),  ScreenInfo(
            routeName = "add-joke",
            title = "Add Joke",
            navIcon = Icons.Default.Add,
            composable =  { AddJokeScreen( navController, viewModel ) }
//            routeName = "add-item",
//        title = "Add Item",
//        composable =  { AddItemScreen() }
        ), ScreenInfo(
//            routeName = "notifications",
//        title = "Notifications",
//        navIcon = Icons.Default.Notifications,
//        composable =  { NotificationsScreen() }
            routeName = "show-jokes",
            title = "Show All Jokes",
            navIcon = Icons.Default.List,
            composable =  {
                ShowJokesScreen(viewModel)
            }
        )
        , ScreenInfo(
//            routeName = "settings",
//        title = "Settings",
//        navIcon = Icons.Default.Settings,
//        composable =  { SettingsScreen() }
            routeName = "search",
            title = "Search",
            navIcon = Icons.Default.Search,
            composable =  { SearchScreen( navController, viewModel ) }
        )
    )

    Scaffold(
        bottomBar = { BottomBar(navController = navController, screens) }
//        modifier = Modifier.padding(bottom = 10.dp)
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)    ) {
            NavigationGraph(navController = navController, screens)
        }
    }
}

// this could be in a separate file but for the sake of simplicity, it is in this file
@Composable
fun NavigationGraph(navController: NavHostController, screens: List<ScreenInfo>) {
    // A navigation graph is a collection of composable destinations.
    // NavHost is a composable that manages the navigation within the app.
    NavHost(
        navController = navController,
        startDestination = screens[0].routeName
    ) {
        screens.forEach() { screen ->
            composable(route = screen.routeName) { screen.composable() }
        }
    }
}

// this could be in a separate file but for the sake of simplicity, it is in this file
@Composable
fun BottomBar(navController: NavHostController, screens: List<ScreenInfo>) {

    // A variable to keep track of the current screen
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // NavigationBar is a composable that displays a bottom navigation bar.
    NavigationBar() {
        // A forEach loop to iterate through all the screens in the app
        screens.forEach { screen ->
            // Navigation Bar Item is a composable used for each item in the bottom navigation bar
            NavigationBarItem(
                label = {
                    Text(text = screen.title)
                },
                icon = {
                    Icon(
                        imageVector = screen.navIcon,
                        contentDescription = "Navigation Icon"
                    )
                },
                selected = currentDestination?.hierarchy?.any {
                    it.route == screen.routeName
                } == true, // If any destination in the navigation hierarchy is this destination, return true

                onClick = {
                    stringToSearch = ""
                    // Navigate to the selected screen
                    navController.navigate(screen.routeName) {
                        // Pop up to the start destination of the graph to
                        popUpTo(navController.graph.findStartDestination().id)
                        // Avoid multiple copies of the same destination when re-selecting the same item
                        launchSingleTop = true
                    }
                }
            )
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
    val textColor = if (isDarkMode) Color(0xFFD599FF) else Color(0xFF00089E)
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

// Four simple composable functions that represent the screens in the app
// These could be in separate files but for the sake of simplicity, they are all in this file
// each screen is customizable. change to input forms, lists, etc.
@Composable
fun HomeScreen() {
    // simple composable that displays a text in the center of the screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Home Page" ,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.headlineLarge.fontSize
        )
    }
}

@Composable
fun AddItemScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Add Item",
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.headlineLarge.fontSize
        )
    }
}

@Composable
fun NotificationsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.inverseSurface),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Notifications" ,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.headlineLarge.fontSize
        )
    }
}

@Composable
fun SettingsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.inversePrimary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Settings" ,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.headlineLarge.fontSize
        )
    }
}
