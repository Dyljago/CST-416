package com.dylanjohnson.contactsapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.AddRoad
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditRoad
import androidx.compose.material.icons.outlined.EmojiObjects
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardBackspace
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.filled.Today
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.dylanjohnson.contactsapp.ui.theme.ContactsAppTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
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
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dylanjohnson.contactsapp.classes.ThemeUtils
import com.dylanjohnson.contactsapp.data.ContactViewModel
import com.dylanjohnson.contactsapp.data.ContactViewModelInterface
import com.dylanjohnson.contactsapp.models.BusinessModel
import com.dylanjohnson.contactsapp.models.ContactModel
import com.dylanjohnson.contactsapp.models.PersonModel
import com.dylanjohnson.contactsapp.models.ScreenInfo
import com.dylanjohnson.contactsapp.ui.theme.blue_accent
import com.dylanjohnson.contactsapp.ui.theme.blue_icons
import com.dylanjohnson.contactsapp.ui.theme.blue_selected
import com.dylanjohnson.contactsapp.ui.theme.dark_text
import com.dylanjohnson.contactsapp.ui.theme.dark_text_color
import com.dylanjohnson.contactsapp.ui.theme.orange_accent
import com.dylanjohnson.contactsapp.ui.theme.orange_icons
import com.dylanjohnson.contactsapp.ui.theme.orange_selected
import com.dylanjohnson.contactsapp.ui.theme.invalid_button
import com.dylanjohnson.contactsapp.ui.theme.light_text_color
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.tooling.preview.Preview
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import android.app.Activity
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import coil.compose.rememberAsyncImagePainter
import android.content.ContentValues
import androidx.compose.ui.draw.clip
import java.net.URLEncoder
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.time.LocalDate

// Use UiModeManager to check if the system is in dark mode
var isDarkMode = false

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
                    Log.d("IMAGE STUPP", "CONTACT RESET2")
                    MainScreen()
//                    PopupButtonExample(context = this)
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    // Track the system theme
    ThemeUtils.TrackSystemTheme()
    isDarkMode = ThemeUtils.isDarkMode.value

    val navController = rememberNavController()
    val context = LocalContext.current
    val viewModel: ContactViewModelInterface = remember { ContactViewModel(context) }

    Log.d("IMAGE STUDD", "CONTACT RESET")
//    var contacts = remember {
//        mutableStateListOf(
//            PersonModel(
//                id = 0,
//                name = "Person Example",
//                email = "test@test.com",
//                phoneNumber = "555-555-5555",
//                address = "11111 east fake street, Zoo, Narnia 99999",
//                isExpanded = false,
//                pictureURL = null,
//                isFamilyMember = true,
//                birthDate = LocalDate.now(),
//                group = "",
//                isEmergency = false,
//                isFavorite = true
//            ),
//            BusinessModel(
//                id = 1,
//                name = "Business Example",
//                email = "business@test.com",
//                phoneNumber = "555-678-9101",
//                address = "99999 west business street, Busy, Ness 00000",
//                isExpanded = false,
//                webURL = "https://businessEmail.com",
//                myOpinionRating = 3.5f,
//                daysOpen = booleanArrayOf(true, true, true, true, true, false, false)
//            ),
//            PersonModel(
//                id = 2,
//                name = "Test Person",
//                email = "test@test.com",
//                phoneNumber = "555-555-5555",
//                address = "11111 west fake street, Zoo, Narnia 99999",
//                isExpanded = false,
//                pictureURL = null,
//                isFamilyMember = true,
//                birthDate = LocalDate.now(),
//                group = "",
//                isEmergency = false,
//                isFavorite = false
//            ),
//            ContactModel(
//                3,
//                "Test2",
//                "test2@test.com",
//                "555-556-5555",
//                "11111 east fake street, Aquarium, Narnia 99998",
//                false
//            ),
//            ContactModel(
//                4,
//                "Test3",
//                "test3@test.com",
//                "555-566-5555",
//                "11111 south fake street, Movie, Narnia 99899",
//                false
//            )
//        )
//    }
//    contacts.forEach { contact ->
//        Log.d("CONTACT LIST", "Contact type in list: ${contact::class.java.simpleName}")
//    }
//    viewModel.contactsList = contacts

    // Set system UI colors
    val systemUiController = rememberSystemUiController()
    val statusBarColor = if (isDarkMode) {
        ContextCompat.getColor(context, R.color.blue_status)
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

    // Set background image resource
    val backgroundImage = if (isDarkMode) {
        R.drawable.background_image_blue
    } else {
        R.drawable.background_image_orange
    }

    // A list of all the screens in the app
    val screens = listOf(
        ScreenInfo(
            routeName = "contacts",
            title = "Contacts",
            navIcon = Icons.Filled.Person,
            composable = { ContactList(viewModel, backgroundImage, navController) }
        ),  ScreenInfo(
            routeName = "favorites",
            title = "Favorites",
            navIcon = Icons.Filled.Favorite,
            composable = { HomeScreen(backgroundImage) }
        ), ScreenInfo(
            routeName = "groups",
            title = "Groups",
            navIcon = Icons.Filled.Group,
            composable = { HomeScreen(backgroundImage) }
        )
        , ScreenInfo(
            routeName = "settings",
            title = "Settings",
            navIcon = Icons.Filled.Settings,
            composable = { SettingsScreen(backgroundImage, viewModel, navController) }
        )
        , ScreenInfo(
            routeName = "addPerson",
            title = "Add Person Contact",
            navIcon = Icons.Filled.Person,
            composable = { AddPersonContact(backgroundImage, viewModel, navController) }
        )
        , ScreenInfo(
            routeName = "addBusiness",
            title = "Add Business Contact",
            navIcon = Icons.Filled.Business,
            composable = { AddBusinessContact(backgroundImage, viewModel, navController) }
        )
        , ScreenInfo(
            routeName = "details/{contactId}",
            title = "Details",
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

@Composable
fun Contact(contact: ContactModel, changeVisibility: (id:Int) -> Unit, context: Context, navController: NavHostController) {
    val isDarkMode = isSystemInDarkTheme() // Check if the system is in dark mode
    val textColor = if (isDarkMode) Color(0xFF363636) else Color(0xFFF85F54)
    Text(
        modifier = Modifier
            .padding(2.dp)
            .clickable {
                changeVisibility(contact.getId())
                Log.d("Contact Tag", "Contact: $contact")
            },

        color = textColor,
        // fontSize = 36.sp,    // Set font size
        fontSize = 8.em,    // Relative font size
        lineHeight = 1.em,
        textAlign = TextAlign.Center,
        text = contact.getName()
    )

    if (contact.getExpanded()) {
        navController.navigate("details/${contact.getId()}")
    }
    Divider(color = Color.Black)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactList(viewModel: ContactViewModelInterface, backgroundImage: Int, navController: NavHostController) {
    var contacts = viewModel.contactsList
//    contacts.forEach { contact ->
//        Log.d("CONTACT LIST", "Contact type in list: ${contact::class.java.simpleName}")
//    }
    // one variable for each field in the form
    var searchString by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = backgroundImage),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        val filteredContacts = remember(contacts, searchString) {
            contacts.filter { it.getName().contains(searchString, ignoreCase = true) }
        }
        val contactCount = filteredContacts.size
        LazyColumn() {
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
            items(contacts.size) {
                    index ->
                if (contacts[index].getName().contains(searchString, ignoreCase = true)) {
                    Contact(
                        contact = contacts[index], {
                            if (contacts[it] is PersonModel) {
                                Log.d("IMAGE PERSON OLD", contacts[it].toString())
                            }
                            var changedContact = when (contacts[it]) {
                                is PersonModel -> (contacts[it] as PersonModel).copy(isExpanded = !contacts[it].getExpanded())
                                is BusinessModel -> (contacts[it] as BusinessModel).copy(isExpanded = !contacts[it].getExpanded())
                                else -> contacts[it].copy(isExpanded = !contacts[it].getExpanded()) // Handle ContactModel or other subclasses
                            }
                            if (changedContact is PersonModel) {
                                Log.d("IMAGE PERSON NEW", changedContact.toString())
                            }

                            // replace the joke item at position number "it"
                            contacts[it] = changedContact
                        },
                        context = LocalContext.current,
                        navController
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(70.dp))
            }
        }
        if (contactCount < 1) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(80.dp))
                Text(
                    modifier = Modifier.padding(10.dp),
                    fontSize = 8.em,
                    lineHeight = 1.em,
                    textAlign = TextAlign.Center,
                    text = "No Contacts matching: \"$searchString\""
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = searchString,
                onValueChange = { textFieldContents -> searchString = textFieldContents },
                placeholder = { Text("Search", color = if (isDarkMode) dark_text_color else light_text_color) },
                leadingIcon = {
                    Icon(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(30.dp),
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = if (isDarkMode) blue_icons else orange_icons
                    )
                },
                shape = RoundedCornerShape(32.dp),
                modifier = Modifier
                    .width(360.dp)
                    .padding(2.dp), // Adjust padding instead of height
                textStyle = TextStyle(fontSize = 16.sp, color = if (isDarkMode) dark_text_color else light_text_color),
                colors = TextFieldDefaults.textFieldColors(
                    focusedTextColor = if (isDarkMode) dark_text_color else light_text_color,
                    containerColor = if (isDarkMode) blue_accent else orange_accent,
                    focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true, // Prevents resizing when typing
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        println("Searching for: $searchString")
                    }
                )
            )
        }
    }
}

