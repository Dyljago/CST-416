package com.dylanjohnson.contactsapp.classes

import android.content.Context
import android.content.Intent
import android.net.Uri

fun EmailContact(email: String, context: Context) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:") // only email apps should handle this
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
    }
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        // Handle the case where there is no SMS app installed
        // Or the user doesn't have permission to send SMS
    }
}