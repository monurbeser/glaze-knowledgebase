package com.glaze.knowledgebase.ui.theme
import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.glaze.knowledgebase.domain.model.ThemeMode

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF8B5A2B), onPrimary = Color.White,
    primaryContainer = Color(0xFFFFDCC2), onPrimaryContainer = Color(0xFF2E1500),
    secondary = Color(0xFF755848), background = Color(0xFFFFFBFF), surface = Color(0xFFFFFBFF)
)
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFB77C), onPrimary = Color(0xFF4A2800),
    primaryContainer = Color(0xFF6A3C00), onPrimaryContainer = Color(0xFFFFDCC2),
    secondary = Color(0xFFE6BEAB), background = Color(0xFF201A17), surface = Color(0xFF201A17)
)

@Composable
fun GlazeKnowledgeBaseTheme(themeMode: ThemeMode = ThemeMode.SYSTEM, content: @Composable () -> Unit) {
    val darkTheme = when (themeMode) { ThemeMode.SYSTEM -> isSystemInDarkTheme(); ThemeMode.LIGHT -> false; ThemeMode.DARK -> true }
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }
    MaterialTheme(colorScheme = colorScheme, content = content)
}

val SafetyToxic = Color(0xFFD32F2F)
val SafetyCaution = Color(0xFFFFA000)
val SafetySafe = Color(0xFF388E3C)