@Composable
fun SettingsScreen(backgroundImage: Int, viewModel: ContactViewModelInterface, navController: NavHostController) {
    // State to control the visibility of the dropdown menu
    val (expanded, setExpanded) = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = backgroundImage),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        val isDarkMode = isSystemInDarkTheme() // Check if the system is in dark mode
        val textColor = if (isDarkMode) Color(0xFF363636) else Color(0xFFF85F54)
        LazyColumn() {
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(2.dp)
                            .clickable {
                                Log.d("Contact Tag", "Add Contact")
                            },

                        color = textColor,
                        // fontSize = 36.sp,    // Set font size
                        fontSize = 8.em,    // Relative font size
                        lineHeight = 1.em,
                        textAlign = TextAlign.Center,
                        text = "Add Contact"
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Button(onClick = { setExpanded(true) }, shape = CircleShape) {
                            Text("+")
                        }
                        // DropdownMenu for options
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { setExpanded(false) }
                        ) {
                            // Option 1 inside DropdownMenuItem, text parameter passed correctly
                            DropdownMenuItem(
                                onClick = {
                                    navController.navigate("addPerson")
                                    setExpanded(false)
                                },
                                text = { Text("Add Person") } // Correct way to use the 'text' parameter
                            )

                            // Option 2 inside DropdownMenuItem, text parameter passed correctly
                            DropdownMenuItem(
                                onClick = {
                                    navController.navigate("addBusiness")
                                    setExpanded(false)
                                },
                                text = { Text("Add Business") } // Correct way to use the 'text' parameter
                            )
                        }
                    }
                }
                Divider(color = Color.Black)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .padding(2.dp)
                            .clickable {
                                Log.d("Contact Tag", "Add Contact")
                            },

                        color = textColor,
                        // fontSize = 36.sp,    // Set font size
                        fontSize = 8.em,    // Relative font size
                        lineHeight = 1.em,
                        textAlign = TextAlign.Center,
                        text = "Save Contacts"
                    )
                    Button(
                        onClick = {
                            Log.d("Save Contact", "Save Contacts")
                            viewModel.saveContacts()
                        },
                        shape = CircleShape,
                        modifier = Modifier.size(40.dp),
                        contentPadding = PaddingValues(2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Save,
                            contentDescription = "Save",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Divider(color = Color.Black)
            }
            item {
                Spacer(modifier = Modifier.height(70.dp))
            }
        }
    }
}

@Composable
fun PopupButtonExample(context: Context) {
    // State to control the visibility of the dropdown menu
    val (expanded, setExpanded) = remember { mutableStateOf(false) }
    val (selectedOption, setSelectedOption) = remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        // Button that opens the popup
        Button(onClick = { setExpanded(true) }) {
            Text("Show Options")
        }

        // DropdownMenu for options
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { setExpanded(false) }
        ) {
            // Option 1 inside DropdownMenuItem, text parameter passed correctly
            DropdownMenuItem(
                onClick = {
                    setSelectedOption("Option 1")
                    setExpanded(false)
                },
                text = { Text("Option 1") } // Correct way to use the 'text' parameter
            )

            // Option 2 inside DropdownMenuItem, text parameter passed correctly
            DropdownMenuItem(
                onClick = {
                    setSelectedOption("Option 2")
                    setExpanded(false)
                },
                text = { Text("Option 2") } // Correct way to use the 'text' parameter
            )
        }

        if (selectedOption != "") {
            Toast
                .makeText(context, selectedOption.toString(), Toast.LENGTH_LONG)
                .show()
            setSelectedOption("")
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
            if (screen.routeName != "details/{contactId}") {
                composable(route = screen.routeName) { screen.composable() }
            }
        }
        composable(
            route = "details/{contactId}",
            arguments = listOf(navArgument("contactId") { type = NavType.IntType })
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getInt("contactId")
            DetailScreen(backgroundImage, contactId, navController, viewModel, { id, uri->
//                var changedPerson = when (viewModel.contactsList[id]) {
//                    is PersonModel -> (viewModel.contactsList[id] as PersonModel).copyPersonPicture(pictureURL = uri)
//                    else -> null // do nothing
//                }
//                if (changedPerson != null) {
//                    viewModel.contactsList[id] = changedPerson
//                }

                viewModel.updateContactPicture(id, uri)
            })
        }
    }
}

// this could be in a separate file but for the sake of simplicity, it is in this file
@Composable
fun BottomBar(navController: NavHostController, screens: List<ScreenInfo>, viewModel: ContactViewModelInterface) {

    // A variable to keep track of the current screen
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // NavigationBar is a composable that displays a bottom navigation bar.
    NavigationBar(
        containerColor = if (isDarkMode) blue_accent else orange_accent,
        contentColor = if (isDarkMode) blue_selected else orange_selected
    ) {
        // A forEach loop to iterate through all the screens in the app
        screens.take(4).forEach { screen ->
            // Navigation Bar Item is a composable used for each item in the bottom navigation bar
            NavigationBarItem(
                label = {
                    Text(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        text = screen.title,
                        color = dark_text,
                    )
                },
                icon = {
                    Icon(
                        imageVector = screen.navIcon,
                        contentDescription = "Navigation Icon",
                        tint = if (isDarkMode) blue_icons else orange_icons,
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
                    indicatorColor = if (isDarkMode) blue_selected else orange_selected
                )
            )
        }
    }
}

@Composable
fun HomeScreen(backgroundImage: Int) {
    // simple composable that displays a text in the center of the screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = backgroundImage),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Text(
            text = "Home Page" ,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.headlineLarge.fontSize
        )
    }
}

