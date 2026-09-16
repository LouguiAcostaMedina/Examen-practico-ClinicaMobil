package pe.edu.upeu.clinicamobil.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Paleta de salud (Azules y Cianes)
val PrimaryLight = Color(0xFF006493)
val OnPrimaryLight = Color(0xFFFFFFFF)
val PrimaryContainerLight = Color(0xFFCAE6FF)
val OnPrimaryContainerLight = Color(0xFF001E30)
val SecondaryLight = Color(0xFF006874)
val OnSecondaryLight = Color(0xFFFFFFFF)
val SecondaryContainerLight = Color(0xFF97F0FF)
val OnSecondaryContainerLight = Color(0xFF001F24)
val TertiaryLight = Color(0xFF006782)
val OnTertiaryLight = Color(0xFFFFFFFF)
val TertiaryContainerLight = Color(0xFFBCE9FF)
val OnTertiaryContainerLight = Color(0xFF001F29)
val ErrorLight = Color(0xFFBA1A1A)
val OnErrorLight = Color(0xFFFFFFFF)
val ErrorContainerLight = Color(0xFFFFDAD6)
val OnErrorContainerLight = Color(0xFF410002)
val BackgroundLight = Color(0xFFFCFCFF)
val OnBackgroundLight = Color(0xFF1A1C1E)
val SurfaceLight = Color(0xFFFCFCFF)
val OnSurfaceLight = Color(0xFF1A1C1E)
val SurfaceVariantLight = Color(0xFFDDE3EA)
val OnSurfaceVariantLight = Color(0xFF41474D)
val OutlineLight = Color(0xFF72787E)
val OutlineVariantLight = Color(0xFFC1C7CE)

// Surface Containers para Material 3
val SurfaceContainerLowestLight = Color(0xFFFFFFFF)
val SurfaceContainerLowLight = Color(0xFFF6F6F9)
val SurfaceContainerLight = Color(0xFFF0F0F4)
val SurfaceContainerHighLight = Color(0xFFEAEAEF)
val SurfaceContainerHighestLight = Color(0xFFE4E4E9)

val PrimaryDark = Color(0xFF8DCDFF)
val OnPrimaryDark = Color(0xFF003450)
val PrimaryContainerDark = Color(0xFF004B70)
val OnPrimaryContainerDark = Color(0xFFCAE6FF)
val SecondaryDark = Color(0xFF4FD8EB)
val OnSecondaryDark = Color(0xFF00363D)
val SecondaryContainerDark = Color(0xFF004F58)
val OnSecondaryContainerDark = Color(0xFF97F0FF)
val TertiaryDark = Color(0xFF63D3FF)
val OnTertiaryDark = Color(0xFF003545)
val TertiaryContainerDark = Color(0xFF004D63)
val OnTertiaryContainerDark = Color(0xFFBCE9FF)
val ErrorDark = Color(0xFFFFB4AB)
val OnErrorDark = Color(0xFF690005)
val ErrorContainerDark = Color(0xFF93000A)
val OnErrorContainerDark = Color(0xFFFFDAD6)
val BackgroundDark = Color(0xFF1A1C1E)
val OnBackgroundDark = Color(0xFFE2E2E5)
val SurfaceDark = Color(0xFF1A1C1E)
val OnSurfaceDark = Color(0xFFE2E2E5)
val SurfaceVariantDark = Color(0xFF41474D)
val OnSurfaceVariantDark = Color(0xFFC1C7CE)
val OutlineDark = Color(0xFF8B9198)
val OutlineVariantDark = Color(0xFF41474D)

val SurfaceContainerLowestDark = Color(0xFF0C0E10)
val SurfaceContainerLowDark = Color(0xFF1A1C1E)
val SurfaceContainerDarkColor = Color(0xFF1E2022)
val SurfaceContainerHighDark = Color(0xFF282A2C)
val SurfaceContainerHighestDark = Color(0xFF333537)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    primaryContainer = PrimaryContainerLight,
    onPrimaryContainer = OnPrimaryContainerLight,
    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,
    secondaryContainer = SecondaryContainerLight,
    onSecondaryContainer = OnSecondaryContainerLight,
    tertiary = TertiaryLight,
    onTertiary = OnTertiaryLight,
    tertiaryContainer = TertiaryContainerLight,
    onTertiaryContainer = OnTertiaryContainerLight,
    error = ErrorLight,
    onError = OnErrorLight,
    errorContainer = ErrorContainerLight,
    onErrorContainer = OnErrorContainerLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    outline = OutlineLight,
    outlineVariant = OutlineVariantLight,
    surfaceContainerLowest = SurfaceContainerLowestLight,
    surfaceContainerLow = SurfaceContainerLowLight,
    surfaceContainer = SurfaceContainerLight,
    surfaceContainerHigh = SurfaceContainerHighLight,
    surfaceContainerHighest = SurfaceContainerHighestLight
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    primaryContainer = PrimaryContainerDark,
    onPrimaryContainer = OnPrimaryContainerDark,
    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
    secondaryContainer = SecondaryContainerDark,
    onSecondaryContainer = OnSecondaryContainerDark,
    tertiary = TertiaryDark,
    onTertiary = OnTertiaryDark,
    tertiaryContainer = TertiaryContainerDark,
    onTertiaryContainer = OnTertiaryContainerDark,
    error = ErrorDark,
    onError = OnErrorDark,
    errorContainer = ErrorContainerDark,
    onErrorContainer = OnErrorContainerDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    outline = OutlineDark,
    outlineVariant = OutlineVariantDark,
    surfaceContainerLowest = SurfaceContainerLowestDark,
    surfaceContainerLow = SurfaceContainerLowDark,
    surfaceContainer = SurfaceContainerDarkColor,
    surfaceContainerHigh = SurfaceContainerHighDark,
    surfaceContainerHighest = SurfaceContainerHighestDark
)

@Composable
fun ClinicaMobilTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
