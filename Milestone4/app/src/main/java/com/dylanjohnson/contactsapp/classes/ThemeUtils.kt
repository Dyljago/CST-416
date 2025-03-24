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
    private val _isDarkMode = mutableStateOf(false)

    // Public read-only access
    val isDarkMode: State<Boolean> get() = _isDarkMode

    // A Composable that updates the state
    @Composable
    fun TrackSystemTheme() {
        // This will be recomposed when the theme changes
        _isDarkMode.value = isSystemInDarkTheme()
        // Return an empty Box so this can be placed anywhere
        Box(modifier = Modifier.size(0.dp)) {}
    }
}