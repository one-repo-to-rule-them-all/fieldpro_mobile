package app.fieldpro.designsystem

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView

private val FieldProLight = lightColorScheme(
    primary = Color(0xFF0E4F88),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFCFE3FA),
    onPrimaryContainer = Color(0xFF001D34),
    secondary = Color(0xFF52606D),
    onSecondary = Color.White,
    tertiary = Color(0xFF1F7A1F),
    onTertiary = Color.White,
    background = Color(0xFFFCFCFF),
    onBackground = Color(0xFF1A1C1E),
    surface = Color(0xFFFCFCFF),
    onSurface = Color(0xFF1A1C1E),
    surfaceVariant = Color(0xFFDEE3EB),
    onSurfaceVariant = Color(0xFF42474E),
    error = Color(0xFFBA1A1A),
    onError = Color.White,
)

private val FieldProDark = darkColorScheme(
    primary = Color(0xFF9CCAFF),
    onPrimary = Color(0xFF003258),
    primaryContainer = Color(0xFF00497D),
    onPrimaryContainer = Color(0xFFCFE3FA),
    secondary = Color(0xFFB9C8D7),
    onSecondary = Color(0xFF23323E),
    tertiary = Color(0xFF8EDC8E),
    onTertiary = Color(0xFF003A03),
    background = Color(0xFF1A1C1E),
    onBackground = Color(0xFFE3E2E6),
    surface = Color(0xFF1A1C1E),
    onSurface = Color(0xFFE3E2E6),
    surfaceVariant = Color(0xFF42474E),
    onSurfaceVariant = Color(0xFFC2C7CF),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
)

@Composable
fun FieldProTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic colour (Material You) opt-in: off by default so the brand palette stays consistent
    // across devices. Phase 3 may flip this to user-selectable.
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val ctx = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(ctx) else dynamicLightColorScheme(ctx)
        }
        darkTheme -> FieldProDark
        else -> FieldProLight
    }

    // Compose owns the window once it draws; status bar tinting is left to system defaults
    // until Phase 3 polish lands.
    @Suppress("UNUSED_VARIABLE")
    val view = LocalView.current

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography,
        content = content,
    )
}

/** Re-export of the underlying [Activity] type so feature modules can opt into edge-to-edge
 *  setup without importing AndroidX explicitly. */
typealias FieldProActivity = Activity
