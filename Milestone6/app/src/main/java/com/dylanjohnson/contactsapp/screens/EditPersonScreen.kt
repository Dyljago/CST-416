package com.dylanjohnson.contactsapp.screens

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.EditRoad
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardBackspace
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.dylanjohnson.contactsapp.R
import com.dylanjohnson.contactsapp.data.ContactViewModelInterface
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPersonScreen(contact: PersonModel, viewModel: ContactViewModelInterface, navController: NavHostController) {
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
    val accent80 = if (themeColor == "blue") {
        blue_accent.copy(alpha = 0.80f)
    } else if (themeColor == "green") {
        green_accent.copy(alpha = 0.8f)
    } else if (themeColor == "purple") {
        purple_accent.copy(alpha = 0.8f)
    } else if (themeColor == "yellow") {
        yellow_accent.copy(alpha = 0.8f)
    } else if (themeColor == "red") {
        red_accent.copy(alpha = 0.8f)
    } else if (themeColor == "pink") {
        pink_accent.copy(alpha = 0.8f)
    } else if (themeColor == "black") {
        black_accent.copy(alpha = 0.8f)
    } else if (themeColor == "white") {
        white_accent.copy(alpha = 0.8f)
    } else {
        orange_accent.copy(alpha = 0.80f)
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

    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
    val date = if (contact.getBirthDate() != null) {
        val convert = LocalDate.parse(contact.getBirthDate().toString(), inputFormatter)
        convert.format(outputFormatter)
    } else {
        ""
    }

    var selectedImageUri by remember(contact) { mutableStateOf(contact.getPictureURL()) }
    var nameContact by remember { mutableStateOf(contact.getName()) }
    var isFamily by remember { mutableStateOf(contact.getFamilyMember()) }
    var isFavorite by remember { mutableStateOf(contact.getFavorite()) }
    var isEmergency by remember { mutableStateOf(contact.getEmergencyContact()) }
    var phoneNum by remember { mutableStateOf(TextFieldValue(contact.getPhoneNum())) }
    var emailAddress by remember { mutableStateOf(contact.getEmail()) }
    var birthday by remember { mutableStateOf(TextFieldValue(date)) }
    var address by remember { mutableStateOf(contact.getAddress()) }
    var groups by remember { mutableStateOf(contact.getGroup()) }

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
                            contact.setExpanded(false)
                            contact.setPictureURL(selectedImageUri)
                            contact.setName(nameContact)
                            if (phoneNum.text.length == 12) {
                                contact.setPhoneNum(phoneNum.text)
                            }
                            contact.setEmail(emailAddress)
                            if (birthday.text.length == 10) {
                                val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
                                val calcBirthDate = LocalDate.parse(birthday.text, formatter);
                                contact.setBirthDate(calcBirthDate)
                            }
                            contact.setIsEmergencyContact(isEmergency)
                            contact.setIsFavorite(isFavorite)
                            contact.setIsFamily(isFamily)
                            contact.setAddress(address)
                            contact.setGroup(groups)
                            val contactID = viewModel.contactsList.indexOf(contact)
                            (viewModel.contactsList[contactID] as PersonModel).personCopy(contact)
                            viewModel.saveContacts()
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
                        modifier = Modifier.size(55.dp),
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Check Icon",
                        tint = accent90
                    )
                }

                Text(
                    text = "Confirm",
                    color = themeTextColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 3.em,
                    modifier = Modifier.padding(end = 20.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

//            Log.d("IMAGE BEFORE", contact.getPictureURL().toString())
            // State to control the visibility of the dropdown menu
            val (expanded, setExpanded) = remember { mutableStateOf(false) }

            // Store photoUri in state to persist across recompositions
            val photoUri = remember(selectedImageUri) {
                context.contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    ContentValues().apply {
                        put(
                            MediaStore.MediaColumns.DISPLAY_NAME,
                            "ContactPhoto_${System.currentTimeMillis()}.jpg"
                        )
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                    }
                )
            }

            val albumLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia(),
//                    contract = ActivityResultContracts.OpenDocument(),
                onResult = { uri: Uri? ->
                    if (uri != null) {
                        uri?.let {
                            context.contentResolver.takePersistableUriPermission(
                                it,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION
                            )
                            selectedImageUri = it
//                            Log.d("SELECTED IAMGE", selectedImageUri.toString())
//                            Log.d("IMAGE AFTER", contact.getPictureURL().toString())
                        }
//                            selectedImageUri = uri
//                            onPhotoSelected(contactId, uri)\
                    }
                }
            )
            val cameraLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.TakePicture(),
                onResult = { success ->
                    if (success && photoUri != null) {
                        selectedImageUri = photoUri
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
                Box {
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
                            containerColor = icons80
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
                        } else {
                            Icon(
                                modifier = Modifier
                                    .size(80.dp),
                                imageVector = Icons.Filled.PhotoCamera,
                                contentDescription = "Contact Image",
                                tint = accent90
                            )
                        }
                    }

                    // DropdownMenu for options
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { setExpanded(false) },
                        modifier = Modifier.background(accent90)
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
//                                    albumLauncher.launch(arrayOf("image/*"))
                            },
                            text = {
                                Text(
                                    text = "Album",
                                    modifier = Modifier.padding(2.dp),
                                    color = themeTextColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 4.em,
                                    lineHeight = 1.em,
                                    textAlign = TextAlign.Center
                                )
                            }
                        )

                        // Option 2 inside DropdownMenuItem, text parameter passed correctly
                        DropdownMenuItem(
                            onClick = {
                                setExpanded(false)
                                if (photoUri != null) {
                                    photoUri?.let { cameraLauncher.launch(it) }
                                }
                            },
                            text = {
                                Text(
                                    text = "Camera",
                                    modifier = Modifier.padding(2.dp),
                                    color = themeTextColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 4.em,
                                    lineHeight = 1.em,
                                    textAlign = TextAlign.Center
                                )
                            }
                        )

                        if (selectedImageUri != null) {
                            DropdownMenuItem(
                                onClick = {
                                    setExpanded(false)
                                    selectedImageUri = null
                                },
                                text = {
                                    Text(
                                        text = "Clear",
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

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(175.dp))
                TextField(
                    value = nameContact,
                    onValueChange = { textFieldContents ->
                        nameContact = textFieldContents
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
                    value = birthday,
                    onValueChange = { textFieldContents ->
                        val allowedDateCharacters = Regex("^[0-9/]+$") // For digits and hyphens
                        if (textFieldContents.text.isEmpty() || (textFieldContents.text.matches(allowedDateCharacters) && textFieldContents.text.length <= 10)) {
                            birthday = textFieldContents
                            val newDate = textFieldContents.text.filter { it.isDigit() } // Extract only digits

                            var formattedBDay = ""
                            for (i in newDate.indices) {
                                formattedBDay += newDate[i]
                                if ((i == 1 || i == 3) && i < newDate.length - 1) {
                                    formattedBDay += "/"
                                }
                            }
                            birthday = TextFieldValue(
                                text = formattedBDay,
                                selection = TextRange(formattedBDay.length)
                            )
                        }
                    },
                    placeholder = { Text("Birthday mm/dd/yyyy", color = themeTextColor) },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(30.dp),
                            imageVector = Icons.Filled.Cake,
                            contentDescription = "Cake Icon",
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
                TextField(
                    value = groups,
                    onValueChange = { textFieldContents ->
                        groups = textFieldContents
                    },
                    placeholder = { Text("Group", color = themeTextColor) },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(30.dp),
                            imageVector = Icons.Filled.Group,
                            contentDescription = "Group Icon",
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
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(35.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    val primaryColor by remember(isFamily) {
                        mutableStateOf(if (isFamily) accent80 else icons80)
                    }
                    val secondaryColor by remember(isFamily) {
                        mutableStateOf(if (!isFamily) accent90 else icons90)
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = {
                            isFamily = !isFamily
                        }, modifier = Modifier
                            .size(50.dp)
                            .then(
                                if (isFamily) {
                                    Modifier.border(
                                        width = 2.dp,
                                        color = secondaryColor,
                                        shape = CircleShape
                                    )
                                } else {
                                    Modifier
                                }
                            ), contentPadding = PaddingValues(2.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = primaryColor
                            )) {
                            Icon(
                                imageVector = Icons.Filled.Home,
                                contentDescription = "Home",
                                modifier = Modifier.size(32.dp),
                                tint = secondaryColor
                            )
                        }
                        Text(
                            text = "Family",
                            color = themeTextColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 3.em
                        )
                    }
                    val primaryColor2 by remember(isFavorite) {
                        mutableStateOf(if (isFavorite) accent80 else icons80)
                    }
                    val secondaryColor2 by remember(isFavorite) {
                        mutableStateOf(if (!isFavorite) accent90 else icons90)
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = {
                            isFavorite = !isFavorite
                        }, modifier = Modifier
                            .size(50.dp)
                            .then(
                                if (isFavorite) {
                                    Modifier.border(
                                        width = 2.dp,
                                        color = secondaryColor2,
                                        shape = CircleShape
                                    )
                                } else {
                                    Modifier
                                }
                            ), contentPadding = PaddingValues(2.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = primaryColor2
                            )) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = "Favorite",
                                modifier = Modifier.size(32.dp),
                                tint = secondaryColor2
                            )
                        }
                        Text(
                            text = "Favorite",
                            color = themeTextColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 3.em
                        )
                    }
                    val primaryColor3 by remember(isEmergency) {
                        mutableStateOf(if (isEmergency) accent80 else icons80)
                    }
                    val secondaryColor3 by remember(isEmergency) {
                        mutableStateOf(if (!isEmergency) accent90 else icons90)
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = {
                            isEmergency = !isEmergency
                        }, modifier = Modifier
                            .size(50.dp)
                            .then(
                                if (isEmergency) {
                                    Modifier.border(
                                        width = 2.dp,
                                        color = secondaryColor3,
                                        shape = CircleShape
                                    )
                                } else {
                                    Modifier
                                }
                            ), contentPadding = PaddingValues(2.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = primaryColor3
                            )) {
                            Icon(
                                imageVector = Icons.Outlined.Warning,
                                contentDescription = "Emergency",
                                modifier = Modifier.size(32.dp),
                                tint = secondaryColor3
                            )
                        }
                        Text(
                            text = "Emergency",
                            color = themeTextColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 3.em
                        )
                    }
                }
            }
        }
    }
}
