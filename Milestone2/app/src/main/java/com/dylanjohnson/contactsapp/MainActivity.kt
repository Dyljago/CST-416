package com.dylanjohnson.contactsapp

import android.app.UiModeManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.EditRoad
import androidx.compose.material.icons.outlined.EmojiObjects
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
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
import androidx.compose.ui.tooling.preview.Preview
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dylanjohnson.contactsapp.classes.ThemeUtils
import com.dylanjohnson.contactsapp.models.BusinessModel
import com.dylanjohnson.contactsapp.models.ContactModel
import com.dylanjohnson.contactsapp.models.PersonModel
import com.dylanjohnson.contactsapp.models.ScreenInfo
import com.dylanjohnson.contactsapp.ui.theme.blue_accent
import com.dylanjohnson.contactsapp.ui.theme.blue_icons
import com.dylanjohnson.contactsapp.ui.theme.blue_selected
import com.dylanjohnson.contactsapp.ui.theme.orange_accent
import com.dylanjohnson.contactsapp.ui.theme.orange_icons
import com.dylanjohnson.contactsapp.ui.theme.orange_selected
import com.dylanjohnson.contactsapp.ui.theme.orange_status
import com.dylanjohnson.contactsapp.ui.theme.text_color
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
                    MainScreen()
//                    PopupButtonExample(context = this)
                }
            }
        }
    }
}

@Composable
fun Contact(contact: ContactModel, changeVisibility: (id:Int) -> Unit, context: Context) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Contact Number: ${contact.getId()}",
            fontSize = 5.em
        )
        androidx.compose.material3.Button(onClick = {
            Log.d("Toast Message", contact.toString())
            Toast
                .makeText(context, contact.toString(), Toast.LENGTH_LONG)
                .show()
        }) {
            Text(text = "Edit")
        }
    }
    val isDarkMode = isSystemInDarkTheme() // Check if the system is in dark mode
    val textColor = if (isDarkMode) Color(0xFFCF5AF1) else Color(0xFFF85F54)
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
        Column {
            Text(
                modifier = Modifier.padding(10.dp),
                fontSize = 5.em,
                fontWeight = FontWeight.Bold,
                text = contact.getEmail()
            )
            Text(
                modifier = Modifier.padding(10.dp),
                fontSize = 5.em,
                fontWeight = FontWeight.Bold,
                text = contact.getPhoneNum()
            )
            Text(
                modifier = Modifier.padding(10.dp),
                fontSize = 5.em,
                fontWeight = FontWeight.Bold,
                text = contact.getAddress()
            )
        }
    }
    Divider(color = Color.Black)
}

