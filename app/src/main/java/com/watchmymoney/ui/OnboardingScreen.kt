package com.watchmymoney.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Backspace
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.CompactButton
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text

@Composable
fun OnboardingScreen(
    onSalarySet: (Double) -> Unit
) {
    var inputString by remember { mutableStateOf("") }

    // Compact layout for round screens (Pixel Watch 2 is ~192dp usable height)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Display Area - Compact
        Text(
            text = if (inputString.isEmpty()) "Salary?" else "$$inputString",
            style = MaterialTheme.typography.title3,
            color = if (inputString.isEmpty()) Color.Gray else MaterialTheme.colors.primary,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
        
        Spacer(modifier = Modifier.height(4.dp))

        // Number Pad - Tighter spacing
        val buttonSize = 34.dp // Slightly larger than standard CompactButton for touch target
        val spacing = 2.dp

        Row(horizontalArrangement = Arrangement.spacedBy(spacing)) {
            NumButton("1", buttonSize) { inputString += "1" }
            NumButton("2", buttonSize) { inputString += "2" }
            NumButton("3", buttonSize) { inputString += "3" }
        }
        Spacer(modifier = Modifier.height(spacing))
        Row(horizontalArrangement = Arrangement.spacedBy(spacing)) {
            NumButton("4", buttonSize) { inputString += "4" }
            NumButton("5", buttonSize) { inputString += "5" }
            NumButton("6", buttonSize) { inputString += "6" }
        }
        Spacer(modifier = Modifier.height(spacing))
        Row(horizontalArrangement = Arrangement.spacedBy(spacing)) {
            NumButton("7", buttonSize) { inputString += "7" }
            NumButton("8", buttonSize) { inputString += "8" }
            NumButton("9", buttonSize) { inputString += "9" }
        }
        Spacer(modifier = Modifier.height(spacing))
        Row(horizontalArrangement = Arrangement.spacedBy(spacing)) {
            NumButton(".", buttonSize) { 
                if (!inputString.contains(".")) {
                    inputString = if (inputString.isEmpty()) "0." else inputString + "."
                }
            }
            NumButton("0", buttonSize) { if (inputString.isNotEmpty()) inputString += "0" }
            // Backspace
            CompactButton(
                onClick = {
                    if (inputString.isNotEmpty()) {
                        inputString = inputString.dropLast(1)
                    }
                },
                colors = ButtonDefaults.secondaryButtonColors(),
                modifier = Modifier.size(buttonSize)
            ) {
                Icon(Icons.Rounded.Backspace, contentDescription = "Del")
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Confirm Button - Only show if valid to save space, or keep it small
        if (inputString.isNotEmpty() && inputString.toDoubleOrNull() != null && inputString.toDouble() > 0) {
            Button(
                onClick = {
                    val salary = inputString.toDoubleOrNull()
                    if (salary != null && salary > 0) {
                        onSalarySet(salary)
                    }
                },
                colors = ButtonDefaults.primaryButtonColors(),
                modifier = Modifier.height(32.dp).width(80.dp) // Wide but short
            ) {
                Icon(Icons.Rounded.Check, contentDescription = "Confirm")
            }
        } else {
             // Placeholder to keep layout stable or just empty
             Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun NumButton(text: String, size: androidx.compose.ui.unit.Dp, onClick: () -> Unit) {
    CompactButton(
        onClick = onClick,
        colors = ButtonDefaults.secondaryButtonColors(),
        modifier = Modifier.size(size)
    ) {
        Text(text = text, style = MaterialTheme.typography.body2)
    }
}
