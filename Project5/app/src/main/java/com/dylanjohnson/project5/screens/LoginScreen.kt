package com.dylanjohnson.project5.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
public fun LoginScreen(navController: NavHostController) {
    // create data entry form for username and password
    // create a button to submit the form

    // username and password are variables to hold the data entered by the user
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // center

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.inversePrimary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // title
        Text(
            "Login",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(16.dp))
        // username field
        TextField(
            value = username,
            // this lambda function is called when the user enters data. "it" is the contents of the
            // text field. The value of username is set to "it" whenever the text field contents change.
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.background(Color.White, shape = RoundedCornerShape(10.dp))
        )
        Spacer(modifier = Modifier.height(16.dp))
        // password field
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.background(Color.White, shape = RoundedCornerShape(10.dp))
        )
        Spacer(modifier = Modifier.height(16.dp))
        // button
        Button(onClick = { navController.navigate("show-jokes") }) {
            Text("Login")
        }
    }
}