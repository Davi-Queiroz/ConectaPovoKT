package davi.github.com.conectapovokt.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import davi.github.com.conectapovokt.model.Categoria
import davi.github.com.conectapovokt.model.Status
import davi.github.com.conectapovokt.ui.theme.Asfalto
import davi.github.com.conectapovokt.ui.theme.AsfaltoClaro
import davi.github.com.conectapovokt.ui.theme.CatBuraco
import davi.github.com.conectapovokt.ui.theme.CatCalcada
import davi.github.com.conectapovokt.ui.theme.CatIluminacao
import davi.github.com.conectapovokt.ui.theme.CatLixo
import davi.github.com.conectapovokt.ui.theme.CatOutro
import davi.github.com.conectapovokt.ui.theme.FaixaAmarela
import davi.github.com.conectapovokt.ui.theme.StatusAberta
import davi.github.com.conectapovokt.ui.theme.StatusAndamento
import davi.github.com.conectapovokt.ui.theme.StatusResolvida

fun Categoria.icone(): ImageVector = when (this) {
    Categoria.BURACO -> Icons.Filled.Warning
    Categoria.ILUMINACAO -> Icons.Filled.Info
    Categoria.LIXO -> Icons.Filled.Build
    Categoria.CALCADA -> Icons.Filled.Place
    Categoria.OUTRO -> Icons.Filled.Home
}

fun Categoria.cor(): Color = when (this) {
    Categoria.BURACO -> CatBuraco
    Categoria.ILUMINACAO -> CatIluminacao
    Categoria.LIXO -> CatLixo
    Categoria.CALCADA -> CatCalcada
    Categoria.OUTRO -> CatOutro
}

fun Status.cor(): Color = when (this) {
    Status.ABERTA -> StatusAberta
    Status.EM_ANDAMENTO -> StatusAndamento
    Status.RESOLVIDA -> StatusResolvida
}

/** Cabeçalho das telas: fundo asfalto + faixa tracejada amarela (marcação de rua). */
@Composable
fun CabecalhoTela(titulo: String, subtitulo: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
            .background(Asfalto)
            .statusBarsPadding()
    ) {
        Column(Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 16.dp)) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
            Text(
                text = subtitulo,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.75f),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        FaixaDeRua(Modifier.padding(bottom = 14.dp))
    }
}

@Composable
fun FaixaDeRua(modifier: Modifier = Modifier) {
    Canvas(
        modifier
            .fillMaxWidth()
            .height(4.dp)
    ) {
        drawLine(
            color = FaixaAmarela,
            start = Offset(0f, size.height / 2),
            end = Offset(size.width, size.height / 2),
            strokeWidth = size.height,
            cap = StrokeCap.Butt,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(36f, 24f))
        )
    }
}

@Composable
fun SeloStatus(status: Status, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(50),
        color = status.cor().copy(alpha = 0.14f),
        modifier = modifier
    ) {
        Column(
            Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = status.rotulo,
                style = MaterialTheme.typography.labelMedium,
                color = status.cor()
            )
        }
    }
}

@Composable
fun IconeCategoria(categoria: Categoria, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(categoria.cor().copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = categoria.icone(),
            contentDescription = categoria.rotulo,
            tint = categoria.cor(),
            modifier = Modifier.size(22.dp)
        )
    }
}

@Composable
fun EstadoVazio(titulo: String, texto: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(AsfaltoClaro.copy(alpha = 0.10f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Filled.Place,
                contentDescription = null,
                tint = AsfaltoClaro,
                modifier = Modifier.size(34.dp)
            )
        }
        Text(
            titulo,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            texto,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
