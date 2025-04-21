package com.dylanjohnson.contactsapp.screens

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.AddRoad
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.KeyboardBackspace
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.dylanjohnson.contactsapp.R
import com.dylanjohnson.contactsapp.classes.CallContact
import com.dylanjohnson.contactsapp.classes.EmailContact
import com.dylanjohnson.contactsapp.classes.MapContact
import com.dylanjohnson.contactsapp.classes.TextContact
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
import com.dylanjohnson.contactsapp.ui.theme.invalid_button
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

@Composable
fun DetailScreen(contactId: Int?, navController: NavHostController, viewModel: ContactViewModelInterface, onPhotoSelected: (changeId: Int, pictureUri: Uri?) -> Unit) {
    val contactsList = viewModel.contactsList
    val contact = contactsList[contactId!!]
    val context = LocalContext.current
    val themeColor = viewModel.chosenTheme.value.getTheme()
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
    val accent60 = if (themeColor == "blue") {
        blue_accent.copy(alpha = 0.6f)
    } else if (themeColor == "green") {
        green_accent.copy(alpha = 0.6f)
    } else if (themeColor == "purple") {
        purple_accent.copy(alpha = 0.6f)
    } else if (themeColor == "yellow") {
        yellow_accent.copy(alpha = 0.6f)
    } else if (themeColor == "red") {
        red_accent.copy(alpha = 0.6f)
    } else if (themeColor == "pink") {
        pink_accent.copy(alpha = 0.6f)
    } else if (themeColor == "black") {
        black_accent.copy(alpha = 0.6f)
    } else if (themeColor == "white") {
        white_accent.copy(alpha = 0.6f)
    } else {
        orange_accent.copy(alpha = 0.6f)
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(accent),
        contentAlignment = Alignment.Center
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
                    text = "Return" ,
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
                        if (contact is PersonModel) {
                            navController.navigate("edit/person/${contact.getId()}")
                        } else if (contact is BusinessModel) {
                            navController.navigate("edit/business/${contact.getId()}")
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
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit Symbol",
                        tint = accent90
                    )
                }

                Text(
                    text = "Edit" ,
                    color = themeTextColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 3.em,
                    modifier = Modifier.padding(end = 31.dp)
                )
            }

            if (contact is PersonModel) {

//                var selectedImageUri by remember { mutableStateOf<Uri?>(contact.getPictureURL()) }
                var selectedImageUri by remember(contact) { mutableStateOf(contact.getPictureURL()) }

//                Log.d("IMAGE BEFORE", contact.getPictureURL().toString())
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
//                    contract = ActivityResultContracts.OpenDocument(),
                    onResult = { uri: Uri? ->
                        if (uri != null) {
                            uri?.let {
                                context.contentResolver.takePersistableUriPermission(
                                    it,
                                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                                )
                                selectedImageUri = it
                                onPhotoSelected(contactId, it)
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
                            }
                            else {
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
                                        onPhotoSelected(contactId, null)
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

                    Text(
                        text = contact.getName(),
                        color = themeTextColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 6.em
                    )
                }
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
//                        Log.d("OPINON", contact.getMyOpinionRating().toString())
                        for (i in 1 until 6) {
                            val star = if (contact.getMyOpinionRating() >= i) {
//                                Log.d("OPINION", "FULL")
                                Icons.Filled.Star
                            } else if (contact.getMyOpinionRating() == (i - 0.5f)) {
//                                Log.d("OPINION", "HALF")
                                Icons.AutoMirrored.Filled.StarHalf
                            } else {
//                                Log.d("OPINION", "EMPTY")
                                Icons.Filled.StarBorder
                            }
                            Icon(
                                modifier = Modifier
                                    .size(60.dp),
                                imageVector = star,
                                contentDescription = "Star Image",
                                tint = icons90
                            )
                        }
                    }

                    Text(
                        text = contact.getName(),
                        color = themeTextColor,
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
                        onClick = {},
                        modifier = Modifier
                            .padding(top = 40.dp)
                            .size(120.dp),
                        contentPadding = PaddingValues(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = icons80
                        )
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(80.dp),
                            imageVector = Icons.Filled.PhotoCamera,
                            contentDescription = "Contact Image",
                            tint = accent90
                        )
                    }

                    Text(
                        text = contact.getName(),
                        color = themeTextColor,
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
                                ButtonDefaults.buttonColors(containerColor = icons80)
                            } else {
                                ButtonDefaults.buttonColors(containerColor = if (themeColor != "black") invalid_button.copy(alpha = 0.80f) else dark_text_color.copy(alpha = 0.8f))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Chat,
                                contentDescription = "Text",
                                modifier = Modifier.size(40.dp),
                                tint = accent90
                            )
                        }

                        Text(
                            text = "Message" ,
                            color = themeTextColor,
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
                                ButtonDefaults.buttonColors(containerColor = icons80)
                            } else {
                                ButtonDefaults.buttonColors(containerColor = if (themeColor != "black") invalid_button.copy(alpha = 0.80f) else dark_text_color.copy(alpha = 0.8f))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Phone,
                                contentDescription = "Call",
                                modifier = Modifier.size(40.dp),
                                tint = accent90
                            )
                        }

                        Text(
                            text = "Call" ,
                            color = themeTextColor,
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
                                ButtonDefaults.buttonColors(containerColor = icons80)
                            } else {
                                ButtonDefaults.buttonColors(containerColor = if (themeColor != "black") invalid_button.copy(alpha = 0.80f) else dark_text_color.copy(alpha = 0.8f))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Mail,
                                contentDescription = "Email",
                                modifier = Modifier.size(40.dp),
                                tint = accent90
                            )
                        }

                        Text(
                            text = "Email" ,
                            color = themeTextColor,
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
                                ButtonDefaults.buttonColors(containerColor = icons80)
                            } else {
                                ButtonDefaults.buttonColors(containerColor = if (themeColor != "black") invalid_button.copy(alpha = 0.80f) else dark_text_color.copy(alpha = 0.8f))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Map,
                                contentDescription = "Map",
                                modifier = Modifier.size(40.dp),
                                tint = accent90
                            )
                        }

                        Text(
                            text = "Map" ,
                            color = themeTextColor,
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
                            color = icons60,
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
                            tint = accent90
                        )
                        Text(
                            text = if (contact.getPhoneNum().isNullOrEmpty()) {
                                "No Phone Number Entered"
                            } else {
                                contact.getPhoneNum()
                            },
                            color = accent90,
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
                            color = icons60,
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
                            tint = accent90
                        )
                        Text(
                            text = if (contact.getEmail().isNullOrEmpty()) {
                                "No Email Entered"
                            } else {
                                contact.getEmail()
                            },
                            color = accent90,
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
                                color = icons60,
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
                                tint = accent90
                            )
                            Text(
                                text = if (contact.getBirthDate() == null) {
                                    "No birthday Entered"
                                } else {
                                    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                                    val outputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
                                    if (contact.getBirthDate() != null) {
                                        val convert = LocalDate.parse(contact.getBirthDate().toString(), inputFormatter)
                                        convert.format(outputFormatter)
                                    } else {
                                        "ERROR BIRTHDAY"
                                    }
                                },
                                color = accent90,
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
                                color = icons60,
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
                                tint = accent90
                            )
                            val websiteURL = contact.getWebURL()
                            val uriHandler = LocalUriHandler.current

                            Text(
                                text = if (websiteURL.isNullOrEmpty()) {
                                    "No website link Entered"
                                } else {
                                    websiteURL
                                },
                                color = accent90,
                                fontWeight = FontWeight.Bold,
                                fontSize = 4.em,
                                modifier = if (!websiteURL.isNullOrEmpty()) {
                                    Modifier.clickable {
                                        val uriString = if (!websiteURL.startsWith("http://") && !websiteURL.startsWith("https://")) {
                                            "http://$websiteURL" // Add http:// as a default if no scheme
                                        } else {
                                            websiteURL
                                        }
                                        uriHandler.openUri(uriString)
                                    }
                                } else {
                                    Modifier
                                }
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
                            color = icons60,
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
                            tint = accent90
                        )
                        Text(
                            text = if (contact.getAddress().isNullOrEmpty()) {
                                "No address Entered"
                            } else {
                                contact.getAddress()
                            },
                            color = accent90,
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
                                color = icons60,
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
                                tint = accent90
                            )
                            Text(
                                text = if (contact.getGroup().isNullOrEmpty()) {
                                    "No groups"
                                } else {
                                    contact.getGroup()
                                },
                                color = accent90,
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
                                color = icons60,
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
                                tint = accent90
                            )
                            Text(
                                text = contact.getDaysOpenForText(),
                                color = accent90,
                                fontWeight = FontWeight.Bold,
                                fontSize = 4.em
                            )
                        }
                    }
                }

                // Favorites
                if (contact is PersonModel) {
                    val favoriteColor = if (contact.getFavorite()) {
                        accent60
                    } else {
                        icons60
                    }
                    val secondaryColor = if (!contact.getFavorite()) {
                        accent90
                    } else {
                        icons90
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
