package com.dylanjohnson.contactsapp.screens

import android.util.Log
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.dylanjohnson.contactsapp.R
import com.dylanjohnson.contactsapp.data.ContactViewModelInterface
import com.dylanjohnson.contactsapp.models.BusinessModel
import com.dylanjohnson.contactsapp.models.PersonModel
import com.dylanjohnson.contactsapp.ui.theme.black_accent
import com.dylanjohnson.contactsapp.ui.theme.black_icons
import com.dylanjohnson.contactsapp.ui.theme.blue_accent
import com.dylanjohnson.contactsapp.ui.theme.blue_icons
import com.dylanjohnson.contactsapp.ui.theme.dark_text_color
import com.dylanjohnson.contactsapp.ui.theme.green_accent
import com.dylanjohnson.contactsapp.ui.theme.green_icons
import com.dylanjohnson.contactsapp.ui.theme.light_text
import com.dylanjohnson.contactsapp.ui.theme.light_text_color
import com.dylanjohnson.contactsapp.ui.theme.orange_accent
import com.dylanjohnson.contactsapp.ui.theme.orange_icons
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

@Composable
fun SettingsScreen(viewModel: ContactViewModelInterface, navController: NavHostController) {
    val context = LocalContext.current
    val themeColor = viewModel.chosenTheme.value.getTheme()
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
    val themeTextColor = if (themeColor == "blue" || themeColor == "pink" || themeColor == "red" || themeColor == "yellow" || themeColor == "purple") {
        dark_text_color
    } else if (themeColor == "black") {
        light_text
    } else {
        light_text_color
    }
    val accent90 = if (themeColor == "blue") {
        blue_accent.copy(alpha = 0.90f)
    } else if (themeColor == "green") {
        green_accent.copy(alpha = 0.9f)
    } else if (themeColor == "purple") {
        purple_accent.copy(alpha = 0.9f)
    } else if (themeColor == "yellow") {
        yellow_accent.copy(alpha = 0.9f)
    } else if (themeColor == "red") {
        red_accent.copy(alpha = 0.9f)
    } else if (themeColor == "pink") {
        pink_accent.copy(alpha = 0.9f)
    } else if (themeColor == "black") {
        black_accent.copy(alpha = 0.9f)
    } else if (themeColor == "white") {
        white_accent.copy(alpha = 0.9f)
    } else {
        orange_accent.copy(alpha = 0.90f)
    }
    val accent75 = if (themeColor == "blue") {
        blue_accent.copy(alpha = 0.75f)
    } else if (themeColor == "green") {
        green_accent.copy(alpha = 0.75f)
    } else if (themeColor == "purple") {
        purple_accent.copy(alpha = 0.75f)
    } else if (themeColor == "yellow") {
        yellow_accent.copy(alpha = 0.75f)
    } else if (themeColor == "red") {
        red_accent.copy(alpha = 0.75f)
    } else if (themeColor == "pink") {
        pink_accent.copy(alpha = 0.75f)
    } else if (themeColor == "black") {
        black_accent.copy(alpha = 0.75f)
    } else if (themeColor == "white") {
        white_accent.copy(alpha = 0.75f)
    } else {
        orange_accent.copy(alpha = 0.75f)
    }
    val icons70 = if (themeColor == "blue") {
        blue_icons.copy(alpha = 0.70f)
    } else if (themeColor == "green") {
        green_icons.copy(alpha = 0.70f)
    } else if (themeColor == "purple") {
        purple_icons.copy(alpha = 0.7f)
    } else if (themeColor == "yellow") {
        yellow_icons.copy(alpha = 0.7f)
    } else if (themeColor == "red") {
        red_icons.copy(alpha = 0.7f)
    } else if (themeColor == "pink") {
        pink_icons.copy(alpha = 0.7f)
    } else if (themeColor == "black") {
        black_icons.copy(alpha = 0.7f)
    } else if (themeColor == "white") {
        white_icons.copy(alpha = 0.7f)
    } else {
        orange_icons.copy(alpha = 0.7f)
    }
    val icons60 = if (themeColor == "blue") {
        blue_icons.copy(alpha = 0.60f)
    } else if (themeColor == "green") {
        green_icons.copy(alpha = 0.60f)
    } else if (themeColor == "purple") {
        purple_icons.copy(alpha = 0.6f)
    } else if (themeColor == "yellow") {
        yellow_icons.copy(alpha = 0.6f)
    } else if (themeColor == "red") {
        red_icons.copy(alpha = 0.6f)
    } else if (themeColor == "pink") {
        pink_icons.copy(alpha = 0.6f)
    } else if (themeColor == "black") {
        black_icons.copy(alpha = 0.6f)
    } else if (themeColor == "white") {
        white_icons.copy(alpha = 0.6f)
    } else {
        orange_icons.copy(alpha = 0.60f)
    }
    // Apply the system UI colors
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color(statusBarColor),
            darkIcons = true // This replaces SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        )
    }
    // State to control the visibility of the dropdown menu
    val (expanded, setExpanded) = remember { mutableStateOf(false) }
    val (expandedTheme, setExpandedTheme) = remember { mutableStateOf(false) }
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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp) // adjust this value as needed
        ) {
            LazyColumn() {
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp),
                        color = themeTextColor,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        fontSize = 10.em,
                        lineHeight = 1.em,
                        textAlign = TextAlign.Center,
                        text = "Settings"
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .width(380.dp)
                                .height(70.dp)
                                .clickable {
                                    setExpanded(true)
                                }
                                .background(
                                    accent75,
                                    shape = MaterialTheme.shapes.medium
                                )
                                .then(
                                    Modifier.border(
                                        width = 1.dp,
                                        color = icons70,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                ),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(), // 💥 This makes the Row match the Box's size
                                verticalAlignment = Alignment.CenterVertically, // ✅ This centers content vertically
                                horizontalArrangement = Arrangement.Start // Optional: default is start
                            ) {
                                Spacer(modifier = Modifier.width(20.dp))
                                Text(
                                    modifier = Modifier.padding(2.dp),
                                    color = themeTextColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 7.em,
                                    lineHeight = 1.em,
                                    textAlign = TextAlign.Center,
                                    text = "Add Contact"
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .padding(end = 18.dp)
                                    .size(45.dp)
                                    .background(
                                        color = icons70,
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    modifier = Modifier.size(64.dp),
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "Add Icon",
                                    tint = accent90
                                )

                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { setExpanded(false) },
                                    modifier = Modifier.background(accent90)
                                ) {
                                    DropdownMenuItem(
                                        onClick = {
                                            navController.navigate("addPerson")
                                            setExpanded(false)
                                        },
                                        text = {
                                            Text(
                                                text = "Add Person",
                                                modifier = Modifier.padding(2.dp),
                                                color = themeTextColor,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 4.em,
                                                lineHeight = 1.em,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    )
                                    DropdownMenuItem(
                                        onClick = {
                                            navController.navigate("addBusiness")
                                            setExpanded(false)
                                        },
                                        text = {
                                            Text(
                                                text = "Add Business",
                                                modifier = Modifier.padding(2.dp),
                                                color = themeTextColor,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 4.em,
                                                lineHeight = 1.em,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                }
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .width(380.dp)
                                .height(70.dp)
                                .clickable {
                                    navController.navigate("removeContact")
                                }
                                .background(
                                    accent75,
                                    shape = MaterialTheme.shapes.medium
                                )
                                .then(
                                    Modifier.border(
                                        width = 1.dp,
                                        color = icons70,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                ),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(), // 💥 This makes the Row match the Box's size
                                verticalAlignment = Alignment.CenterVertically, // ✅ This centers content vertically
                                horizontalArrangement = Arrangement.Start // Optional: default is start
                            ) {
                                Spacer(modifier = Modifier.width(20.dp))
                                Text(
                                    modifier = Modifier.padding(2.dp),
                                    color = themeTextColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 7.em,
                                    lineHeight = 1.em,
                                    textAlign = TextAlign.Center,
                                    text = "Remove Contact"
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .padding(end = 18.dp)
                                    .size(45.dp)
                                    .background(
                                        color = icons70,
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    modifier = Modifier.size(64.dp),
                                    imageVector = Icons.Filled.Remove,
                                    contentDescription = "Remove Icon",
                                    tint = accent90
                                )
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                }
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .width(380.dp)
                                .height(70.dp)
                                .clickable {
                                    setExpandedTheme(true)
                                }
                                .background(
                                    accent75,
                                    shape = MaterialTheme.shapes.medium
                                )
                                .then(
                                    Modifier.border(
                                        width = 1.dp,
                                        color = icons70,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                ),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(), // 💥 This makes the Row match the Box's size
                                verticalAlignment = Alignment.CenterVertically, // ✅ This centers content vertically
                                horizontalArrangement = Arrangement.Start // Optional: default is start
                            ) {
                                Spacer(modifier = Modifier.width(20.dp))
                                Text(
                                    modifier = Modifier.padding(2.dp),
                                    color = themeTextColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 7.em,
                                    lineHeight = 1.em,
                                    textAlign = TextAlign.Center,
                                    text = "Change Theme"
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .padding(end = 18.dp)
                                    .size(45.dp)
                                    .background(
                                        color = icons70,
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    modifier = Modifier.size(35.dp),
                                    imageVector = Icons.Filled.ColorLens,
                                    contentDescription = "Theme Icon",
                                    tint = accent90
                                )
                                DropdownMenu(
                                    expanded = expandedTheme,
                                    onDismissRequest = { setExpandedTheme(false) },
                                    modifier = Modifier.background(accent90)
                                ) {
                                    DropdownMenuItem(
                                        onClick = {
                                            viewModel.chosenTheme.value.setTheme("orange")
                                            viewModel.saveTheme()
                                            navController.navigate("settings")
                                            setExpandedTheme(false)
                                        },
                                        text = {
                                            Text(
                                                text = "Orange",
                                                modifier = Modifier.padding(2.dp),
                                                color = themeTextColor,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 4.em,
                                                lineHeight = 1.em,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    )

                                    DropdownMenuItem(
                                        onClick = {
                                            viewModel.chosenTheme.value.setTheme("blue")
                                            viewModel.saveTheme()
                                            navController.navigate("settings")
                                            setExpandedTheme(false)
                                        },
                                        text = {
                                            Text(
                                                text = "Blue",
                                                modifier = Modifier.padding(2.dp),
                                                color = themeTextColor,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 4.em,
                                                lineHeight = 1.em,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    )

                                    DropdownMenuItem(
                                        onClick = {
                                            viewModel.chosenTheme.value.setTheme("green")
                                            viewModel.saveTheme()
                                            navController.navigate("settings")
                                            setExpandedTheme(false)
                                        },
                                        text = {
                                            Text(
                                                text = "Green",
                                                modifier = Modifier.padding(2.dp),
                                                color = themeTextColor,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 4.em,
                                                lineHeight = 1.em,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    )

                                    DropdownMenuItem(
                                        onClick = {
                                            viewModel.chosenTheme.value.setTheme("purple")
                                            viewModel.saveTheme()
                                            navController.navigate("settings")
                                            setExpandedTheme(false)
                                        },
                                        text = {
                                            Text(
                                                text = "Purple",
                                                modifier = Modifier.padding(2.dp),
                                                color = themeTextColor,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 4.em,
                                                lineHeight = 1.em,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    )

                                    DropdownMenuItem(
                                        onClick = {
                                            viewModel.chosenTheme.value.setTheme("yellow")
                                            viewModel.saveTheme()
                                            navController.navigate("settings")
                                            setExpandedTheme(false)
                                        },
                                        text = {
                                            Text(
                                                text = "Yellow",
                                                modifier = Modifier.padding(2.dp),
                                                color = themeTextColor,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 4.em,
                                                lineHeight = 1.em,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    )

                                    DropdownMenuItem(
                                        onClick = {
                                            viewModel.chosenTheme.value.setTheme("red")
                                            viewModel.saveTheme()
                                            navController.navigate("settings")
                                            setExpandedTheme(false)
                                        },
                                        text = {
                                            Text(
                                                text = "Red",
                                                modifier = Modifier.padding(2.dp),
                                                color = themeTextColor,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 4.em,
                                                lineHeight = 1.em,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    )

                                    DropdownMenuItem(
                                        onClick = {
                                            viewModel.chosenTheme.value.setTheme("pink")
                                            viewModel.saveTheme()
                                            navController.navigate("settings")
                                            setExpandedTheme(false)
                                        },
                                        text = {
                                            Text(
                                                text = "Pink",
                                                modifier = Modifier.padding(2.dp),
                                                color = themeTextColor,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 4.em,
                                                lineHeight = 1.em,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    )

                                    DropdownMenuItem(
                                        onClick = {
                                            viewModel.chosenTheme.value.setTheme("black")
                                            viewModel.saveTheme()
                                            navController.navigate("settings")
                                            setExpandedTheme(false)
                                        },
                                        text = {
                                            Text(
                                                text = "Black",
                                                modifier = Modifier.padding(2.dp),
                                                color = themeTextColor,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 4.em,
                                                lineHeight = 1.em,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    )

                                    DropdownMenuItem(
                                        onClick = {
                                            viewModel.chosenTheme.value.setTheme("white")
                                            viewModel.saveTheme()
                                            navController.navigate("settings")
                                            setExpandedTheme(false)
                                        },
                                        text = {
                                            Text(
                                                text = "White",
                                                modifier = Modifier.padding(2.dp),
                                                color = themeTextColor,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 4.em,
                                                lineHeight = 1.em,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                }
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .width(380.dp)
                                .height(70.dp)
                                .clickable {
                                    viewModel.saveContacts()
                                    viewModel.saveTheme()
                                }
                                .background(
                                    accent75,
                                    shape = MaterialTheme.shapes.medium
                                )
                                .then(
                                    Modifier.border(
                                        width = 1.dp,
                                        color = icons70,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                ),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(), // 💥 This makes the Row match the Box's size
                                verticalAlignment = Alignment.CenterVertically, // ✅ This centers content vertically
                                horizontalArrangement = Arrangement.Start // Optional: default is start
                            ) {
                                Spacer(modifier = Modifier.width(20.dp))
                                Text(
                                    modifier = Modifier.padding(2.dp),
                                    color = themeTextColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 7.em,
                                    lineHeight = 1.em,
                                    textAlign = TextAlign.Center,
                                    text = "Save Contacts"
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .padding(end = 18.dp)
                                    .size(45.dp)
                                    .background(
                                        color = icons70,
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    modifier = Modifier.size(28.dp),
                                    imageVector = Icons.Filled.Save,
                                    contentDescription = "Save Icon",
                                    tint = accent90
                                )
                            }
                        }
                    }
                }
//                item {
//                    Spacer(modifier = Modifier.height(70.dp))
//                }
            }
        }
    }
}
