package com.dylanjohnson.contactsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.dylanjohnson.contactsapp.data.ContactViewModelInterface
import com.dylanjohnson.contactsapp.models.BusinessModel
import com.dylanjohnson.contactsapp.models.PersonModel
import com.dylanjohnson.contactsapp.models.ScreenInfo
import com.dylanjohnson.contactsapp.screens.ContactListScreen
import com.dylanjohnson.contactsapp.screens.DetailRemoveScreen
import com.dylanjohnson.contactsapp.ui.theme.ContactsAppTheme
import com.dylanjohnson.contactsapp.ui.theme.blue_accent
import com.dylanjohnson.contactsapp.ui.theme.blue_icons
import com.dylanjohnson.contactsapp.ui.theme.blue_selected
import com.dylanjohnson.contactsapp.ui.theme.dark_text
import com.dylanjohnson.contactsapp.ui.theme.orange_accent
import com.dylanjohnson.contactsapp.ui.theme.orange_icons
import com.dylanjohnson.contactsapp.ui.theme.orange_selected
import com.dylanjohnson.contactsapp.screens.DetailScreen
import com.dylanjohnson.contactsapp.screens.EditBusinessScreen
import com.dylanjohnson.contactsapp.screens.EditPersonScreen
import com.dylanjohnson.contactsapp.screens.MainScreen
import com.dylanjohnson.contactsapp.ui.theme.black_accent
import com.dylanjohnson.contactsapp.ui.theme.black_icons
import com.dylanjohnson.contactsapp.ui.theme.black_selected
import com.dylanjohnson.contactsapp.ui.theme.dark_text_color
import com.dylanjohnson.contactsapp.ui.theme.green_accent
import com.dylanjohnson.contactsapp.ui.theme.green_icons
import com.dylanjohnson.contactsapp.ui.theme.green_selected
import com.dylanjohnson.contactsapp.ui.theme.light_text
import com.dylanjohnson.contactsapp.ui.theme.light_text_color
import com.dylanjohnson.contactsapp.ui.theme.pink_accent
import com.dylanjohnson.contactsapp.ui.theme.pink_icons
import com.dylanjohnson.contactsapp.ui.theme.pink_selected
import com.dylanjohnson.contactsapp.ui.theme.purple_accent
import com.dylanjohnson.contactsapp.ui.theme.purple_icons
import com.dylanjohnson.contactsapp.ui.theme.purple_selected
import com.dylanjohnson.contactsapp.ui.theme.red_accent
import com.dylanjohnson.contactsapp.ui.theme.red_icons
import com.dylanjohnson.contactsapp.ui.theme.red_selected
import com.dylanjohnson.contactsapp.ui.theme.white_accent
import com.dylanjohnson.contactsapp.ui.theme.white_icons
import com.dylanjohnson.contactsapp.ui.theme.white_selected
import com.dylanjohnson.contactsapp.ui.theme.yellow_accent
import com.dylanjohnson.contactsapp.ui.theme.yellow_icons
import com.dylanjohnson.contactsapp.ui.theme.yellow_selected

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
            windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())

            // Prevents the navigation bar from reappearing when swiping
            windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

            ContactsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
//                    Log.d("IMAGE STUPP", "CONTACT RESET2")
                    MainScreen()
//                    PopupButtonExample(context = this)
                }
            }
        }
    }
}

// this could be in a separate file but for the sake of simplicity, it is in this file
@Composable
fun NavigationGraph(navController: NavHostController, screens: List<ScreenInfo>, backgroundImage: Int, viewModel: ContactViewModelInterface) {
    // A navigation graph is a collection of composable destinations.
    // NavHost is a composable that manages the navigation within the app.
    NavHost(
        navController = navController,
        startDestination = screens[0].routeName
    ) {
        screens.forEach { screen ->
            if (screen.routeName != "details/{contactId}" && screen.routeName != "edit/person/{contactId}") {
                composable(route = screen.routeName) { screen.composable() }
            }
        }
        composable(
            route = "details/{contactId}",
            arguments = listOf(navArgument("contactId") { type = NavType.IntType })
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getInt("contactId")
            DetailScreen(contactId, navController, viewModel, { id, uri->
                viewModel.updateContactPicture(id, uri)
            })
        }
        composable(
            route = "detailsRemove/{contactId}",
            arguments = listOf(navArgument("contactId") { type = NavType.IntType })
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getInt("contactId")
            if (contactId != null && contactId in viewModel.contactsList.indices) {
                DetailRemoveScreen(contactId, navController, viewModel)
            } else {
                ContactListScreen(viewModel, navController)
            }
        }
        composable(
            route = "edit/person/{contactId}",
            arguments = listOf(navArgument("contactId") { type = NavType.IntType })
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getInt("contactId")
            if (contactId != null) {
                EditPersonScreen(contact = viewModel.contactsList[contactId] as PersonModel, navController = navController, viewModel = viewModel)
            } else {
                // Handle the case where contactId is null, maybe navigate back or show an error
                Text("Error: Contact ID not found")
            }
        }
        composable(
            route = "edit/business/{contactId}",
            arguments = listOf(navArgument("contactId") { type = NavType.IntType })
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getInt("contactId")
            if (contactId != null) {
                EditBusinessScreen(contact = viewModel.contactsList[contactId] as BusinessModel, navController = navController, viewModel = viewModel)
            } else {
                // Handle the case where contactId is null, maybe navigate back or show an error
                Text("Error: Contact ID not found")
            }
        }
    }
}