@Composable
fun DetailScreen(backgroundImage: Int, contactId: Int?, navController: NavHostController, viewModel: ContactViewModelInterface, onPhotoSelected: (changeId: Int, pictureUri: Uri?) -> Unit) {
    val contactsList = viewModel.contactsList
    val contact = contactsList[contactId!!]
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if(isDarkMode) blue_accent else orange_accent),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = backgroundImage),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        val isDarkMode = isSystemInDarkTheme() // Check if the system is in dark mode
        Box(
            modifier = Modifier
                .width(360.dp)
                .height(720.dp)
                .background(
                    if(isDarkMode) blue_accent.copy(alpha = 0.75f) else orange_accent.copy(alpha = 0.75f),
                    shape = MaterialTheme.shapes.medium
                )
                .align(Alignment.Center)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxWidth()
            ) {
                // Top Left Button
                Button(
                    onClick = {
                        viewModel.closeAllContacts()
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .padding(start = 20.dp, top = 20.dp)
                        .size(50.dp),
                    contentPadding = PaddingValues(4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = if (isDarkMode) blue_icons.copy(alpha = 0.80f) else orange_icons.copy(alpha = 0.80f))
                ) {
                    Icon(
                        modifier = Modifier
                            .size(50.dp),
                        imageVector = Icons.Filled.KeyboardBackspace,
                        contentDescription = "Back Arrow",
                        tint = (if (isDarkMode) blue_accent.copy(alpha = 0.90f) else orange_accent.copy(alpha = 0.90f))
                    )
                }

                Text(
                    text = "Return" ,
                    color = if (isDarkMode) dark_text_color else light_text_color,
                    fontWeight = FontWeight.Bold,
                    fontSize = 3.em,
                    modifier = Modifier.padding(start = 23.dp)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .fillMaxWidth()
            ) {
                // Top Right Button
                Button(
                    onClick = { /* TRANSFER TO EDIT SCREEN */ },
                    modifier = Modifier
                        .padding(end = 20.dp, top = 20.dp)
                        .size(50.dp),
                    contentPadding = PaddingValues(4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = if (isDarkMode) blue_icons.copy(alpha = 0.80f) else orange_icons.copy(alpha = 0.80f))
                ) {
                    Icon(
                        modifier = Modifier
                            .scale(scaleX = -1f, scaleY = 1f)
                            .size(35.dp),
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit Symbol",
                        tint = (if (isDarkMode) blue_accent.copy(alpha = 0.90f) else orange_accent.copy(alpha = 0.90f))
                    )
                }

                Text(
                    text = "Edit" ,
                    color = if (isDarkMode) dark_text_color else light_text_color,
                    fontWeight = FontWeight.Bold,
                    fontSize = 3.em,
                    modifier = Modifier.padding(end = 31.dp)
                )
            }

            if (contact is PersonModel) {

//                var selectedImageUri by remember { mutableStateOf<Uri?>(contact.getPictureURL()) }
                var selectedImageUri by remember(contact) { mutableStateOf(contact.getPictureURL()) }

                Log.d("IMAGE BEFORE", contact.getPictureURL().toString())
                // State to control the visibility of the dropdown menu
                val (expanded, setExpanded) = remember { mutableStateOf(false) }

                // Store photoUri in state to persist across recompositions
                val photoUri = remember(selectedImageUri) {
                    context.contentResolver.insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        ContentValues().apply {
                            put(MediaStore.MediaColumns.DISPLAY_NAME, "ContactPhoto_${System.currentTimeMillis()}.jpg")
                            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                        }
                    )
                }

                val albumLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickVisualMedia(),
                    onResult = { uri: Uri? ->
                        if (uri != null) {
                            selectedImageUri = uri
                            onPhotoSelected(contactId, uri)
//                            contact.setPictureURL(uri)
//                            Log.d("IMAGE", uri.toString())
//                            val newContact = contact.copyPersonPicture(pictureURL = uri)
//                            contactsList[contactId!!] = newContact
//                            viewModel.contactsList = contactsList
//                            Log.d("IMAGE AFTER", (viewModel.contactsList[contactId!!] as PersonModel).getPictureURL().toString())
//                            onPhotoSelected(uri)
                        }
                    }
                )
                val cameraLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.TakePicture(),
                    onResult = { success ->
                        if (success && photoUri != null) {
                            selectedImageUri = photoUri
                            onPhotoSelected(contactId, photoUri)
//                            onPhotoSelected(photoUri)
                        } else {
//                            onPhotoSelected(null)
                        }
                    }
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        // Top Middle Button
                        Button(
                            onClick = {
                                setExpanded(true)
                            },
                            modifier = Modifier
                                .padding(top = 40.dp)
                                .size(120.dp),
                            contentPadding = PaddingValues(4.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isDarkMode) blue_icons.copy(
                                    alpha = 0.80f
                                ) else orange_icons.copy(alpha = 0.80f)
                            )
                        ) {
                            if (selectedImageUri != null) {
                                Image(
                                    painter = rememberAsyncImagePainter(model = selectedImageUri),
                                    contentDescription = "Selected Photo",
                                    modifier = Modifier
                                        .size(112.dp) // Slightly smaller than button to fit padding
                                        .clip(CircleShape), // Ensures the image is circular
                                    contentScale = ContentScale.Crop
                                )
                            }
                            else {
                                Icon(
                                    modifier = Modifier
                                        .size(80.dp),
                                    imageVector = Icons.Filled.PhotoCamera,
                                    contentDescription = "Contact Image",
                                    tint = (if (isDarkMode) blue_accent.copy(alpha = 0.90f) else orange_accent.copy(
                                        alpha = 0.90f
                                    ))
                                )
                            }
                        }

                        // DropdownMenu for options
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { setExpanded(false) }
                        ) {
                            // Option 1 inside DropdownMenuItem, text parameter passed correctly
                            DropdownMenuItem(
                                onClick = {
                                    setExpanded(false)
                                    albumLauncher.launch(
                                        PickVisualMediaRequest(
                                            ActivityResultContracts.PickVisualMedia.ImageOnly
                                        )
                                    )
                                },
                                text = { Text("Album") } // Correct way to use the 'text' parameter
                            )

                            // Option 2 inside DropdownMenuItem, text parameter passed correctly
                            DropdownMenuItem(
                                onClick = {
                                    setExpanded(false)
                                    if (photoUri != null) {
                                        photoUri?.let { cameraLauncher.launch(it) }
                                    }
                                },
                                text = { Text("Camera") } // Correct way to use the 'text' parameter
                            )
                        }
                    }


                    if (!expanded) {
                        Text(
                            text = contact.getName(),
                            color = if (isDarkMode) dark_text_color else light_text_color,
                            fontWeight = FontWeight.Bold,
                            fontSize = 6.em
                        )
                    }
                }

