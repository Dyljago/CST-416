package com.dylanjohnson.contactsapp.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.EditRoad
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.KeyboardBackspace
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.EmojiObjects
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBusinessContactScreen(viewModel: ContactViewModelInterface, navController: NavHostController) {
    val themeColor = viewModel.chosenTheme.value.getTheme()
    val context = LocalContext.current
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
    val icons80 = if (themeColor == "blue") {
        blue_icons.copy(alpha = 0.80f)
    } else if (themeColor == "green") {
        green_icons.copy(alpha = 0.8f)
    } else if (themeColor == "purple") {
        purple_icons.copy(alpha = 0.8f)
    } else if (themeColor == "yellow") {
        yellow_icons.copy(alpha = 0.8f)
    } else if (themeColor == "red") {
        red_icons.copy(alpha = 0.8f)
    } else if (themeColor == "pink") {
        pink_icons.copy(alpha = 0.8f)
    } else if (themeColor == "black") {
        black_icons.copy(alpha = 0.8f)
    } else if (themeColor == "white") {
        white_icons.copy(alpha = 0.8f)
    } else {
        orange_icons.copy(alpha = 0.80f)
    }
    val icons90 = if (themeColor == "blue") {
        blue_icons.copy(alpha = 0.90f)
    } else if (themeColor == "green") {
        green_icons.copy(alpha = 0.90f)
    } else if (themeColor == "purple") {
        purple_icons.copy(alpha = 0.9f)
    } else if (themeColor == "yellow") {
        yellow_icons.copy(alpha = 0.9f)
    } else if (themeColor == "red") {
        red_icons.copy(alpha = 0.9f)
    } else if (themeColor == "pink") {
        pink_icons.copy(alpha = 0.9f)
    } else if (themeColor == "black") {
        black_icons.copy(alpha = 0.9f)
    } else if (themeColor == "white") {
        white_icons.copy(alpha = 0.9f)
    } else {
        orange_icons.copy(alpha = 0.90f)
    }
    val contact = remember { BusinessModel() }
    var ratingState by remember { mutableStateOf(contact.getMyOpinionRating()) }
    var daysOfBusiness by remember { mutableStateOf(contact.getDaysOpen().toMutableList()) }
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
                .width(360.dp)
                .height(720.dp)
                .background(
                    accent75,
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
                    colors = ButtonDefaults.buttonColors(containerColor = icons80)
                ) {
                    Icon(
                        modifier = Modifier
                            .size(50.dp),
                        imageVector = Icons.Filled.KeyboardBackspace,
                        contentDescription = "Back Arrow",
                        tint = accent90
                    )
                }

                Text(
                    text = "Return",
                    color = themeTextColor,
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
                    onClick = {
                        if (!contact.getName().isNullOrBlank()) {
                            contact.setID(viewModel.contactsList[viewModel.contactsList.size-1].getId()+1)
                            viewModel.addContact(contact)
                            navController.navigate("contacts")
                        } else {
                            val toast = Toast.makeText(context, "NO NAME ENTERED", Toast.LENGTH_SHORT)
                            toast.show()
                        }
                    },
                    modifier = Modifier
                        .padding(end = 20.dp, top = 20.dp)
                        .size(50.dp),
                    contentPadding = PaddingValues(4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = icons80)
                ) {
                    Icon(
                        modifier = Modifier
                            .scale(scaleX = -1f, scaleY = 1f)
                            .size(35.dp),
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add Symbol",
                        tint = accent90
                    )
                }

                Text(
                    text = "Add",
                    color = themeTextColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 3.em,
                    modifier = Modifier.padding(end = 31.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 100.dp)
                ) {
                    for (i in 1..5) {
                        // Use the ratingState instead of directly accessing contact.getMyOpinionRating()
                        val star = if (ratingState >= i) {
                            Icons.Filled.Star
                        } else if (ratingState >= i - 0.5f) {
                            Icons.AutoMirrored.Filled.StarHalf
                        } else {
                            Icons.Filled.StarBorder
                        }

                        Icon(
                            modifier = Modifier
                                .size(60.dp)
                                .clickable {
                                    // Update both the contact model and our state variable
                                    if (contact.getMyOpinionRating() != i.toFloat()) {
                                        contact.setMyOpinionRating(i.toFloat())
                                        ratingState = i.toFloat()
                                    } else {
                                        contact.setMyOpinionRating((i-0.5).toFloat())
                                        ratingState = (i-0.5).toFloat()
                                    }
                                    Log.d("OPINION", contact.getMyOpinionRating().toString())
                                },
                            imageVector = star,
                            contentDescription = "Star $i",
                            tint = icons90
                        )
                    }
                }
            }

            var nameContact by remember { mutableStateOf("") }
            var phoneNum by remember { mutableStateOf(TextFieldValue("")) }
            var emailAddress by remember { mutableStateOf("") }
            var websiteLink by remember { mutableStateOf("") }
            var address by remember { mutableStateOf("") }

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(165.dp))
                TextField(
                    value = nameContact,
                    onValueChange = { textFieldContents ->
                        nameContact = textFieldContents
                        contact.setName(textFieldContents)
                    },
                    placeholder = { Text("Name", color = themeTextColor) },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(30.dp),
                            imageVector = Icons.Filled.Abc,
                            contentDescription = "Letters Icon",
                            tint = icons
                        )
                    },
                    shape = RoundedCornerShape(64.dp),
                    modifier = Modifier
                        .width(320.dp)
                        .padding(2.dp)
                        .then(
                            Modifier.border(
                                width = 1.dp,
                                color = icons80,
                                shape = RoundedCornerShape(30.dp)
                            )
                        ), // Adjust padding instead of height
                    textStyle = TextStyle(fontSize = 16.sp, color = themeTextColor),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedTextColor = themeTextColor,
                        containerColor = accent,
                        focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
                TextField(
                    value = phoneNum,
                    onValueChange = { textFieldContents ->
                        val allowedPhoneCharacters = Regex("^[0-9-]+$") // For digits and hyphens
                        if (textFieldContents.text.isEmpty() || (textFieldContents.text.matches(allowedPhoneCharacters) && textFieldContents.text.length <= 12)) {
                            phoneNum = textFieldContents

                            val newNum = textFieldContents.text.filter { it.isDigit() } // Extract only digits

                            var formattedPhoneNumber = ""
                            for (i in newNum.indices) {
                                formattedPhoneNumber += newNum[i]
                                if ((i == 2 || i == 5) && i < newNum.length - 1) {
                                    formattedPhoneNumber += "-"
                                }
                            }
                            phoneNum = TextFieldValue(
                                text = formattedPhoneNumber,
                                selection = TextRange(formattedPhoneNumber.length)
                            )

                            if (phoneNum.text.length == 12) {
                                contact.setPhoneNum(phoneNum.text)
                            } else {
                                contact.setPhoneNum("")
                            }
                        }
                    },
                    placeholder = { Text("Phone Number", color = themeTextColor) },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(30.dp),
                            imageVector = Icons.Filled.Phone,
                            contentDescription = "Phone Icon",
                            tint = icons
                        )
                    },
                    shape = RoundedCornerShape(64.dp),
                    modifier = Modifier
                        .width(320.dp)
                        .padding(2.dp)
                        .then(
                            Modifier.border(
                                width = 1.dp,
                                color = icons80,
                                shape = RoundedCornerShape(30.dp)
                            )
                        ), // Adjust padding instead of height
                    textStyle = TextStyle(fontSize = 16.sp, color = themeTextColor),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedTextColor = themeTextColor,
                        containerColor = accent,
                        focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
                TextField(
                    value = emailAddress,
                    onValueChange = { textFieldContents ->
                        emailAddress = textFieldContents
                        contact.setEmail(textFieldContents)
                    },
                    placeholder = { Text("Email Address", color = themeTextColor) },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(30.dp),
                            imageVector = Icons.Filled.Mail,
                            contentDescription = "Mail Icon",
                            tint = icons
                        )
                    },
                    shape = RoundedCornerShape(64.dp),
                    modifier = Modifier
                        .width(320.dp)
                        .padding(2.dp)
                        .then(
                            Modifier.border(
                                width = 1.dp,
                                color = icons80,
                                shape = RoundedCornerShape(30.dp)
                            )
                        ), // Adjust padding instead of height
                    textStyle = TextStyle(fontSize = 16.sp, color = themeTextColor),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedTextColor = themeTextColor,
                        containerColor = accent,
                        focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
                TextField(
                    value = websiteLink,
                    onValueChange = { textFieldContents ->
                        websiteLink = textFieldContents
                        contact.setWebURL(websiteLink)
                    },
                    placeholder = { Text("Website", color = themeTextColor) },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(30.dp),
                            imageVector = Icons.Filled.Computer,
                            contentDescription = "Website Icon",
                            tint = icons
                        )
                    },
                    shape = RoundedCornerShape(64.dp),
                    modifier = Modifier
                        .width(320.dp)
                        .padding(2.dp)
                        .then(
                            Modifier.border(
                                width = 1.dp,
                                color = icons80,
                                shape = RoundedCornerShape(30.dp)
                            )
                        ), // Adjust padding instead of height
                    textStyle = TextStyle(fontSize = 16.sp, color = themeTextColor),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedTextColor = themeTextColor,
                        containerColor = accent,
                        focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
                TextField(
                    value = address,
                    onValueChange = { textFieldContents ->
                        address = textFieldContents
                        contact.setAddress(textFieldContents)
                    },
                    placeholder = { Text("Address", color = themeTextColor) },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(30.dp),
                            imageVector = Icons.Filled.EditRoad,
                            contentDescription = "Phone Icon",
                            tint = icons
                        )
                    },
                    shape = RoundedCornerShape(64.dp),
                    modifier = Modifier
                        .width(320.dp)
                        .padding(2.dp)
                        .then(
                            Modifier.border(
                                width = 1.dp,
                                color = icons80,
                                shape = RoundedCornerShape(30.dp)
                            )
                        ), // Adjust padding instead of height
                    textStyle = TextStyle(fontSize = 16.sp, color = themeTextColor),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedTextColor = themeTextColor,
                        containerColor = accent,
                        focusedIndicatorColor = Color.Transparent, // Remove bottom bar
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Days Open",
                    color = themeTextColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 4.em
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp), // reduce spacing between columns
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(IntrinsicSize.Min)
                    ) {
                        Checkbox(
                            checked = daysOfBusiness[0],
                            onCheckedChange = {
                                val updatedList = daysOfBusiness.toMutableList()
                                updatedList[0] = it
                                daysOfBusiness = updatedList
                                contact.setDaysOpen(0, daysOfBusiness[0])
                            },
                            modifier = Modifier
                                .size(36.dp)
                                .scale(1.25f),
                            colors = CheckboxColors(
                                uncheckedBorderColor = icons,
                                checkedBorderColor = icons,
                                checkedBoxColor = icons,
                                uncheckedBoxColor = accent,
                                checkedCheckmarkColor = accent,
                                uncheckedCheckmarkColor = icons,
                                disabledCheckedBoxColor = themeTextColor,
                                disabledUncheckedBoxColor = themeTextColor,
                                disabledIndeterminateBoxColor = themeTextColor,
                                disabledBorderColor = themeTextColor,
                                disabledUncheckedBorderColor = themeTextColor,
                                disabledIndeterminateBorderColor = themeTextColor
                            )
                        )
                        Text(
                            text = "Mon",
                            color = themeTextColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 3.em,
                            modifier = Modifier.padding(0.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.width(IntrinsicSize.Min),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Checkbox(
                            checked = daysOfBusiness[1],
                            onCheckedChange = {
                                val updatedList = daysOfBusiness.toMutableList()
                                updatedList[1] = it
                                daysOfBusiness = updatedList
                                contact.setDaysOpen(1, daysOfBusiness[1])
                            },
                            modifier = Modifier
                                .size(36.dp)
                                .scale(1.25f),
                            colors = CheckboxColors(
                                uncheckedBorderColor = icons,
                                checkedBorderColor = icons,
                                checkedBoxColor = icons,
                                uncheckedBoxColor = accent,
                                checkedCheckmarkColor = accent,
                                uncheckedCheckmarkColor = icons,
                                disabledCheckedBoxColor = themeTextColor,
                                disabledUncheckedBoxColor = themeTextColor,
                                disabledIndeterminateBoxColor = themeTextColor,
                                disabledBorderColor = themeTextColor,
                                disabledUncheckedBorderColor = themeTextColor,
                                disabledIndeterminateBorderColor = themeTextColor
                            )
                        )
                        Text(
                            text = "Tue",
                            color = themeTextColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 3.em,
                            modifier = Modifier.padding(0.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.width(IntrinsicSize.Min),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Checkbox(
                            checked = daysOfBusiness[2],
                            onCheckedChange = {
                                val updatedList = daysOfBusiness.toMutableList()
                                updatedList[2] = it
                                daysOfBusiness = updatedList
                                contact.setDaysOpen(2, daysOfBusiness[2])
                            },
                            modifier = Modifier
                                .size(36.dp)
                                .scale(1.25f),
                            colors = CheckboxColors(
                                uncheckedBorderColor = icons,
                                checkedBorderColor = icons,
                                checkedBoxColor = icons,
                                uncheckedBoxColor = accent,
                                checkedCheckmarkColor = accent,
                                uncheckedCheckmarkColor = icons,
                                disabledCheckedBoxColor = themeTextColor,
                                disabledUncheckedBoxColor = themeTextColor,
                                disabledIndeterminateBoxColor = themeTextColor,
                                disabledBorderColor = themeTextColor,
                                disabledUncheckedBorderColor = themeTextColor,
                                disabledIndeterminateBorderColor = themeTextColor
                            )
                        )
                        Text(
                            text = "Wed",
                            color = themeTextColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 3.em,
                            modifier = Modifier.padding(0.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.width(IntrinsicSize.Min),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Checkbox(
                            checked = daysOfBusiness[3],
                            onCheckedChange = {
                                val updatedList = daysOfBusiness.toMutableList()
                                updatedList[3] = it
                                daysOfBusiness = updatedList
                                contact.setDaysOpen(3, daysOfBusiness[3])
                            },
                            modifier = Modifier
                                .size(36.dp)
                                .scale(1.25f),
                            colors = CheckboxColors(
                                uncheckedBorderColor = icons,
                                checkedBorderColor = icons,
                                checkedBoxColor = icons,
                                uncheckedBoxColor = accent,
                                checkedCheckmarkColor = accent,
                                uncheckedCheckmarkColor = icons,
                                disabledCheckedBoxColor = themeTextColor,
                                disabledUncheckedBoxColor = themeTextColor,
                                disabledIndeterminateBoxColor = themeTextColor,
                                disabledBorderColor = themeTextColor,
                                disabledUncheckedBorderColor = themeTextColor,
                                disabledIndeterminateBorderColor = themeTextColor
                            )
                        )
                        Text(
                            text = "Thu",
                            color = themeTextColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 3.em,
                            modifier = Modifier.padding(0.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.width(IntrinsicSize.Min),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Checkbox(
                            checked = daysOfBusiness[4],
                            onCheckedChange = {
                                val updatedList = daysOfBusiness.toMutableList()
                                updatedList[4] = it
                                daysOfBusiness = updatedList
                                contact.setDaysOpen(4, daysOfBusiness[4])
                            },
                            modifier = Modifier
                                .size(36.dp)
                                .scale(1.25f),
                            colors = CheckboxColors(
                                uncheckedBorderColor = icons,
                                checkedBorderColor = icons,
                                checkedBoxColor = icons,
                                uncheckedBoxColor = accent,
                                checkedCheckmarkColor = accent,
                                uncheckedCheckmarkColor = icons,
                                disabledCheckedBoxColor = themeTextColor,
                                disabledUncheckedBoxColor = themeTextColor,
                                disabledIndeterminateBoxColor = themeTextColor,
                                disabledBorderColor = themeTextColor,
                                disabledUncheckedBorderColor = themeTextColor,
                                disabledIndeterminateBorderColor = themeTextColor
                            )
                        )
                        Text(
                            text = "Fri",
                            color = themeTextColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 3.em,
                            modifier = Modifier.padding(0.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.width(IntrinsicSize.Min),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Checkbox(
                            checked = daysOfBusiness[5],
                            onCheckedChange = {
                                val updatedList = daysOfBusiness.toMutableList()
                                updatedList[5] = it
                                daysOfBusiness = updatedList
                                contact.setDaysOpen(5, daysOfBusiness[5])
                            },
                            modifier = Modifier
                                .size(36.dp)
                                .scale(1.25f),
                            colors = CheckboxColors(
                                uncheckedBorderColor = icons,
                                checkedBorderColor = icons,
                                checkedBoxColor = icons,
                                uncheckedBoxColor = accent,
                                checkedCheckmarkColor = accent,
                                uncheckedCheckmarkColor = icons,
                                disabledCheckedBoxColor = themeTextColor,
                                disabledUncheckedBoxColor = themeTextColor,
                                disabledIndeterminateBoxColor = themeTextColor,
                                disabledBorderColor = themeTextColor,
                                disabledUncheckedBorderColor = themeTextColor,
                                disabledIndeterminateBorderColor = themeTextColor
                            )
                        )
                        Text(
                            text = "Sat",
                            color = themeTextColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 3.em,
                            modifier = Modifier.padding(0.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.width(IntrinsicSize.Min),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Checkbox(
                            checked = daysOfBusiness[6],
                            onCheckedChange = {
                                val updatedList = daysOfBusiness.toMutableList()
                                updatedList[6] = it
                                daysOfBusiness = updatedList
                                contact.setDaysOpen(6, daysOfBusiness[6])
                            },
                            modifier = Modifier
                                .size(36.dp)
                                .scale(1.25f),
                            colors = CheckboxColors(
                                uncheckedBorderColor = icons,
                                checkedBorderColor = icons,
                                checkedBoxColor = icons,
                                uncheckedBoxColor = accent,
                                checkedCheckmarkColor = accent,
                                uncheckedCheckmarkColor = icons,
                                disabledCheckedBoxColor = themeTextColor,
                                disabledUncheckedBoxColor = themeTextColor,
                                disabledIndeterminateBoxColor = themeTextColor,
                                disabledBorderColor = themeTextColor,
                                disabledUncheckedBorderColor = themeTextColor,
                                disabledIndeterminateBorderColor = themeTextColor
                            )
                        )
                        Text(
                            text = "Sun",
                            color = themeTextColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 3.em,
                            modifier = Modifier.padding(0.dp)
                        )
                    }
                }
            }
        }
    }
}