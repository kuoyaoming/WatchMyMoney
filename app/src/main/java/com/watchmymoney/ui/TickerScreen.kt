package com.watchmymoney.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.watchmymoney.logic.SalaryCalculator
import java.text.DecimalFormat

@Composable
fun TickerScreen(
    annualSalary: Double,
    currencySymbol: String,
    resetHour: Int,
    onEditClick: () -> Unit
) {
    var earnedAmount by remember { mutableStateOf(0.0) }

    // 60Hz Animation Loop
    LaunchedEffect(annualSalary, resetHour) {
        while (true) {
            withFrameMillis { frameTimeMillis ->
                // We use System.currentTimeMillis() for wall-clock accuracy
                // frameTimeMillis is monotonic, good for delta, but we need absolute time for salary
                val now = System.currentTimeMillis()
                val result = SalaryCalculator.calculate(annualSalary, now, resetHour)
                earnedAmount = result.earnedToday
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "You earned",
            style = MaterialTheme.typography.caption1,
            color = MaterialTheme.colors.secondary
        )
        
        // Split integer and decimal for visual styling
        val parts = String.format("%.6f", earnedAmount).split(".")
        val integerPart = parts[0]
        val decimalPart = parts.getOrElse(1) { "00" }

        Text(
            text = "$currencySymbol$integerPart",
            style = MaterialTheme.typography.display1.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp
            )
        )
        Text(
            text = ".$decimalPart",
            style = MaterialTheme.typography.title3.copy(
                color = MaterialTheme.colors.primaryVariant
            )
        )
        
        androidx.wear.compose.material.CompactButton(
            onClick = onEditClick,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            androidx.wear.compose.material.Icon(
                imageVector = androidx.compose.material.icons.Icons.Rounded.Edit,
                contentDescription = "Edit"
            )
        }
    }
}