//                Column(
//                    verticalArrangement = Arrangement.spacedBy(3.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    modifier = Modifier
//                        .align(Alignment.TopCenter)
//                        .fillMaxWidth()
//                ) {
//                    // Top Middle Button
//                    Button(
//                        onClick = {
//
//                        },
//                        modifier = Modifier
//                            .padding(top = 40.dp)
//                            .size(120.dp),
//                        contentPadding = PaddingValues(4.dp),
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = if (isDarkMode) blue_icons.copy(
//                                alpha = 0.80f
//                            ) else orange_icons.copy(alpha = 0.80f)
//                        )
//                    ) {
//                        Icon(
//                            modifier = Modifier
//                                .size(80.dp),
//                            imageVector = Icons.Filled.PhotoCamera,
//                            contentDescription = "Contact Image",
//                            tint = (if (isDarkMode) blue_accent.copy(alpha = 0.90f) else orange_accent.copy(
//                                alpha = 0.90f
//                            ))
//                        )
//                    }
//
//                    Text(
//                        text = contact.getName(),
//                        color = if (isDarkMode) dark_text_color else light_text_color,
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 6.em
//                    )
//                }
            }
            else if (contact is BusinessModel) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                ) {
                    // Top Middle Button
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(3.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(top = 100.dp)
                    ) {
                        for (i in 1 until 6) {
                            val star = if (contact.getMyOpinionRating() > i) {
                                Log.d("OPINION", "FULL")
                                Icons.Filled.Star
                            } else if (contact.getMyOpinionRating() == (i - 0.5f)) {
                                Log.d("OPINION", "HALF")
                                Icons.AutoMirrored.Filled.StarHalf
                            } else {
                                Log.d("OPINION", "EMPTY")
                                Icons.Filled.StarBorder
                            }
                            Icon(
                                modifier = Modifier
                                    .size(60.dp),
                                imageVector = star,
                                contentDescription = "Star Image",
                                tint = (if (isDarkMode) blue_icons.copy(alpha = 0.90f) else orange_icons.copy(alpha = 0.90f))
                            )
                        }
                    }

                    Text(
                        text = contact.getName(),
                        color = if (isDarkMode) dark_text_color else light_text_color,
                        fontWeight = FontWeight.Bold,
                        fontSize = 6.em
                    )
                }
            }
            else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                ) {
                    // Top Middle Button
                    Button(
                        onClick = { /* TRANSFER TO EDIT SCREEN */ },
                        modifier = Modifier
                            .padding(top = 40.dp)
                            .size(120.dp),
                        contentPadding = PaddingValues(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isDarkMode) blue_icons.copy(
                                alpha = 0.80f
                            ) else orange_icons.copy(alpha = 0.80f)
                        )
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(80.dp),
                            imageVector = Icons.Filled.PhotoCamera,
                            contentDescription = "Contact Image",
                            tint = (if (isDarkMode) blue_accent.copy(alpha = 0.90f) else orange_accent.copy(
                                alpha = 0.90f
                            ))
                        )
                    }

                    Text(
                        text = contact.getName(),
                        color = if (isDarkMode) dark_text_color else light_text_color,
                        fontWeight = FontWeight.Bold,
                        fontSize = 6.em
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(3.dp),
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (contact is BusinessModel) {
                    Spacer(modifier = Modifier.height(230.dp))
                } else {
                    Spacer(modifier = Modifier.height(210.dp))
                }

                // Buttons
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Message Button
                    Column(
                        verticalArrangement = Arrangement.spacedBy(3.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Message Button
                        Button(
                            onClick = {
                                if (!contact.getPhoneNum().isNullOrEmpty()) {
                                    TextContact(contact.getPhoneNum(), context)
                                }
                            },
                            modifier = Modifier.size(60.dp),
                            contentPadding = PaddingValues(2.dp),
                            colors = if (!contact.getPhoneNum().isNullOrEmpty()) {
                                ButtonDefaults.buttonColors(containerColor = if (isDarkMode) blue_icons.copy(alpha = 0.80f) else orange_icons.copy(alpha = 0.80f))
                            } else {
                                ButtonDefaults.buttonColors(containerColor = invalid_button.copy(alpha = 0.80f))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Chat,
                                contentDescription = "Text",
                                modifier = Modifier.size(40.dp),
                                tint = (if (isDarkMode) blue_accent.copy(alpha = 0.90f) else orange_accent.copy(alpha = 0.90f))
                            )
                        }

                        Text(
                            text = "Message" ,
                            color = if (isDarkMode) dark_text_color else light_text_color,
                            fontWeight = FontWeight.Bold,
                            fontSize = 3.em
                        )
                    }

                    // Call Button
                    Column(
                        verticalArrangement = Arrangement.spacedBy(3.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                if (!contact.getPhoneNum().isNullOrEmpty()) {
                                    CallContact(contact.getPhoneNum(), context)
                                }
                            },
                            modifier = Modifier.size(60.dp),
                            contentPadding = PaddingValues(2.dp),
                            colors = if (!contact.getPhoneNum().isNullOrEmpty()) {
                                ButtonDefaults.buttonColors(containerColor = if (isDarkMode) blue_icons.copy(alpha = 0.80f) else orange_icons.copy(alpha = 0.80f))
                            } else {
                                ButtonDefaults.buttonColors(containerColor = invalid_button.copy(alpha = 0.80f))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Phone,
                                contentDescription = "Call",
                                modifier = Modifier.size(40.dp),
                                tint = (if (isDarkMode) blue_accent.copy(alpha = 0.90f) else orange_accent.copy(alpha = 0.90f))
                            )
                        }

                        Text(
                            text = "Call" ,
                            color = if (isDarkMode) dark_text_color else light_text_color,
                            fontWeight = FontWeight.Bold,
                            fontSize = 3.em
                        )
                    }

                    // Mail Button
                    Column(
                        verticalArrangement = Arrangement.spacedBy(3.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                if (!contact.getEmail().isNullOrEmpty()) {
                                    EmailContact(contact.getEmail(), context)
                                }
                            },
                            modifier = Modifier.size(60.dp),
                            contentPadding = PaddingValues(2.dp),
                            colors = if (!contact.getEmail().isNullOrEmpty()) {
                                ButtonDefaults.buttonColors(containerColor = if (isDarkMode) blue_icons.copy(alpha = 0.80f) else orange_icons.copy(alpha = 0.80f))
                            } else {
                                ButtonDefaults.buttonColors(containerColor = invalid_button.copy(alpha = 0.80f))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Mail,
                                contentDescription = "Email",
                                modifier = Modifier.size(40.dp),
                                tint = (if (isDarkMode) blue_accent.copy(alpha = 0.90f) else orange_accent.copy(alpha = 0.90f))
                            )
                        }

                        Text(
                            text = "Email" ,
                            color = if (isDarkMode) dark_text_color else light_text_color,
                            fontWeight = FontWeight.Bold,
                            fontSize = 3.em
                        )
                    }

                    // Map Button
                    Column(
                        verticalArrangement = Arrangement.spacedBy(3.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                if (!contact.getAddress().isNullOrEmpty()) {
                                    MapContact(contact.getAddress(), context)
                                }
                            },
                            modifier = Modifier.size(60.dp),
                            contentPadding = PaddingValues(2.dp),
                            colors = if (!contact.getAddress().isNullOrEmpty()) {
                                ButtonDefaults.buttonColors(containerColor = if (isDarkMode) blue_icons.copy(alpha = 0.80f) else orange_icons.copy(alpha = 0.80f))
                            } else {
                                ButtonDefaults.buttonColors(containerColor = invalid_button.copy(alpha = 0.80f))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Map,
                                contentDescription = "Map",
                                modifier = Modifier.size(40.dp),
                                tint = (if (isDarkMode) blue_accent.copy(alpha = 0.90f) else orange_accent.copy(alpha = 0.90f))
                            )
                        }

                        Text(
                            text = "Map" ,
                            color = if (isDarkMode) dark_text_color else light_text_color,
                            fontWeight = FontWeight.Bold,
                            fontSize = 3.em
                        )
                    }
                }

                Spacer(modifier = Modifier.height(3.dp))
                // Phone Number
                Box(
                    modifier = Modifier
                        .width(320.dp)
                        .padding(2.dp)
                        .background(
                            color = if (isDarkMode) blue_icons.copy(alpha = 0.60f) else orange_icons.copy(
                                alpha = 0.60f
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(horizontal = 16.dp, vertical = 12.dp) //Adjust padding to match your text field
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(30.dp),
                            imageVector = Icons.Filled.Numbers,
                            contentDescription = "PhoneNumber Icon",
                            tint = if (isDarkMode) blue_accent.copy(alpha = 0.90f) else orange_accent.copy(alpha = 0.90f)
                        )
                        Text(
                            text = if (contact.getPhoneNum().isNullOrEmpty()) {
                                "No Phone Number Entered"
                            } else {
                                contact.getPhoneNum()
                            },
                            color = if (isDarkMode) blue_accent.copy(alpha = 0.90f) else orange_accent.copy(
                                alpha = 0.90f
                            ),
                            fontWeight = FontWeight.Bold,
                            fontSize = 4.em
                        )
                    }
                }

                // Email Address
                Box(
                    modifier = Modifier
                        .width(320.dp)
                        .padding(2.dp)
                        .background(
                            color = if (isDarkMode) blue_icons.copy(alpha = 0.60f) else orange_icons.copy(
                                alpha = 0.60f
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(horizontal = 16.dp, vertical = 12.dp) //Adjust padding to match your text field
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(30.dp),
                            imageVector = Icons.Filled.AlternateEmail,
                            contentDescription = "Email Icon",
                            tint = if (isDarkMode) blue_accent.copy(alpha = 0.90f) else orange_accent.copy(alpha = 0.90f)
                        )
                        Text(
                            text = if (contact.getEmail().isNullOrEmpty()) {
                                "No Email Entered"
                            } else {
                                contact.getEmail()
                            },
                            color = if (isDarkMode) blue_accent.copy(alpha = 0.90f) else orange_accent.copy(
                                alpha = 0.90f
                            ),
                            fontWeight = FontWeight.Bold,
                            fontSize = 4.em
                        )
                    }
                }

                // Birthday or Website Link
                if (contact is PersonModel) {
                    Box(
                        modifier = Modifier
                            .width(320.dp)
                            .padding(2.dp)
                            .background(
                                color = if (isDarkMode) blue_icons.copy(alpha = 0.60f) else orange_icons.copy(
                                    alpha = 0.60f
                                ),
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(horizontal = 16.dp, vertical = 12.dp) //Adjust padding to match your text field
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(30.dp),
                                imageVector = Icons.Filled.Cake,
                                contentDescription = "Cake Icon",
                                tint = if (isDarkMode) blue_accent.copy(alpha = 0.90f) else orange_accent.copy(alpha = 0.90f)
                            )
                            Text(
                                text = if (contact.getBirthDate() == null) {
                                    "No birthday Entered"
                                } else {
                                    contact.getBirthDate().toString()
                                },
                                color = if (isDarkMode) blue_accent.copy(alpha = 0.90f) else orange_accent.copy(
                                    alpha = 0.90f
                                ),
                                fontWeight = FontWeight.Bold,
                                fontSize = 4.em
                            )
                        }
                    }
                }
                else if (contact is BusinessModel) {
                    Box(
                        modifier = Modifier
                            .width(320.dp)
                            .padding(2.dp)
                            .background(
                                color = if (isDarkMode) blue_icons.copy(alpha = 0.60f) else orange_icons.copy(
                                    alpha = 0.60f
                                ),
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(horizontal = 16.dp, vertical = 16.dp) //Adjust padding to match your text field
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(30.dp),
                                imageVector = Icons.Filled.AttachFile,
                                contentDescription = "Link Icon",
                                tint = if (isDarkMode) blue_accent.copy(alpha = 0.90f) else orange_accent.copy(alpha = 0.90f)
                            )
                            Text(
                                text = if (contact.getWebURL().isNullOrEmpty()) {
                                    "No website link Entered"
                                } else {
                                    contact.getWebURL()
                                },
                                color = if (isDarkMode) blue_accent.copy(alpha = 0.90f) else orange_accent.copy(
                                    alpha = 0.90f
                                ),
                                fontWeight = FontWeight.Bold,
                                fontSize = 4.em
                            )
                        }
                    }
                }

                // Address
                Box(
                    modifier = Modifier
                        .width(320.dp)
                        .padding(2.dp)
                        .background(
                            color = if (isDarkMode) blue_icons.copy(alpha = 0.60f) else orange_icons.copy(
                                alpha = 0.60f
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(horizontal = 16.dp, vertical = 16.dp) //Adjust padding to match your text field
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(30.dp),
                            imageVector = Icons.Filled.AddRoad,
                            contentDescription = "Address Icon",
                            tint = if (isDarkMode) blue_accent.copy(alpha = 0.90f) else orange_accent.copy(alpha = 0.90f)
                        )
                        Text(
                            text = if (contact.getAddress().isNullOrEmpty()) {
                                "No address Entered"
                            } else {
                                contact.getAddress()
                            },
                            color = if (isDarkMode) blue_accent.copy(alpha = 0.90f) else orange_accent.copy(
                                alpha = 0.90f
                            ),
                            fontWeight = FontWeight.Bold,
                            fontSize = 4.em
                        )
                    }
                }

                // Groups and business days open
                if (contact is PersonModel) {
                    Box(
                        modifier = Modifier
                            .width(320.dp)
                            .padding(2.dp)
                            .background(
                                color = if (isDarkMode) blue_icons.copy(alpha = 0.60f) else orange_icons.copy(
                                    alpha = 0.60f
                                ),
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 12.dp
                                ) //Adjust padding to match your text field
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(30.dp),
                                imageVector = Icons.Filled.Group,
                                contentDescription = "Group Icon",
                                tint = if (isDarkMode) blue_accent.copy(alpha = 0.90f) else orange_accent.copy(
                                    alpha = 0.90f
                                )
                            )
                            Text(
                                text = if (contact.getGroup().isNullOrEmpty()) {
                                    "No groups"
                                } else {
                                    contact.getGroup()
                                },
                                color = if (isDarkMode) blue_accent.copy(alpha = 0.90f) else orange_accent.copy(
                                    alpha = 0.90f
                                ),
                                fontWeight = FontWeight.Bold,
                                fontSize = 4.em
                            )
                        }
                    }
                } else if (contact is BusinessModel) {
                    Box(
                        modifier = Modifier
                            .width(320.dp)
                            .padding(2.dp)
                            .background(
                                color = if (isDarkMode) blue_icons.copy(alpha = 0.60f) else orange_icons.copy(
                                    alpha = 0.60f
                                ),
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 12.dp
                                ) //Adjust padding to match your text field
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(30.dp),
                                imageVector = Icons.Filled.Today,
                                contentDescription = "Days Open Icon",
                                tint = if (isDarkMode) blue_accent.copy(alpha = 0.90f) else orange_accent.copy(
                                    alpha = 0.90f
                                )
                            )
                            Text(
                                text = contact.getDaysOpenForText(),
                                color = if (isDarkMode) blue_accent.copy(alpha = 0.90f) else orange_accent.copy(
                                    alpha = 0.90f
                                ),
                                fontWeight = FontWeight.Bold,
                                fontSize = 4.em
                            )
                        }
                    }
                }

                // Favorites
                if (contact is PersonModel) {
                    val favoriteColor = if (contact.getFavorite()) {
                            if (isDarkMode) {
                                blue_accent.copy(alpha = 0.60f)
                            } else {
                                orange_accent.copy(alpha = 0.60f)
                            }
                        } else {
                            if (isDarkMode) {
                                blue_icons.copy(alpha = 0.60f)
                            } else {
                                orange_icons.copy(alpha = 0.60f)
                            }
                        }
                    val secondaryColor = if (!contact.getFavorite()) {
                        if (isDarkMode) {
                            blue_accent.copy(alpha = 0.90f)
                        } else {
                            orange_accent.copy(alpha = 0.90f)
                        }
                    } else {
                        if (isDarkMode) {
                            blue_icons.copy(alpha = 0.90f)
                        } else {
                            orange_icons.copy(alpha = 0.90f)
                        }
                    }
                    Box(
                        modifier = Modifier
                            .width(320.dp)
                            .padding(2.dp)
                            .then(
                                if (contact.getFavorite()) {
                                    Modifier.border(
                                        width = 2.dp,
                                        color = secondaryColor,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                } else {
                                    Modifier
                                }
                            )
                            .background(
                                color = favoriteColor,
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 12.dp
                                ) //Adjust padding to match your text field
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(30.dp),
                                imageVector = if(contact.getFavorite()) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = "Group Icon",
                                tint = secondaryColor
                            )
                            Text(
                                text = if (!contact.getFavorite()) {
                                    "Not a Favorite"
                                } else {
                                    "Favorite"
                                },
                                color = secondaryColor,
                                fontWeight = FontWeight.Bold,
                                fontSize = 4.em
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPersonContact(backgroundImage: Int, viewModel: ContactViewModelInterface, navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = backgroundImage),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        val isDarkMode = isSystemInDarkTheme() // Check if the system is in dark mode
        Box(
            modifier = Modifier
                .width(360.dp)
                .height(720.dp)
                .background(
                    orange_accent.copy(alpha = 0.75f),
                    shape = MaterialTheme.shapes.medium
                )
                .align(Alignment.Center)
        ) {
            // Top Left Button
            Button(
                onClick = {
                    viewModel.closeAllContacts()
                    navController.popBackStack()
                },
                modifier = Modifier
                    .padding(20.dp)
                    .size(50.dp)
                    .align(Alignment.TopStart),
                contentPadding = PaddingValues(4.dp)
            ) {
                Text("<-", fontWeight = FontWeight.Bold, fontSize = 30.sp)
            }

            // Top Right Button
            Button(
                onClick = { /* Handle click for top right button */ },
                modifier = Modifier
                    .padding(20.dp)
                    .size(50.dp)
                    .align(Alignment.TopEnd),
                contentPadding = PaddingValues(4.dp)
            ) {
                Text("+", fontWeight = FontWeight.Bold, fontSize = 30.sp)
            }

            Spacer(modifier = Modifier.height(10.dp))

            var pictureURL by remember { mutableStateOf("") }
            var nameContact by remember { mutableStateOf("") }
            var isFamily by remember { mutableStateOf(false) }
            var isFavorite by remember { mutableStateOf(false) }
            var isEmergency by remember { mutableStateOf(false) }
            var phoneNum by remember { mutableStateOf("") }
            var emailAddress by remember { mutableStateOf("") }
            var birthday by remember { mutableStateOf("") }
            var address by remember { mutableStateOf("") }
            var groups by remember { mutableStateOf("") }

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(75.dp))
                TextField(
                    value = pictureURL,
                    onValueChange = { textFieldContents -> pictureURL = textFieldContents },
                    placeholder = { Text("Picture", color = if (isDarkMode) dark_text_color else light_text_color) },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(30.dp),
                            imageVector = Icons.Filled.PhotoCamera,
                            contentDescription = "Camera Icon",
                            tint = if (isDarkMode) blue_icons else orange_icons
                        )
                    },
                    shape = RoundedCornerShape(64.dp),
                    modifier = Modifier
                        .width(320.dp)
                        .padding(2.dp), // Adjust padding instead of height
                    textStyle = TextStyle(fontSize = 16.sp, color = if (isDarkMode) dark_text_color else light_text_color),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedTextColor = if (isDarkMode) dark_text_color else light_text_color,
                        containerColor = if (isDarkMode) blue_accent else orange_accent,
                        focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
                TextField(
                    value = nameContact,
                    onValueChange = { textFieldContents -> nameContact = textFieldContents },
                    placeholder = { Text("Name", color = if (isDarkMode) dark_text_color else light_text_color) },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(30.dp),
                            imageVector = Icons.Filled.Abc,
                            contentDescription = "Letters Icon",
                            tint = if (isDarkMode) blue_icons else orange_icons
                        )
                    },
                    shape = RoundedCornerShape(64.dp),
                    modifier = Modifier
                        .width(320.dp)
                        .padding(2.dp), // Adjust padding instead of height
                    textStyle = TextStyle(fontSize = 16.sp, color = if (isDarkMode) dark_text_color else light_text_color),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedTextColor = if (isDarkMode) dark_text_color else light_text_color,
                        containerColor = if (isDarkMode) blue_accent else orange_accent,
                        focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Button(onClick = {
                        isFamily = !isFamily
                    }, modifier = Modifier.size(50.dp), contentPadding = PaddingValues(2.dp)) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Button(onClick = {
                        isFavorite = !isFavorite
                    }, modifier = Modifier.size(50.dp), contentPadding = PaddingValues(2.dp)) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Favorite",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Button(onClick = {
                        isEmergency = !isEmergency
                    }, modifier = Modifier.size(50.dp), contentPadding = PaddingValues(2.dp)) {
                        Icon(
                            imageVector = Icons.Outlined.Warning,
                            contentDescription = "Emergency",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
                TextField(
                    value = phoneNum,
                    onValueChange = { textFieldContents -> phoneNum = textFieldContents },
                    placeholder = { Text("Phone Number", color = if (isDarkMode) dark_text_color else light_text_color) },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(30.dp),
                            imageVector = Icons.Filled.Phone,
                            contentDescription = "Phone Icon",
                            tint = if (isDarkMode) blue_icons else orange_icons
                        )
                    },
                    shape = RoundedCornerShape(64.dp),
                    modifier = Modifier
                        .width(320.dp)
                        .padding(2.dp), // Adjust padding instead of height
                    textStyle = TextStyle(fontSize = 16.sp, color = if (isDarkMode) dark_text_color else light_text_color),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedTextColor = if (isDarkMode) dark_text_color else light_text_color,
                        containerColor = if (isDarkMode) blue_accent else orange_accent,
                        focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
                TextField(
                    value = emailAddress,
                    onValueChange = { textFieldContents -> emailAddress = textFieldContents },
                    placeholder = { Text("Email Address", color = if (isDarkMode) dark_text_color else light_text_color) },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(30.dp),
                            imageVector = Icons.Filled.Mail,
                            contentDescription = "Mail Icon",
                            tint = if (isDarkMode) blue_icons else orange_icons
                        )
                    },
                    shape = RoundedCornerShape(64.dp),
                    modifier = Modifier
                        .width(320.dp)
                        .padding(2.dp), // Adjust padding instead of height
                    textStyle = TextStyle(fontSize = 16.sp, color = if (isDarkMode) dark_text_color else light_text_color),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedTextColor = if (isDarkMode) dark_text_color else light_text_color,
                        containerColor = if (isDarkMode) blue_accent else orange_accent,
                        focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
                TextField(
                    value = birthday,
                    onValueChange = { textFieldContents -> birthday = textFieldContents },
                    placeholder = { Text("Birthday", color = if (isDarkMode) dark_text_color else light_text_color) },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(30.dp),
                            imageVector = Icons.Filled.Cake,
                            contentDescription = "Cake Icon",
                            tint = if (isDarkMode) blue_icons else orange_icons
                        )
                    },
                    shape = RoundedCornerShape(64.dp),
                    modifier = Modifier
                        .width(320.dp)
                        .padding(2.dp), // Adjust padding instead of height
                    textStyle = TextStyle(fontSize = 16.sp, color = if (isDarkMode) dark_text_color else light_text_color),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedTextColor = if (isDarkMode) dark_text_color else light_text_color,
                        containerColor = if (isDarkMode) blue_accent else orange_accent,
                        focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
                TextField(
                    value = address,
                    onValueChange = { textFieldContents -> address = textFieldContents },
                    placeholder = { Text("Address", color = if (isDarkMode) dark_text_color else light_text_color) },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(30.dp),
                            imageVector = Icons.Filled.EditRoad,
                            contentDescription = "Phone Icon",
                            tint = if (isDarkMode) blue_icons else orange_icons
                        )
                    },
                    shape = RoundedCornerShape(64.dp),
                    modifier = Modifier
                        .width(320.dp)
                        .padding(2.dp), // Adjust padding instead of height
                    textStyle = TextStyle(fontSize = 16.sp, color = if (isDarkMode) dark_text_color else light_text_color),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedTextColor = if (isDarkMode) dark_text_color else light_text_color,
                        containerColor = if (isDarkMode) blue_accent else orange_accent,
                        focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
                TextField(
                    value = groups,
                    onValueChange = { textFieldContents -> groups = textFieldContents },
                    placeholder = { Text("Groups", color = if (isDarkMode) dark_text_color else light_text_color) },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(30.dp),
                            imageVector = Icons.Filled.Group,
                            contentDescription = "Group Icon",
                            tint = if (isDarkMode) blue_icons else orange_icons
                        )
                    },
                    shape = RoundedCornerShape(64.dp),
                    modifier = Modifier
                        .width(320.dp)
                        .padding(2.dp), // Adjust padding instead of height
                    textStyle = TextStyle(fontSize = 16.sp, color = if (isDarkMode) dark_text_color else light_text_color),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedTextColor = if (isDarkMode) dark_text_color else light_text_color,
                        containerColor = if (isDarkMode) blue_accent else orange_accent,
                        focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBusinessContact(backgroundImage: Int, viewModel: ContactViewModelInterface, navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = backgroundImage),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        val isDarkMode = isSystemInDarkTheme() // Check if the system is in dark mode
        Box(
            modifier = Modifier
                .width(360.dp)
                .height(720.dp)
                .background(
                    orange_accent.copy(alpha = 0.75f),
                    shape = MaterialTheme.shapes.medium
                )
                .align(Alignment.Center)
        ) {
            // Top Left Button
            Button(
                onClick = {
                    viewModel.closeAllContacts()
                    navController.popBackStack()
                },
                modifier = Modifier
                    .padding(20.dp)
                    .size(50.dp)
                    .align(Alignment.TopStart),
                contentPadding = PaddingValues(4.dp)
            ) {
                Text("<-", fontWeight = FontWeight.Bold, fontSize = 30.sp)
            }

            // Top Right Button
            Button(
                onClick = { /* Handle click for top right button */ },
                modifier = Modifier
                    .padding(20.dp)
                    .size(50.dp)
                    .align(Alignment.TopEnd),
                contentPadding = PaddingValues(4.dp)
            ) {
                Text("+", fontWeight = FontWeight.Bold, fontSize = 30.sp)
            }
            Spacer(modifier = Modifier.height(10.dp))

            var pictureURL by remember { mutableStateOf("") }
            var nameContact by remember { mutableStateOf("") }
            var opinion by remember { mutableStateOf( false ) }
            var isFavorite by remember { mutableStateOf( false ) }
            var daysOpen by remember { mutableStateOf( false ) }
            var phoneNum by remember { mutableStateOf("") }
            var emailAddress by remember { mutableStateOf("") }
            var websiteLink by remember { mutableStateOf("") }
            var address by remember { mutableStateOf("") }
            var groups by remember { mutableStateOf("") }

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(75.dp))
                TextField(
                    value = pictureURL,
                    onValueChange = { textFieldContents -> pictureURL = textFieldContents },
                    placeholder = { Text("Picture", color = if (isDarkMode) dark_text_color else light_text_color) },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(30.dp),
                            imageVector = Icons.Filled.PhotoCamera,
                            contentDescription = "Camera Icon",
                            tint = if (isDarkMode) blue_icons else orange_icons
                        )
                    },
                    shape = RoundedCornerShape(64.dp),
                    modifier = Modifier
                        .width(320.dp)
                        .padding(2.dp), // Adjust padding instead of height
                    textStyle = TextStyle(fontSize = 16.sp, color = if (isDarkMode) dark_text_color else light_text_color),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedTextColor = if (isDarkMode) dark_text_color else light_text_color,
                        containerColor = if (isDarkMode) blue_accent else orange_accent,
                        focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
                TextField(
                    value = nameContact,
                    onValueChange = { textFieldContents -> nameContact = textFieldContents },
                    placeholder = { Text("Name", color = if (isDarkMode) dark_text_color else light_text_color) },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(30.dp),
                            imageVector = Icons.Filled.Abc,
                            contentDescription = "Letters Icon",
                            tint = if (isDarkMode) blue_icons else orange_icons
                        )
                    },
                    shape = RoundedCornerShape(64.dp),
                    modifier = Modifier
                        .width(320.dp)
                        .padding(2.dp), // Adjust padding instead of height
                    textStyle = TextStyle(fontSize = 16.sp, color = if (isDarkMode) dark_text_color else light_text_color),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedTextColor = if (isDarkMode) dark_text_color else light_text_color,
                        containerColor = if (isDarkMode) blue_accent else orange_accent,
                        focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Button(onClick = {
                        opinion = !opinion
                    }, modifier = Modifier.size(50.dp), contentPadding = PaddingValues(2.dp)) {
                        Icon(
                            imageVector = Icons.Outlined.EmojiObjects,
                            contentDescription = "Ideas",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Button(onClick = {
                        isFavorite = !isFavorite
                    }, modifier = Modifier.size(50.dp), contentPadding = PaddingValues(2.dp)) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Favorite",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Button(onClick = {
                        daysOpen = !daysOpen
                    }, modifier = Modifier.size(50.dp), contentPadding = PaddingValues(2.dp)) {
                        Icon(
                            imageVector = Icons.Outlined.CalendarMonth,
                            contentDescription = "Calendar",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
                TextField(
                    value = phoneNum,
                    onValueChange = { textFieldContents -> phoneNum = textFieldContents },
                    placeholder = { Text("Phone Number", color = if (isDarkMode) dark_text_color else light_text_color) },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(30.dp),
                            imageVector = Icons.Filled.Phone,
                            contentDescription = "Phone Icon",
                            tint = if (isDarkMode) blue_icons else orange_icons
                        )
                    },
                    shape = RoundedCornerShape(64.dp),
                    modifier = Modifier
                        .width(320.dp)
                        .padding(2.dp), // Adjust padding instead of height
                    textStyle = TextStyle(fontSize = 16.sp, color = if (isDarkMode) dark_text_color else light_text_color),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedTextColor = if (isDarkMode) dark_text_color else light_text_color,
                        containerColor = if (isDarkMode) blue_accent else orange_accent,
                        focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
                TextField(
                    value = emailAddress,
                    onValueChange = { textFieldContents -> emailAddress = textFieldContents },
                    placeholder = { Text("Email Address", color = if (isDarkMode) dark_text_color else light_text_color) },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(30.dp),
                            imageVector = Icons.Filled.Mail,
                            contentDescription = "Mail Icon",
                            tint = if (isDarkMode) blue_icons else orange_icons
                        )
                    },
                    shape = RoundedCornerShape(64.dp),
                    modifier = Modifier
                        .width(320.dp)
                        .padding(2.dp), // Adjust padding instead of height
                    textStyle = TextStyle(fontSize = 16.sp, color = if (isDarkMode) dark_text_color else light_text_color),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedTextColor = if (isDarkMode) dark_text_color else light_text_color,
                        containerColor = if (isDarkMode) blue_accent else orange_accent,
                        focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
                TextField(
                    value = websiteLink,
                    onValueChange = { textFieldContents -> websiteLink = textFieldContents },
                    placeholder = { Text("Website Link", color = if (isDarkMode) dark_text_color else light_text_color) },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(30.dp),
                            imageVector = Icons.Filled.Computer,
                            contentDescription = "Website Icon",
                            tint = if (isDarkMode) blue_icons else orange_icons
                        )
                    },
                    shape = RoundedCornerShape(64.dp),
                    modifier = Modifier
                        .width(320.dp)
                        .padding(2.dp), // Adjust padding instead of height
                    textStyle = TextStyle(fontSize = 16.sp, color = if (isDarkMode) dark_text_color else light_text_color),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedTextColor = if (isDarkMode) dark_text_color else light_text_color,
                        containerColor = if (isDarkMode) blue_accent else orange_accent,
                        focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
                TextField(
                    value = address,
                    onValueChange = { textFieldContents -> address = textFieldContents },
                    placeholder = { Text("Address", color = if (isDarkMode) dark_text_color else light_text_color) },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(30.dp),
                            imageVector = Icons.Filled.EditRoad,
                            contentDescription = "Phone Icon",
                            tint = if (isDarkMode) blue_icons else orange_icons
                        )
                    },
                    shape = RoundedCornerShape(64.dp),
                    modifier = Modifier
                        .width(320.dp)
                        .padding(2.dp), // Adjust padding instead of height
                    textStyle = TextStyle(fontSize = 16.sp, color = if (isDarkMode) dark_text_color else light_text_color),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedTextColor = if (isDarkMode) dark_text_color else light_text_color,
                        containerColor = if (isDarkMode) blue_accent else orange_accent,
                        focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
                TextField(
                    value = groups,
                    onValueChange = { textFieldContents -> groups = textFieldContents },
                    placeholder = { Text("Groups", color = if (isDarkMode) dark_text_color else light_text_color) },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(30.dp),
                            imageVector = Icons.Filled.Group,
                            contentDescription = "Group Icon",
                            tint = if (isDarkMode) blue_icons else orange_icons
                        )
                    },
                    shape = RoundedCornerShape(64.dp),
                    modifier = Modifier
                        .width(320.dp)
                        .padding(2.dp), // Adjust padding instead of height
                    textStyle = TextStyle(fontSize = 16.sp, color = if (isDarkMode) dark_text_color else light_text_color),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedTextColor = if (isDarkMode) dark_text_color else light_text_color,
                        containerColor = if (isDarkMode) blue_accent else orange_accent,
                        focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
            }
        }
    }
}

