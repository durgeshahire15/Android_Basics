package com.example.androidproject

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidproject.ui.theme.AndroidProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            AndroidProjectTheme {
                Button(onClick = {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                       type = "text/plain"
                        putExtra(Intent.EXTRA_EMAIL, arrayOf("test@test.com"))
                        putExtra(Intent.EXTRA_SUBJECT,"This is my subject")
                        putExtra(Intent.EXTRA_TEXT,"This is my content of the email")
                    }
                    if(intent.resolveActivity(packageManager)!=null){
                        startActivity(intent)
                    }
                }) {
                    Text(text = "Click me")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidProjectTheme {
        Greeting("Android")
    }
}