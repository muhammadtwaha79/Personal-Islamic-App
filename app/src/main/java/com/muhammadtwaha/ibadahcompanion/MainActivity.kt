package com.muhammadtwaha.ibadahcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.muhammadtwaha.ibadahcompanion.ui.navigation.AppNavHost
import com.muhammadtwaha.ibadahcompanion.ui.theme.IbadahTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IbadahTheme {
                AppNavHost()
            }
        }
    }
}
