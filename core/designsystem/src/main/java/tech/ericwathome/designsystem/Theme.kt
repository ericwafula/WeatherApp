package tech.ericwathome.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import tech.ericwathome.designsystem.utils.LocalTextUtils
import tech.ericwathome.designsystem.utils.TextUtils
import tech.ericwathome.designsystem.utils.previewSupported

private val DarkColorScheme =
    darkColorScheme(
        primary = White,
        onPrimary = Black,
        secondary = Yellow,
        onSecondary = Black,
        surface = White.copy(0.1f),
        onSurface = White,
        outline = Yellow.copy(0.4f),
        outlineVariant = White.copy(0.4f),
    )

@Composable
fun WeatherAppTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalTextUtils provides TextUtils(),
    ) {
        MaterialTheme(
            colorScheme = DarkColorScheme,
            typography = Typography.previewSupported,
            content = content,
        )
    }
}