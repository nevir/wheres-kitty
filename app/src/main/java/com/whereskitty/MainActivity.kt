package com.whereskitty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.whereskitty.navigation.AppNavGraph
import com.whereskitty.ui.theme.WhereIsKittyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WhereIsKittyTheme {
                AppNavGraph()
            }
        }
    }
}
