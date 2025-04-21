package com.dylanjohnson.contactsapp.classes

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.dylanjohnson.contactsapp.R
import com.dylanjohnson.contactsapp.data.ContactViewModelInterface
import com.dylanjohnson.contactsapp.models.BusinessModel
import com.dylanjohnson.contactsapp.models.ContactModel
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
fun Contact(viewModel: ContactViewModelInterface, contact: ContactModel, changeVisibility: (id:Int) -> Unit, context: Context, navController: NavHostController) {
    val themeColor = viewModel.chosenTheme.value.getTheme()
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
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(380.dp)
                .height(60.dp)
                .clickable {
                    changeVisibility(contact.getId())
                    //Log.d("Contact Tag", "Contact: $contact")
                }
                .background(
                    accent75,
                    shape = MaterialTheme.shapes.medium
                ),
//            contentAlignment = Alignment.Center // ✅ Center content both horizontally and vertically
        ) {
            Row(
                modifier = Modifier.fillMaxSize(), // 💥 This makes the Row match the Box's size
                verticalAlignment = Alignment.CenterVertically, // ✅ This centers content vertically
                horizontalArrangement = Arrangement.Start // Optional: default is start
            ) {
                if (contact is BusinessModel) {
                    val star = if (contact.getMyOpinionRating() < 2) {
                        Icons.Filled.StarBorder
                    } else if (contact.getMyOpinionRating() <= 3.5f) {
                        Icons.AutoMirrored.Filled.StarHalf
                    } else {
                        Icons.Filled.Star
                    }
                    Box(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(35.dp) // Outer circle size, slightly bigger than icon
//                            .align(Alignment.CenterVertically)
                            .background(
                                color = icons70,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(25.dp),
                            imageVector = star,
                            contentDescription = "Star Image",
                            tint = accent90
                        )
                    }
                }
                else if (contact is PersonModel) {
                    var selectedImageUri by remember(contact) { mutableStateOf(contact.getPictureURL()) }
                    Box(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(35.dp) // Outer circle size, slightly bigger than icon
                            //                            .align(Alignment.CenterVertically)
                            .background(
                                color = icons70,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (selectedImageUri != null) {
                            Image(
                                painter = rememberAsyncImagePainter(model = selectedImageUri),
                                contentDescription = "Selected Photo",
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(
                                modifier = Modifier.size(22.dp), // Slightly smaller than outer circle
                                imageVector = Icons.Filled.PhotoCamera,
                                contentDescription = "Contact Icon",
                                tint = accent90
                            )
                        }
                    }
                }
                else {
                    Box(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(35.dp) // Outer circle size, slightly bigger than icon
//                            .align(Alignment.CenterVertically)
                            .background(
                                color = icons70,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(22.dp), // Slightly smaller than outer circle
                            imageVector = Icons.Filled.PhotoCamera,
                            contentDescription = "Contact Icon",
                            tint = accent90
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    modifier = Modifier
                        .padding(2.dp)
                        .clickable {
                            changeVisibility(contact.getId())
                            //Log.d("Contact Tag", "Contact: $contact")
                        },
                    color = themeTextColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 5.em,
                    lineHeight = 1.em,
                    textAlign = TextAlign.Center,
                    text = contact.getName()
                )
            }
            if (contact is PersonModel) {
                if (contact.getFavorite()) {
                    Icon(
                        imageVector = Icons.Filled.Favorite, // or whatever icon you want
                        contentDescription = "Favorite Icon",
                        tint = icons60,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 18.dp)
                            .size(24.dp)
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(5.dp))

    if (contact.getExpanded()) {
        navController.navigate("details/${contact.getId()}")
    }
//    Divider(color = Color.Black)
}
