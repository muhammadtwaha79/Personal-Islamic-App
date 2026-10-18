package com.muhammadtwaha.ibadahcompanion.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Emerald700 = Color(0xFF0B5D4B)
private val Emerald300 = Color(0xFF6FCFB4)
private val Ivory = Color(0xFFFAF6EC)
private val Ink = Color(0xFF1B1B18)
private val Night = Color(0xFF10140F)
private val Gold600 = Color(0xFF9A7B1C)
private val Gold400 = Color(0xFFE0C15A)

private val LightColors = lightColorScheme(
    primary = Emerald700,
    onPrimary = Ivory,
    secondary = Gold600,
    onSecondary = Ink,
    background = Ivory,
    onBackground = Ink,
    surface = Ivory,
    onSurface = Ink,
)

private val DarkColors = darkColorScheme(
    primary = Emerald300,
    onPrimary = Night,
    secondary = Gold400,
    onSecondary = Night,
    background = Night,
    onBackground = Ivory,
    surface = Night,
    onSurface = Ivory,
)

@Composable
fun IbadahTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content,
    )
}
