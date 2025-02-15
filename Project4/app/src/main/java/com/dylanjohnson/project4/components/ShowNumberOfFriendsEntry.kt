package com.dylanjohnson.project4.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dylanjohnson.project4.ViewModelForTips

@Composable
fun ShowNumberOfFriendsEntry(tipViewModel: ViewModelForTips) {
    // card is a container control to put a pretty rectangle around related items.
    Card(
        // elevation is the shadow effect
        elevation = CardDefaults.cardElevation(10.dp),
        // specify only top and bottom extra padding. No need to put extra space on sides.
        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var counterState by remember {
                mutableStateOf(1)
            }
            Text(text = "Number of people in group:", modifier = Modifier.padding(bottom = 10.dp))

            // must use Row to line up the three items horizontally
            Row(
                modifier = Modifier.padding(bottom = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // item #1 in row is a round button
                Button(
                    onClick = {
                        // no negative numbers allowed
                        if (counterState > 1) {
                            counterState--
                        }
                        // send the updated value to the callback function.
                        tipViewModel.numberOfFriends = counterState
                        tipViewModel.calculateTipValues()
                    }, // onClick end.
                    modifier = Modifier.size(40.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(text = "-")
                }
                // item #2 in row is an integer
                Text(
                    text = "$counterState",
                    Modifier.padding(10.dp),
                    // larger font than default size
                    style = MaterialTheme.typography.headlineMedium
                )
                // item #3 in row is another round button
                Button(
                    onClick = {
                        // up to 10 friends can play!
                        if (counterState < 10) {
                            counterState++
                        }
                        tipViewModel.numberOfFriends = counterState
                        tipViewModel.calculateTipValues()
                    },
                    modifier = Modifier.size(40.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(text = "+")
                }
            }
        }
    }
}

@Composable
private fun ShowNumberOfFriendsEntry(numberOfFriends: Int, updateFriendsCount: (Int) -> Unit) {
    // card is a container control to put a pretty rectangle around related items.
    Card(
        // elevation is the shadow effect
        elevation = CardDefaults.cardElevation(10.dp),
        // specify only top and bottom extra padding. No need to put extra space on sides.
        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var counterState by remember {
                mutableStateOf(1)
            }
            Text(text = "Number of people in group:", modifier = Modifier.padding(bottom = 10.dp))

            // must use Row to line up the three items horizontally
            Row(
                modifier = Modifier.padding(bottom = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // item #1 in row is a round button
                Button(
                    onClick = {
                        // no negative numbers allowed
                        if (counterState > 1) {
                            counterState--
                        }
                        // send the updated value to the callback function.
                        updateFriendsCount(counterState)
                    }, // onClick end.
                    modifier = Modifier.size(40.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(text = "-")
                }
                // item #2 in row is an integer
                Text(
                    text = "$counterState",
                    Modifier.padding(10.dp),
                    // larger font than default size
                    style = MaterialTheme.typography.headlineMedium
                )
                // item #3 in row is another round button
                Button(
                    onClick = {
                        // up to 10 friends can play!
                        if (counterState < 10) {
                            counterState++
                        }
                        updateFriendsCount(counterState)
                    },
                    modifier = Modifier.size(40.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(text = "+")
                }
            }
        }
    }
}
