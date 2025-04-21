package com.dylanjohnson.contactsapp.classes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object ThemeUtils {
    // A MutableState to hold the current theme state
    private val _themeColor = mutableStateOf(false)

    // Public read-only access
    val themeColor: State<Boolean> get() = _themeColor

    // A Composable that updates the state
    @Composable
    fun TrackSystemTheme() {
        // This will be recomposed when the theme changes
        _themeColor.value = isSystemInDarkTheme()
        // Return an empty Box so this can be placed anywhere
        Box(modifier = Modifier.size(0.dp)) {}
    }
}