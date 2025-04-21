package com.dylanjohnson.contactsapp.classes

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import java.net.URLEncoder

fun MapContact(address: String, context: Context) {
    val encodedAddress = URLEncoder.encode(address, "UTF-8")
    val gmmIntentUri = Uri.parse("geo:0,0?q=$encodedAddress")
    val intent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    intent.setPackage("com.google.android.apps.maps")
    try {
        context.startActivity(intent)
    } catch (e: android.content.ActivityNotFoundException) {
        //Log.d("ERROR", "ERROR REACHED")
        // Handle the exception if Google Maps is not installed
        val genericMapIntentUri = Uri.parse("geo:0,0?q=$encodedAddress")
        val genericMapIntent = Intent(Intent.ACTION_VIEW, genericMapIntentUri)
        context.startActivity(genericMapIntent)
    }
}