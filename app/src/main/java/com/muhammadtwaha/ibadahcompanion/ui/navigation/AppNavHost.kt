package com.muhammadtwaha.ibadahcompanion.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.muhammadtwaha.ibadahcompanion.data.WorshipRepository
import com.muhammadtwaha.ibadahcompanion.data.DhikrRepository
import com.muhammadtwaha.ibadahcompanion.ui.dhikr.DhikrScreen
import com.muhammadtwaha.ibadahcompanion.ui.nafl.NaflScreen
import com.muhammadtwaha.ibadahcompanion.ui.quran.QuranScreen
import com.muhammadtwaha.ibadahcompanion.ui.salah.ChallengeScreen
import com.muhammadtwaha.ibadahcompanion.ui.salah.SalahScreen

private data class Tab(val route: String, val label: String, val symbol: String)
private val tabs = listOf(
    Tab("salah", "Salah", "S"),
    Tab("challenge", "40 days", "40"),
    Tab("nafl", "Nafl", "N"),
    Tab("quran", "Quran", "Q"),
    Tab("dhikr", "Dhikr", "D"),
)

@Composable
fun AppNavHost(repository: WorshipRepository, dhikrRepository: DhikrRepository) {
    val navController = rememberNavController()
    val entry = navController.currentBackStackEntryAsState().value
    Scaffold(bottomBar = {
        NavigationBar {
            tabs.forEach { tab ->
                NavigationBarItem(
                    selected = (entry?.destination?.route ?: "salah") == tab.route,
                    onClick = { navController.navigate(tab.route) {
                        popUpTo("salah") { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    } },
                    icon = { Text(tab.symbol) },
                    label = { Text(tab.label) },
                )
            }
        }
    }) { inner ->
        NavHost(navController = navController, startDestination = "salah",
            modifier = Modifier.padding(inner)) {
            composable("salah") { SalahScreen(repository) }
            composable("challenge") { ChallengeScreen(repository) }
            composable("nafl") { NaflScreen(repository) }
            composable("quran") { QuranScreen(repository) }
            composable("dhikr") { DhikrScreen(dhikrRepository) }
        }
    }
}
