package com.dylanjohnson.contactsapp.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.dylanjohnson.contactsapp.R
import com.dylanjohnson.contactsapp.data.ContactViewModelInterface
import com.dylanjohnson.contactsapp.models.BusinessModel
import com.dylanjohnson.contactsapp.models.PersonModel
import com.dylanjohnson.contactsapp.ui.theme.blue_accent
import com.dylanjohnson.contactsapp.ui.theme.blue_icons
import com.dylanjohnson.contactsapp.ui.theme.dark_text_color
import com.dylanjohnson.contactsapp.ui.theme.light_text_color
import com.dylanjohnson.contactsapp.ui.theme.orange_accent
import com.dylanjohnson.contactsapp.ui.theme.orange_icons
import com.dylanjohnson.contactsapp.classes.Contact
import com.dylanjohnson.contactsapp.models.ContactModel
import com.dylanjohnson.contactsapp.ui.theme.black_accent
import com.dylanjohnson.contactsapp.ui.theme.black_icons
import com.dylanjohnson.contactsapp.ui.theme.green_accent
import com.dylanjohnson.contactsapp.ui.theme.green_icons
import com.dylanjohnson.contactsapp.ui.theme.light_text
import com.dylanjohnson.contactsapp.ui.theme.pink_accent
import com.dylanjohnson.contactsapp.ui.theme.pink_icons
import com.dylanjohnson.contactsapp.ui.theme.purple_accent
import com.dylanjohnson.contactsapp.ui.theme.purple_icons
import com.dylanjohnson.contactsapp.ui.theme.red_accent
import com.dylanjohnson.contactsapp.ui.theme.red_icons
import com.dylanjohnson.contactsapp.ui.theme.white_accent
import com.dylanjohnson.contactsapp.ui.theme.white_icons
import com.dylanjohnson.contactsapp.ui.theme.yellow_accent
import com.dylanjohnson.contactsapp.ui.theme.yellow_icons
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsScreen(viewModel: ContactViewModelInterface, navController: NavHostController) {
    val contacts by remember {
        derivedStateOf {
            viewModel.contactsList
                .filterIsInstance<PersonModel>()
                .toMutableStateList()
        }
    }
    val groups = mutableSetOf<String>()

    for (contact in contacts) {
        if (contact.getGroup() != "") {
            groups.add(contact.getGroup())
        } else {
            groups.add("Undefined")
        }
    }
    val sortedGroups = groups.sortedWith(compareBy { group ->
        if (group.isBlank()) "Undefined" else group
    }).sortedWith(compareBy { group ->
        group == "Undefined" // Sort by whether the group is "Undefined" (true goes to the end)
    })

//    Log.d("GROUP INFO", sortedGroups.toString())
    val themeColor = viewModel.chosenTheme.value.getTheme()
    val context = LocalContext.current
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
    val systemUiController = rememberSystemUiController()
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

    val themeTextColor = if (themeColor == "blue" || themeColor == "pink" || themeColor == "red" || themeColor == "yellow" || themeColor == "purple") {
        dark_text_color
    } else if (themeColor == "black") {
        light_text
    } else {
        light_text_color
    }
    val accent = if (themeColor == "blue") {
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
    }
    val icons = if (themeColor == "blue") {
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
    }
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
        val filteredGroups = remember(sortedGroups, searchString) {
            sortedGroups.filter { it.contains(searchString, ignoreCase = true) }
        }
        val contactCount = filteredGroups.size
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp) // adjust this value as needed
        ) {
            LazyColumn() {
                items(sortedGroups.size) { index ->
                    if (sortedGroups[index].contains(searchString, ignoreCase = true)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.padding(10.dp),
                                color = themeTextColor,
                                fontWeight = FontWeight.Bold,
                                fontSize = 4.em,
                                lineHeight = 1.em,
                                textAlign = TextAlign.Start, // Align text to the start
                                text = sortedGroups[index] // Correctly set the text here
                            )
                            Spacer(modifier = Modifier.width(4.dp)) // Add a small gap
                            Divider(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f), // Line takes remaining space
                                color = themeTextColor,
                                thickness = 1.6f.dp
                            )
                        }
                        for (contact in contacts) {
                            val contactIndex =
                                contacts.indexOf(contact) // Get the current contact's index
                            if (contact.getGroup() == sortedGroups[index] || (contact.getGroup() == "" && sortedGroups[index] == "Undefined")) {
                                Contact(
                                    viewModel, contact = contacts[contactIndex], {
                                        //Log.d("BUG", contacts[contactIndex].toString())
                                        if (contacts[contactIndex] is PersonModel) {
                                            //Log.d("IMAGE PERSON OLD", contacts[contactIndex].toString())
                                        }
                                        var changedContact = when (contacts[contactIndex]) {
                                            is PersonModel -> (contacts[contactIndex] as PersonModel).copy(
                                                isExpanded = !contacts[contactIndex].getExpanded()
                                            )

                                            else -> contacts[contactIndex].copy(isExpanded = !contacts[contactIndex].getExpanded()) // Handle ContactModel or other subclasses
                                        }
                                        if (changedContact is PersonModel) {
                                            //Log.d("IMAGE PERSON NEW", changedContact.toString())
                                        }

                                        // replace the joke item at position number "it"
                                        contacts[contactIndex] = changedContact
                                    },
                                    context = LocalContext.current,
                                    navController
                                )
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(70.dp))
                }
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
                    text = "No Groups matching: \"$searchString\""
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
                placeholder = {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(start = 2.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        BasicText(
                            text = "Search",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = themeTextColor
                            )
                        )
                    }
                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(30.dp),
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = icons
                    )
                },
                shape = RoundedCornerShape(32.dp),
                modifier = Modifier
                    .width(360.dp)
                    .height(50.dp)
//                    .defaultMinSize(minHeight = 50.dp)
                    .padding(1.dp), // Adjust padding instead of height
                textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp, color = themeTextColor),
                colors = TextFieldDefaults.textFieldColors(
                    focusedTextColor = themeTextColor,
                    containerColor = accent,
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
