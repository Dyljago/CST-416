package com.dylanjohnson.project4.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dylanjohnson.project4.ViewModelForTips
import java.text.NumberFormat

@Composable
fun ShowTotals(tipViewModel: ViewModelForTips) {
    // first card shows cost for each person
    Card(
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp, top = 10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(10.dp)
        ) {
            Text(
                style = MaterialTheme.typography.headlineSmall,
                text = "Each person pays:"
            )
            // format for $
            val formattedPerPerson = NumberFormat.getCurrencyInstance().format(tipViewModel.totalPerPerson)
            Text(
                style = MaterialTheme.typography.headlineLarge,
                text = "${formattedPerPerson}"
            )
        }
    }
    // second card shows the total cost for the group.
    Card(
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(10.dp)
        ) {
            Text(
                style = MaterialTheme.typography.headlineSmall,
                text = "Total price:"
            )
            val formattedTotal = NumberFormat.getCurrencyInstance().format(tipViewModel.totalBill)
            Text(
                style = MaterialTheme.typography.headlineLarge,
                text = "${formattedTotal}"
            )
        }
    }
}

@Composable
private fun ShowTotals(totalBill: Float, totalPerPerson: Float) {
    // first card shows cost for each person
    Card(
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp, top = 10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(10.dp)
        ) {
            Text(
                style = MaterialTheme.typography.headlineSmall,
                text = "Each person pays:"
            )
            // format for $
            val formattedPerPerson = NumberFormat.getCurrencyInstance().format(totalPerPerson)
            Text(
                style = MaterialTheme.typography.headlineLarge,
                text = "${formattedPerPerson}"
            )
        }
    }
    // second card shows the total cost for the group.
    Card(
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(10.dp)
        ) {
            Text(
                style = MaterialTheme.typography.headlineSmall,
                text = "Total price:"
            )
            val formattedTotal = NumberFormat.getCurrencyInstance().format(totalBill)
            Text(
                style = MaterialTheme.typography.headlineLarge,
                text = "${formattedTotal}"
            )
        }
    }
}
