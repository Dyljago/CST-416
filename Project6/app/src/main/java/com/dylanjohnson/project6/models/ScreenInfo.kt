package com.dylanjohnson.project6.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

// A data class representing a bottom navigation bar item
class ScreenInfo(
    val routeName: String, // The route name used to navigate to this screen
    val title: String, // The title to be displayed in the bottom navigation bar
    val navIcon: ImageVector,   // The icon to be displayed in the bottom navigation bar
    // A composable (i.e. screen) is passed as a parameter to be displayed when the item is selected
    val composable: @Composable () -> Unit = {}
)