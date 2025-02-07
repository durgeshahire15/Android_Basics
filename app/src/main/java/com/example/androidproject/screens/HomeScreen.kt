package com.example.androidproject.screens
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    Surface(
        modifier = Modifier.padding(16.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Box( // Use Box to center content
            modifier = Modifier.fillMaxSize(), // Fill the Surface
            contentAlignment = Alignment.Center // Center the Text
        ) {
            Text(
                text = "🏠 Home Screen",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center // Center text within Text composable
            )
        }
    }
}


@Preview(showBackground = true, heightDp = 500, widthDp = 300)
@Composable
fun PreviewHomeScreen() {
    Surface(
        modifier = Modifier.padding(16.dp),

        color = MaterialTheme.colorScheme.surface
    ) {
        Box( // Use Box to center content
            modifier = Modifier.fillMaxSize(), // Fill the Surface
            contentAlignment = Alignment.Center // Center the Text
        ) {
            Text(
                text = "🏠 Home Screen",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center // Center text within Text composable
            )
        }
    }
}
