package com.example.androidproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidproject.ui.theme.AndroidProjectTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Durgesh Ahire",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
        val userListAPI = RetroFitHelper.getInstance().create(UsersAPI::class.java)
        GlobalScope.launch {
            val result = userListAPI.getUsers()
            result.body()?.forEach {
                Log.d("DurgeshAPI", it.avatar_url)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.Bottom) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Text(text = "I am sitting in US",)
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidProjectTheme {
        Greeting("Android")
    }
}