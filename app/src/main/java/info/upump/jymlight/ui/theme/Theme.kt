package info.upump.jymlight.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = ONPrimary,
    secondary = Secondary,
    onSecondary = ONSecondary,
    background = Background,
    onBackground = OnBackground,
    primaryContainer = Accent,
    onPrimaryContainer = OnAccent,
    // текст labal
    tertiary = Tertiary,
    onTertiary = OnTertiary, //подчеркивание и label
    outlineVariant = OutlineVariant
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = ONPrimaryLight,
    secondary = SecondaryLight,
    onSecondary = ONSecondaryLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    primaryContainer = AccentLight,
    onPrimaryContainer = OnAccentLight,
    // текст labal
    tertiary = TertiaryLight,
    onTertiary = OnTertiaryLight, //подчеркивание и label
    outlineVariant = OutlineVariantLight
)

@Composable
fun JymTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
    /*    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }*/

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}