fun TextContact(phoneNumber: String, context: Context) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:$phoneNumber"))
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        // Handle the case where there is no SMS app installed
        // Or the user doesn't have permission to send SMS
    }
}

fun CallContact(phoneNumber: String, context: Context) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$phoneNumber")
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        // Handle the case where there is no SMS app installed
        // Or the user doesn't have permission to send SMS
    }
}

fun EmailContact(email: String, context: Context) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:") // only email apps should handle this
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
    }
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        // Handle the case where there is no SMS app installed
        // Or the user doesn't have permission to send SMS
    }
}

fun MapContact(address: String, context: Context) {
    val encodedAddress = URLEncoder.encode(address, "UTF-8")
    val gmmIntentUri = Uri.parse("geo:0,0?q=$encodedAddress")
    val intent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    intent.setPackage("com.google.android.apps.maps")
    try {
        context.startActivity(intent)
    } catch (e: android.content.ActivityNotFoundException) {
        Log.d("ERROR", "ERROR REACHED")
        // Handle the exception if Google Maps is not installed
        val genericMapIntentUri = Uri.parse("geo:0,0?q=$encodedAddress")
        val genericMapIntent = Intent(Intent.ACTION_VIEW, genericMapIntentUri)
        context.startActivity(genericMapIntent)
    }
}

