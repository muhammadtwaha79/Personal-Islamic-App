package com.muhammadtwaha.ibadahcompanion.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammadtwaha.ibadahcompanion.ui.design.ElitePalette

private val LightColors = lightColorScheme(
    primary = ElitePalette.Pine,
    onPrimary = Color.White,
    primaryContainer = ElitePalette.Sage,
    onPrimaryContainer = ElitePalette.PineDeep,
    secondary = ElitePalette.Emerald,
    onSecondary = Color.White,
    secondaryContainer = ElitePalette.Cream,
    onSecondaryContainer = ElitePalette.Ink,
    tertiary = Color(0xFF9B743D),
    background = ElitePalette.Ivory,
    onBackground = ElitePalette.Ink,
    surface = Color.White,
    onSurface = ElitePalette.Ink,
    onSurfaceVariant = ElitePalette.Muted,
    surfaceVariant = Color(0xFFF0F3EF),
    outline = Color(0xFFA2B2A8),
    outlineVariant = Color(0xFFDCE5DE),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFA9E5CF),
    onPrimary = Color(0xFF123C31),
    primaryContainer = Color(0xFF24493D),
    onPrimaryContainer = Color(0xFFDDF4E8),
    secondary = Color(0xFF93CEB7),
    onSecondary = Color(0xFF12382E),
    tertiary = ElitePalette.Gold,
    background = Color(0xFF101D19),
    onBackground = Color(0xFFF6F4ED),
    surface = Color(0xFF1C2B25),
    onSurface = Color(0xFFF4F5ED),
    onSurfaceVariant = Color(0xFFB6C4BC),
    surfaceVariant = Color(0xFF2A3B34),
    outline = Color(0xFF73867B),
    outlineVariant = Color(0xFF3B5045),
)

private val AppTypography = Typography(
    headlineMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 34.sp,
        letterSpacing = (-0.6).sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 23.sp,
        lineHeight = 30.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 26.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 23.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 15.sp,
        lineHeight = 23.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 13.sp,
        lineHeight = 20.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 12.sp,
        lineHeight = 18.sp,
    ),
)

private val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(9.dp),
    small = RoundedCornerShape(13.dp),
    medium = RoundedCornerShape(17.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(30.dp),
)

@Composable
fun IbadahTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = AppTypography,
        shapes = AppShapes,
        content = content,
    )
}
