package davi.github.com.conectapovokt.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val EsquemaClaro = lightColorScheme(
    primary = Verde,
    onPrimary = Superficie,
    primaryContainer = VerdeClaro,
    onPrimaryContainer = Asfalto,
    secondary = FaixaAmarela,
    onSecondary = Asfalto,
    background = Fundo,
    onBackground = Asfalto,
    surface = Superficie,
    onSurface = Asfalto,
    onSurfaceVariant = TextoSecundario,
    error = StatusAberta
)

// Cores fixas (sem dynamicColor) para a identidade ficar igual em todo aparelho.
@Composable
fun ConectaPovoKTTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EsquemaClaro,
        typography = Typography,
        content = content
    )
}