/*
@Composable
fun PhotoContact(context: Context) {
    var contactPhotoUri by remember { mutableStateOf<Uri?>(null) }

    ContactPhotoPickerButton(context = context) { uri ->
        contactPhotoUri = uri
        // Use the selected photo URI (contactPhotoUri) here.
    }
}

@Composable
fun ContactPhotoPickerButton(context: Context, onPhotoSelected: (Uri?) -> Unit) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    // State to control the visibility of the dropdown menu
    val (expanded, setExpanded) = remember { mutableStateOf(false) }

    // Store photoUri in state to persist across recompositions
    val photoUri = remember {
        context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "ContactPhoto_${System.currentTimeMillis()}.jpg")
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            }
        )
    }

    val albumLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                selectedImageUri = uri
                onPhotoSelected(uri)
            }
        }
    )
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success && photoUri != null) {
                selectedImageUri = photoUri
                onPhotoSelected(photoUri)
            } else {
                onPhotoSelected(null)
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (selectedImageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(model = selectedImageUri),
                contentDescription = "Selected Photo",
                modifier = Modifier
                    .size(200.dp) // Adjust size as needed
                    .padding(16.dp),
                contentScale = ContentScale.Crop // or ContentScale.Fit
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                onClick = {
                    setExpanded(true)
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Select Photo")
            }

            // DropdownMenu for options
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { setExpanded(false) }
            ) {
                // Option 1 inside DropdownMenuItem, text parameter passed correctly
                DropdownMenuItem(
                    onClick = {
                        setExpanded(false)
                        albumLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    },
                    text = { Text("Album") } // Correct way to use the 'text' parameter
                )

                // Option 2 inside DropdownMenuItem, text parameter passed correctly
                DropdownMenuItem(
                    onClick = {
                        setExpanded(false)
                        if (photoUri != null) {
                            photoUri?.let { cameraLauncher.launch(it) }
                        }
                    },
                    text = { Text("Camera") } // Correct way to use the 'text' parameter
                )
            }
        }
    }
}


data class JokeModel(
    val id: Int,
    var question: String,
    var answer : String,
    var answerIsVisible: Boolean
)
*/
