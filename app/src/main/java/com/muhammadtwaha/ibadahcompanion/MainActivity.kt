package com.muhammadtwaha.ibadahcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.muhammadtwaha.ibadahcompanion.data.WorshipRepository
import com.muhammadtwaha.ibadahcompanion.data.DhikrRepository
import com.muhammadtwaha.ibadahcompanion.data.ManzilsRepository
import com.muhammadtwaha.ibadahcompanion.ui.navigation.AppNavHost
import com.muhammadtwaha.ibadahcompanion.ui.theme.IbadahTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var repository: WorshipRepository
    @Inject lateinit var dhikrRepository: DhikrRepository
    @Inject lateinit var manzilsRepository: ManzilsRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { IbadahTheme { AppNavHost(repository, dhikrRepository, manzilsRepository) } }
    }
}