/*
// Here, we use AndroidView to display the search bar in the XML layout.
        AndroidView(
            factory = { context ->
                // Inflate the bottom_bar.xml layout
                val layout = layoutInflater.inflate(R.layout.search_bar, null) as LinearLayout

                val searchColor = ContextCompat.getColor(context, if (isDarkMode) R.color.blue_accent else R.color.orange_accent)

                // Set the search bar tint for the search bar background drawable
                val searchBarBackground = ContextCompat.getDrawable(context, R.drawable.search_bar)
                searchBarBackground?.let {
                    // Apply the tint to the background
                    DrawableCompat.setTint(it, searchColor)

                    // Set the tinted background to the EditText
                    val editText = layout.findViewById<EditText>(R.id.search_bar)
                    editText.background = it
                }

                val themeColor = ContextCompat.getColor(context, if (isDarkMode) R.color.blue_icons else R.color.orange_icons)

                val searchImage = layout.findViewById<ImageView>(R.id.search_icon)

                // Set the icon tint for the search bar
                val searchIcon = ContextCompat.getDrawable(context, R.drawable.search_icon)
                searchIcon?.setTint(themeColor)
                searchImage.setImageDrawable(searchIcon)

                layout // Return the layout
            },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
        )
        // Here, we use AndroidView to display the bottom bar in the XML layout.
        AndroidView(
            factory = { context ->
                // Inflate the bottom_bar.xml layout
                val layout = layoutInflater.inflate(R.layout.bottom_bar, null) as LinearLayout

                val searchColor = ContextCompat.getColor(context, if (isDarkMode) R.color.blue_accent else R.color.orange_accent)

                // Set the background color of the layout
                layout.setBackgroundColor(searchColor)

                val themeColor = ContextCompat.getColor(context, if (isDarkMode) R.color.blue_icons else R.color.orange_icons)

                val contactsButton = layout.findViewById<Button>(R.id.button_contacts)
                contactsButton.setOnClickListener {
                    // Handle contacts button click
                }

                // Set the icon tint for the contacts button
                val contactsIcon = ContextCompat.getDrawable(context, R.drawable.contacts_icon)
                contactsIcon?.setTint(themeColor)
                contactsButton.setCompoundDrawablesWithIntrinsicBounds(null, contactsIcon, null, null)

                val favoritesButton = layout.findViewById<Button>(R.id.button_favorites)
                favoritesButton.setOnClickListener {
                    // Handle favorites button click
                }

                // Set the icon tint for the favorites button
                val favoritesIcon = ContextCompat.getDrawable(context, R.drawable.favorites_icon)
                favoritesIcon?.setTint(themeColor)
                favoritesButton.setCompoundDrawablesWithIntrinsicBounds(null, favoritesIcon, null, null)

                val groupsButton = layout.findViewById<Button>(R.id.button_groups)
                groupsButton.setOnClickListener {
                    // Handle groups button click
                }

                // Set the icon tint for the groups button
                val groupsIcon = ContextCompat.getDrawable(context, R.drawable.groups_icon)
                groupsIcon?.setTint(themeColor)
                groupsButton.setCompoundDrawablesWithIntrinsicBounds(null, groupsIcon, null, null)

                layout // Return the layout
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactList(contacts: MutableList<ContactModel>, backgroundImage: Int) {
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
                            Log.d("Contact tag", "You clicked contact number: $it")

                            // toggle the visibility property of the joke that was clicked
                            // notice the ! (negation) character
                            var changedContact =
                                contacts[it].copy(isExpanded = !contacts[it].getExpanded())

                            // replace the joke item at position number "it"
                            contacts[it] = changedContact
                        },
                        context = LocalContext.current
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
                placeholder = { Text("Search", color = text_color) },
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
                textStyle = TextStyle(fontSize = 16.sp, color = text_color),
                colors = TextFieldDefaults.textFieldColors(
                    focusedTextColor = text_color,
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
fun MainScreen() {
    // Track the system theme
    ThemeUtils.TrackSystemTheme()
    isDarkMode = ThemeUtils.isDarkMode.value

    val navController = rememberNavController()

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

    var contacts = remember {
        mutableStateListOf(
            ContactModel(
                0,
                "Person Example",
                "test@test.com",
                "555-555-5555",
                "11111 east fake street",
                "Zoo",
                "Narnia",
                "99999",
                false
            ),
            ContactModel(
                1,
                "Business Example",
                "business@test.com",
                "555-678-9101",
                "99999 west business street",
                "Busy",
                "Ness",
                "00000",
                false
            ),
            ContactModel(
                2,
                "Test",
                "test@test.com",
                "555-555-5555",
                "11111 west fake street",
                "Zoo",
                "Narnia",
                "99999",
                false
            ),
            ContactModel(
                3,
                "Test2",
                "test2@test.com",
                "555-556-5555",
                "11111 south fake street",
                "Aquarium",
                "Narnia",
                "99998",
                false
            ),
            ContactModel(
                4,
                "Test3",
                "test3@test.com",
                "555-566-5555",
                "11111 north fake street",
                "Movies",
                "Narnia",
                "99997",
                false
            )
        )
    }

    val context = LocalContext.current

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
            composable = { ContactList(contacts, backgroundImage) }
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
            composable = { SettingsScreen(backgroundImage) }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(backgroundImage: Int) {
    // State to control the visibility of the dropdown menu
    val (expanded, setExpanded) = remember { mutableStateOf(false) }
    val (selectedOption, setSelectedOption) = remember { mutableStateOf("") }
    var selectedOptionRemember by remember { mutableStateOf("") }
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

        if (selectedOptionRemember == "") {
            LazyColumn() {
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
                item {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        val isDarkMode =
                            isSystemInDarkTheme() // Check if the system is in dark mode
                        val textColor = if (isDarkMode) Color(0xFFCF5AF1) else Color(0xFFF85F54)
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
                                    setSelectedOption("Add Person")
                                    selectedOptionRemember = ("Add Person")
                                    setExpanded(false)
                                },
                                text = { Text("Add Person") } // Correct way to use the 'text' parameter
                            )

                            // Option 2 inside DropdownMenuItem, text parameter passed correctly
                            DropdownMenuItem(
                                onClick = {
                                    setSelectedOption("Add Business")
                                    selectedOptionRemember = ("Add Business")
                                    setExpanded(false)
                                },
                                text = { Text("Add Business") } // Correct way to use the 'text' parameter
                            )
                        }

                        if (selectedOption != "") {
                            Toast
                                .makeText(LocalContext.current, selectedOption, Toast.LENGTH_LONG)
                                .show()
                            setSelectedOption("")
                        }
                    }
                    Divider(color = Color.Black)
                }
                item {
                    Spacer(modifier = Modifier.height(70.dp))
                }
            }
        }
        else if (selectedOptionRemember == "Add Person") {
            Box(
                modifier = Modifier
                    .width(360.dp)
                    .height(720.dp)
                    .background(orange_accent.copy(alpha = 0.75f), shape = MaterialTheme.shapes.medium)
                    .align(Alignment.Center)
            ) {
                // Top Left Button
                Button(
                    onClick = { selectedOptionRemember = "" },
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
                var isFamily by remember { mutableStateOf( false ) }
                var isFavorite by remember { mutableStateOf( false ) }
                var isEmergency by remember { mutableStateOf( false ) }
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
                        placeholder = { Text("Picture", color = text_color) },
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
                        textStyle = TextStyle(fontSize = 16.sp, color = text_color),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedTextColor = text_color,
                            containerColor = if (isDarkMode) blue_accent else orange_accent,
                            focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true
                    )
                    TextField(
                        value = nameContact,
                        onValueChange = { textFieldContents -> nameContact = textFieldContents },
                        placeholder = { Text("Name", color = text_color) },
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
                        textStyle = TextStyle(fontSize = 16.sp, color = text_color),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedTextColor = text_color,
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
                        placeholder = { Text("Phone Number", color = text_color) },
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
                        textStyle = TextStyle(fontSize = 16.sp, color = text_color),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedTextColor = text_color,
                            containerColor = if (isDarkMode) blue_accent else orange_accent,
                            focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true
                    )
                    TextField(
                        value = emailAddress,
                        onValueChange = { textFieldContents -> emailAddress = textFieldContents },
                        placeholder = { Text("Email Address", color = text_color) },
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
                        textStyle = TextStyle(fontSize = 16.sp, color = text_color),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedTextColor = text_color,
                            containerColor = if (isDarkMode) blue_accent else orange_accent,
                            focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true
                    )
                    TextField(
                        value = birthday,
                        onValueChange = { textFieldContents -> birthday = textFieldContents },
                        placeholder = { Text("Birthday", color = text_color) },
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
                        textStyle = TextStyle(fontSize = 16.sp, color = text_color),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedTextColor = text_color,
                            containerColor = if (isDarkMode) blue_accent else orange_accent,
                            focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true
                    )
                    TextField(
                        value = address,
                        onValueChange = { textFieldContents -> address = textFieldContents },
                        placeholder = { Text("Address", color = text_color) },
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
                        textStyle = TextStyle(fontSize = 16.sp, color = text_color),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedTextColor = text_color,
                            containerColor = if (isDarkMode) blue_accent else orange_accent,
                            focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true
                    )
                    TextField(
                        value = groups,
                        onValueChange = { textFieldContents -> groups = textFieldContents },
                        placeholder = { Text("Groups", color = text_color) },
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
                        textStyle = TextStyle(fontSize = 16.sp, color = text_color),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedTextColor = text_color,
                            containerColor = if (isDarkMode) blue_accent else orange_accent,
                            focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true
                    )
                }
            }
        }
        else {
            Box(
                modifier = Modifier
                    .width(360.dp)
                    .height(720.dp)
                    .background(orange_accent.copy(alpha = 0.75f), shape = MaterialTheme.shapes.medium)
                    .align(Alignment.Center)
            ) {
                // Top Left Button
                Button(
                    onClick = { selectedOptionRemember = "" },
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
                        placeholder = { Text("Picture", color = text_color) },
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
                        textStyle = TextStyle(fontSize = 16.sp, color = text_color),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedTextColor = text_color,
                            containerColor = if (isDarkMode) blue_accent else orange_accent,
                            focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true
                    )
                    TextField(
                        value = nameContact,
                        onValueChange = { textFieldContents -> nameContact = textFieldContents },
                        placeholder = { Text("Name", color = text_color) },
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
                        textStyle = TextStyle(fontSize = 16.sp, color = text_color),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedTextColor = text_color,
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
                        placeholder = { Text("Phone Number", color = text_color) },
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
                        textStyle = TextStyle(fontSize = 16.sp, color = text_color),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedTextColor = text_color,
                            containerColor = if (isDarkMode) blue_accent else orange_accent,
                            focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true
                    )
                    TextField(
                        value = emailAddress,
                        onValueChange = { textFieldContents -> emailAddress = textFieldContents },
                        placeholder = { Text("Email Address", color = text_color) },
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
                        textStyle = TextStyle(fontSize = 16.sp, color = text_color),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedTextColor = text_color,
                            containerColor = if (isDarkMode) blue_accent else orange_accent,
                            focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true
                    )
                    TextField(
                        value = websiteLink,
                        onValueChange = { textFieldContents -> websiteLink = textFieldContents },
                        placeholder = { Text("Website Link", color = text_color) },
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
                        textStyle = TextStyle(fontSize = 16.sp, color = text_color),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedTextColor = text_color,
                            containerColor = if (isDarkMode) blue_accent else orange_accent,
                            focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true
                    )
                    TextField(
                        value = address,
                        onValueChange = { textFieldContents -> address = textFieldContents },
                        placeholder = { Text("Address", color = text_color) },
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
                        textStyle = TextStyle(fontSize = 16.sp, color = text_color),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedTextColor = text_color,
                            containerColor = if (isDarkMode) blue_accent else orange_accent,
                            focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true
                    )
                    TextField(
                        value = groups,
                        onValueChange = { textFieldContents -> groups = textFieldContents },
                        placeholder = { Text("Groups", color = text_color) },
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
                        textStyle = TextStyle(fontSize = 16.sp, color = text_color),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedTextColor = text_color,
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
    NavigationBar(
        containerColor = if (isDarkMode) blue_accent else orange_accent,
        contentColor = if (isDarkMode) blue_selected else orange_selected
    ) {
        // A forEach loop to iterate through all the screens in the app
        screens.forEach { screen ->
            // Navigation Bar Item is a composable used for each item in the bottom navigation bar
            NavigationBarItem(
                label = {
                    Text(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        text = screen.title
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

data class JokeModel(
    val id: Int,
    var question: String,
    var answer : String,
    var answerIsVisible: Boolean
)

