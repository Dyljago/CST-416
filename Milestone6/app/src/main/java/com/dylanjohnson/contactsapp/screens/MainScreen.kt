package com.dylanjohnson.contactsapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.GroupOff
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.dylanjohnson.contactsapp.BottomBar
import com.dylanjohnson.contactsapp.NavigationGraph
import com.dylanjohnson.contactsapp.R
import com.dylanjohnson.contactsapp.classes.ThemeUtils
import com.dylanjohnson.contactsapp.data.ContactViewModel
import com.dylanjohnson.contactsapp.data.ContactViewModelInterface
import com.dylanjohnson.contactsapp.models.ScreenInfo
import com.dylanjohnson.contactsapp.screen.FavoriteScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MainScreen() {
    // Track the system theme
    ThemeUtils.TrackSystemTheme()

    val navController = rememberNavController()
    val context = LocalContext.current
    val viewModel: ContactViewModelInterface = remember { ContactViewModel(context) }

    val themeColor = viewModel.chosenTheme.value.getTheme()

    // Set system UI colors
    val systemUiController = rememberSystemUiController()
    // Set background image resource
    val backgroundImage = if (themeColor == "blue") {
        R.drawable.background_image_blue
    } else if (themeColor == "green") {
        R.drawable.background_image_green
    } else if (themeColor == "purple") {
        R.drawable.background_image_purple
    } else if (themeColor == "yellow") {
        R.drawable.background_image_yellow
    } else if (themeColor == "red") {
        R.drawable.background_image_red
    } else if (themeColor == "pink") {
        R.drawable.background_image_pink
    } else if (themeColor == "black") {
        R.drawable.background_image_black
    } else if (themeColor == "white") {
        R.drawable.background_image_white
    } else {
        R.drawable.background_image_orange
    }
    // Set system UI colors
    val statusBarColor = if (themeColor == "blue") {
        ContextCompat.getColor(context, R.color.blue_status)
    } else if (themeColor == "green") {
        ContextCompat.getColor(context, R.color.green_status)
    }  else if (themeColor == "purple") {
        ContextCompat.getColor(context, R.color.purple_status)
    } else if (themeColor == "yellow") {
        ContextCompat.getColor(context, R.color.yellow_status)
    } else if (themeColor == "red") {
        ContextCompat.getColor(context, R.color.red_status)
    } else if (themeColor == "pink") {
        ContextCompat.getColor(context, R.color.pink_status)
    } else if (themeColor == "black") {
        ContextCompat.getColor(context, R.color.black_status)
    } else if (themeColor == "white") {
        ContextCompat.getColor(context, R.color.white_status)
    } else {
        ContextCompat.getColor(context, R.color.orange_status)
    }

    // Apply the system UI colors
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color(statusBarColor),
            darkIcons = true // This replaces SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        )
    }

    // A list of all the screens in the app
    val screens = listOf(
        ScreenInfo(
            routeName = "contacts",
            title = "Contacts",
            navIcon = Icons.Filled.Person,
            composable = { ContactListScreen(viewModel, navController) }
        ),  ScreenInfo(
            routeName = "favorites",
            title = "Favorites",
            navIcon = Icons.Filled.Favorite,
            composable = { FavoriteScreen(viewModel, navController) }
        ), ScreenInfo(
            routeName = "groups",
            title = "Groups",
            navIcon = Icons.Filled.Group,
            composable = { GroupsScreen(viewModel, navController) }
        )
        , ScreenInfo(
            routeName = "settings",
            title = "Settings",
            navIcon = Icons.Filled.Settings,
            composable = { SettingsScreen(viewModel, navController) }
        )
        , ScreenInfo(
            routeName = "addPerson",
            title = "Add Person Contact",
            navIcon = Icons.Filled.Person,
            composable = { AddPersonContactScreen(viewModel, navController) }
        )
        , ScreenInfo(
            routeName = "addBusiness",
            title = "Add Business Contact",
            navIcon = Icons.Filled.Business,
            composable = { AddBusinessContactScreen(viewModel, navController) }
        )
        , ScreenInfo(
            routeName = "removeContact",
            title = "Remove Contact",
            navIcon = Icons.Filled.GroupOff,
            composable = { RemoveContactScreen(viewModel, navController) }
        )
        , ScreenInfo(
            routeName = "details/{contactId}",
            title = "Details",
            navIcon = Icons.Filled.Info,
            composable = {  }
        )
        , ScreenInfo(
            routeName = "detailsRemove/{contactId}",
            title = "Details Remove",
            navIcon = Icons.Filled.Info,
            composable = {  }
        )
        , ScreenInfo(
            routeName = "edit/person/{contactId}",
            title = "Edit",
            navIcon = Icons.Filled.Info,
            composable = {  }
        )
        , ScreenInfo(
            routeName = "edit/business/{contactId}",
            title = "Edit",
            navIcon = Icons.Filled.Info,
            composable = {  }
        )
    )

    Scaffold(
        bottomBar = { BottomBar(navController = navController, screens, viewModel) }
//        modifier = Modifier.padding(bottom = 10.dp)
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)    ) {
            NavigationGraph(navController = navController, screens, backgroundImage, viewModel)
        }
    }
}