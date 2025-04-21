package com.dylanjohnson.contactsapp.classes

import android.content.Context
import android.content.Intent
import android.net.Uri

fun TextContact(phoneNumber: String, context: Context) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:$phoneNumber"))
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        // Handle the case where there is no SMS app installed
        // Or the user doesn't have permission to send SMS
    }
}