// this could be in a separate file but for the sake of simplicity, it is in this file
@Composable
fun BottomBar(navController: NavHostController, screens: List<ScreenInfo>, viewModel: ContactViewModelInterface) {

    // A variable to keep track of the current screen
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val themeColor = viewModel.chosenTheme.value.getTheme()
    val themeTextColor = if (themeColor == "blue" || themeColor == "pink" || themeColor == "red" || themeColor == "yellow" || themeColor == "purple") {
        dark_text_color
    } else if (themeColor == "black") {
        light_text
    } else {
        light_text_color
    }
    // NavigationBar is a composable that displays a bottom navigation bar.
    NavigationBar(
        containerColor = if (themeColor == "blue") {
            blue_accent
        } else if (themeColor == "green") {
            green_accent
        } else if (themeColor == "purple") {
            purple_accent
        } else if (themeColor == "yellow") {
            yellow_accent
        } else if (themeColor == "red") {
            red_accent
        } else if (themeColor == "pink") {
            pink_accent
        } else if (themeColor == "black") {
            black_accent
        } else if (themeColor == "white") {
            white_accent
        } else {
            orange_accent
        },
        contentColor = if (themeColor == "blue") {
            blue_selected
        } else if (themeColor == "green") {
            green_selected
        } else if (themeColor == "purple") {
            purple_selected
        } else if (themeColor == "yellow") {
            yellow_selected
        } else if (themeColor == "red") {
            red_selected
        } else if (themeColor == "pink") {
            pink_selected
        } else if (themeColor == "black") {
            black_selected
        } else if (themeColor == "white") {
            white_selected
        } else {
            orange_selected
        }
    ) {
        // A forEach loop to iterate through all the screens in the app
        screens.take(4).forEach { screen ->
            // Navigation Bar Item is a composable used for each item in the bottom navigation bar
            NavigationBarItem(
                label = {
                    Text(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        text = screen.title,
                        color = themeTextColor,
                    )
                },
                icon = {
                    Icon(
                        imageVector = screen.navIcon,
                        contentDescription = "Navigation Icon",
                        tint = if (themeColor == "blue") {
                            blue_icons
                        } else if (themeColor == "green") {
                            green_icons
                        } else if (themeColor == "purple") {
                            purple_icons
                        } else if (themeColor == "yellow") {
                            yellow_icons
                        } else if (themeColor == "red") {
                            red_icons
                        } else if (themeColor == "pink") {
                            pink_icons
                        } else if (themeColor == "black") {
                            black_icons
                        } else if (themeColor == "white") {
                            white_icons
                        } else {
                            orange_icons
                        },
                        modifier = Modifier.size(40.dp)
                    )
                },
                selected = currentDestination?.hierarchy?.any {
                    it.route == screen.routeName
                } == true, // If any destination in the navigation hierarchy is this destination, return true

                onClick = {
                    viewModel.closeAllContacts()
                    // Navigate to the selected screen
                    navController.navigate(screen.routeName) {
                        // Pop up to the start destination of the graph to
                        popUpTo(navController.graph.findStartDestination().id)
                        // Avoid multiple copies of the same destination when re-selecting the same item
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = if (themeColor == "blue") {
                        blue_selected
                    } else if (themeColor == "green") {
                        green_selected
                    } else if (themeColor == "purple") {
                        purple_selected
                    } else if (themeColor == "yellow") {
                        yellow_selected
                    } else if (themeColor == "red") {
                        red_selected
                    } else if (themeColor == "pink") {
                        pink_selected
                    } else if (themeColor == "black") {
                        black_selected
                    } else if (themeColor == "white") {
                        white_selected
                    } else {
                        orange_selected
                    }
                )
            )
        }
    }
}
