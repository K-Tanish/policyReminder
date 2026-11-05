package com.policyreminder.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val PolicyReminderColorScheme = darkColorScheme(
    primary = ForegroundPrimary,
    onPrimary = BackgroundPrimary,
    secondary = ForegroundPrimary,
    onSecondary = BackgroundPrimary,
    background = BackgroundPrimary,
    onBackground = ForegroundPrimary,
    surface = BackgroundPrimary,
    onSurface = ForegroundPrimary,
    surfaceVariant = Foreground15,
    onSurfaceVariant = ForegroundPrimary,
    outline = Foreground30,
    error = ForegroundPrimary,
    onError = BackgroundPrimary
)

@Composable
fun PolicyReminderTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = PolicyReminderColorScheme,
        typography = Typography,
        content = content
    )
}
