package com.example.myapplication2

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    var urlPrefix by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("0") }
    var result by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = urlPrefix,
            onValueChange = { urlPrefix = it },
            label = { Text("URL Prefix") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = number,
            onValueChange = { newValue ->
                // Only allow digits
                if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                    number = newValue
                }
            },
            label = { Text("Number") },
            modifier = Modifier.fillMaxWidth()
        )

        // Positive adjustments row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AdjustmentButton("+1000") { 
                val currentNum = number.toIntOrNull() ?: 0
                number = (currentNum + 1000).toString()
            }
            AdjustmentButton("+100") { 
                val currentNum = number.toIntOrNull() ?: 0
                number = (currentNum + 100).toString()
            }
            AdjustmentButton("+10") { 
                val currentNum = number.toIntOrNull() ?: 0
                number = (currentNum + 10).toString()
            }
            AdjustmentButton("+1") { 
                val currentNum = number.toIntOrNull() ?: 0
                number = (currentNum + 1).toString()
            }
        }

        // Negative adjustments row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AdjustmentButton("-1000") { 
                val currentNum = number.toIntOrNull() ?: 0
                number = (currentNum - 1000).toString()
            }
            AdjustmentButton("-100") { 
                val currentNum = number.toIntOrNull() ?: 0
                number = (currentNum - 100).toString()
            }
            AdjustmentButton("-10") { 
                val currentNum = number.toIntOrNull() ?: 0
                number = (currentNum - 10).toString()
            }
            AdjustmentButton("-1") { 
                val currentNum = number.toIntOrNull() ?: 0
                number = (currentNum - 1).toString()
            }
        }

        Button(
            onClick = {
                val num = number.toIntOrNull()
                if (num != null) {
                    result = "$urlPrefix$num"
                    // Copy to clipboard
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Generated URL", result)
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(context, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Please enter a valid number", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Generate")
        }

        if (result.isNotEmpty()) {
            Text(
                text = result,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Composable
fun AdjustmentButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        Text(text)
    }
} 