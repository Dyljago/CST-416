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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.dylanjohnson.contactsapp.ui.theme.ContactsAppTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.dylanjohnson.contactsapp.models.BusinessModel
import com.dylanjohnson.contactsapp.models.ContactModel
import com.dylanjohnson.contactsapp.models.PersonModel
import java.time.LocalDate


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use UiModeManager to check if the system is in dark mode
        val uiModeManager = getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
        val isDarkMode = uiModeManager.nightMode == UiModeManager.MODE_NIGHT_YES

        val backgroundImage = if (isDarkMode) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.blue_status)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            R.drawable.background_image_blue
        } else {
            window.statusBarColor = ContextCompat.getColor(this, R.color.orange_status)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            R.drawable.background_image_orange
        }

        setContent {
            val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
            windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())

            // Prevents the navigation bar from reappearing when swiping
            windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

            ContactsAppTheme {
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
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
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
                        LazyColumn() {
                            item {
                                Spacer(modifier = Modifier.height(70.dp))
                            }
                            items(contacts.size) {
                                index -> Contact(contact = contacts[index], {
                                    Log.d("Conatct tag", "You clicked contact number: $it")

                                    // toggle the visibility property of the joke that was clicked
                                    // notice the ! (negation) character
                                    var changedContact = contacts[it].copy(isExpanded = !contacts[it].getExpanded())

                                    // replace the joke item at position number "it"
                                    contacts[it] = changedContact
                                },
                                context = LocalContext.current)
                            }
                            item {
                                Spacer(modifier = Modifier.height(70.dp))
                            }
//                            items(10) { index -> Greeting("Android #$index", modifier = Modifier.padding(innerPadding)) }
                        }
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
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ContactsAppTheme {
        Greeting("Android")
    }
}

data class JokeModel(
    val id: Int,
    var question: String,
    var answer : String,
    var answerIsVisible: Boolean
)

