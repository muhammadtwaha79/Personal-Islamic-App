package com.muhammadtwaha.ibadahcompanion.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.muhammadtwaha.ibadahcompanion.data.WorshipRepository
import com.muhammadtwaha.ibadahcompanion.data.DhikrRepository
import com.muhammadtwaha.ibadahcompanion.data.ManzilsRepository
import com.muhammadtwaha.ibadahcompanion.ui.dhikr.DhikrScreen
import com.muhammadtwaha.ibadahcompanion.ui.home.HomeScreen
import com.muhammadtwaha.ibadahcompanion.ui.manzils.ManzilsScreen
import com.muhammadtwaha.ibadahcompanion.ui.nafl.NaflScreen
import com.muhammadtwaha.ibadahcompanion.ui.quran.QuranScreen
import com.muhammadtwaha.ibadahcompanion.ui.salah.ChallengeScreen
import com.muhammadtwaha.ibadahcompanion.ui.salah.SalahScreen

private data class NavigationItem(val route: String, val label: String, val glyph: String)
private val barItems = listOf(
    NavigationItem("home", "Home", "⌂"),
    NavigationItem("salah", "Salah", "◈"),
    NavigationItem("quran", "Quran", "▤"),
    NavigationItem("dhikr", "Dhikr", "✦"),
)

/** Home is the default entry; all modules are reachable by illustrated home tiles.
 * No Masnoon Duas catalog is routed or included in the visible UI. */
@Composable
fun AppNavHost(
    repository: WorshipRepository,
    dhikrRepository: DhikrRepository,
    manzilsRepository: ManzilsRepository,
) {
    val controller = rememberNavController()
    val entry = controller.currentBackStackEntryAsState().value
    val activeRoute = entry?.destination?.route ?: "home"

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp,
            ) {
                barItems.forEach { tab ->
                    val selected = activeRoute == tab.route
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            controller.navigate(tab.route) {
                                popUpTo("home") { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Text(
                                tab.glyph, fontSize = 23.sp,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                            )
                        },
                        label = { Text(tab.label, fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                        ),
                    )
                }
            }
        },
    ) { inner ->
        NavHost(
            navController = controller,
            startDestination = "home",
            modifier = Modifier.padding(inner),
        ) {
            composable("home") { HomeScreen(repository, onOpen = { controller.navigate(it) }) }
            composable("salah") { SalahScreen(repository) }
            composable("challenge") { ChallengeScreen(repository) }
            composable("nafl") { NaflScreen(repository) }
            composable("quran") { QuranScreen(repository) }
            composable("dhikr") { DhikrScreen(dhikrRepository) }
            composable("manzils") { ManzilsScreen(dhikrRepository, manzilsRepository) }
        }
    }
}
