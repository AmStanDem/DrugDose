package it.uninsubria.drugdose.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = MedicalBlue80,
    onPrimary = MedicalBlue40,
    primaryContainer = MedicalBlue40,
    onPrimaryContainer = MedicalBlue90,
    
    secondary = MedicalTeal80,
    onSecondary = MedicalTeal40,
    secondaryContainer = MedicalTeal40,
    onSecondaryContainer = MedicalTeal80,
    
    error = MedicalError80,
    onError = MedicalError40,
    
    background = Neutral10,
    onBackground = Neutral90,
    surface = Neutral10,
    onSurface = Neutral90,
)

private val LightColorScheme = lightColorScheme(
    primary = MedicalBlue40,
    onPrimary = Neutral99,
    primaryContainer = MedicalBlue90,
    onPrimaryContainer = MedicalBlue40,
    
    secondary = MedicalTeal40,
    onSecondary = Neutral99,
    secondaryContainer = MedicalTealContainer,
    onSecondaryContainer = MedicalTeal40,
    
    error = MedicalError40,
    onError = Neutral99,
    
    background = Neutral99,
    onBackground = Neutral10,
    surface = Neutral99,
    onSurface = Neutral10,
)

@Composable
fun DrugDoseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // In Android moderno con enableEdgeToEdge(), la barra di stato è trasparente di default.
            // Gestiamo solo la visibilità delle icone (scure in Light Theme, chiare in Dark Theme).
            val insetsController = WindowCompat.getInsetsController(window, view)
            insetsController.isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
