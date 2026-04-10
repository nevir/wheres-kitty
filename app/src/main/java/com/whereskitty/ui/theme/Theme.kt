package com.whereskitty.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBrown,
    onPrimary = OnPrimary,
    primaryContainer = WarmTan,
    onPrimaryContainer = DeepBrown,
    secondary = SecondaryGreen,
    onSecondary = Cream,
    secondaryContainer = Color(0xFFD4EDCF),
    onSecondaryContainer = DeepGreen,
    tertiary = CoralOrange,
    onTertiary = Cream,
    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = WarmTan,
    onSurfaceVariant = WarmBrown,
    outline = WarmBrown
)

@Composable
fun WhereIsKittyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
